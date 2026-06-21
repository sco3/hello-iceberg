package icb;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.iceberg.spark.extensions.IcebergSparkSessionExtensions;

import static java.lang.System.out;

import org.apache.iceberg.spark.SparkCatalog;

public class CheckSpark {
	public static void main(String[] args) {

		SparkSession spark = SparkSession.builder() //
				.master("local[*]") //
				.config("spark.sql.extensions", IcebergSparkSessionExtensions.class.getName())
				.config("spark.sql.catalog.local", SparkCatalog.class.getName())
				.config("spark.sql.catalog.local.type", "hadoop")
				.config("spark.sql.catalog.local.warehouse", "/home/iceberg/warehouse") //
				.getOrCreate();

		spark.sparkContext().setLogLevel("ERROR");

		out.println("Spark version: " + spark.version());
		out.println("Catalogs: " + spark.catalog().listCatalogs().collectAsList());

		spark.sql("SHOW catalogs").show();
		spark.sql("SHOW NAMESPACES IN local").show();

		// 1. Create a table using SQL
		var result = spark.sql("""
				CREATE TABLE IF NOT EXISTS
				local.db.users (
				   id bigint,
				   name string,
				   signup_date date
				)
				USING iceberg
				""" //
		);
		out.println("Create result:" + result);

		boolean tabExists = spark.catalog().tableExists("local.db.users");

		out.println("Exists: " + tabExists);

		Dataset<Row> tabDesc = spark.sql("DESCRIBE EXTENDED local.db.users");
		tabDesc.filter("col_name = 'Location'").collectAsList().forEach(r -> {
			out.println("Property: " + r.getAs("col_name"));
			out.println("Value: " + r.getAs("data_type"));
		});

		// 2. Insert some data
		result = spark.sql("""
				INSERT INTO local.db.users
				VALUES
				   (1, 'Alice', now()),
				   (2, 'Bob', now())
				""" //
		);

		out.println("Insert result:" + result);
		spark.sql("""
						ALTER TABLE local.db.users
						CREATE TAG if not exists V1
						RETAIN 365 DAYS
				"""//
		);
		spark.sql("SELECT * FROM local.db.users").show();

		spark.sql("delete from local.db.users");

		spark.sql("SELECT * FROM local.db.users").show();

		spark.sql("""
				select * from local.db.users version as of 'V1'
				"""//
		).show();

		spark.stop();
	}
}