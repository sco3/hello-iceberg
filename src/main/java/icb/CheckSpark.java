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

		spark //
				.sql("DESCRIBE EXTENDED local.db.users") //
				.filter("col_name = 'Location'") //
				.show(1, 60, false);

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

		out.println("Select after insert");
		spark.sql("SELECT * FROM local.db.users").show();

		spark.sql("delete from local.db.users");

		out.println("Select after delete");
		spark.sql("SELECT * FROM local.db.users").show();

		out.println("Select version V1");
		spark.sql("""
				select * from local.db.users version as of 'V1'
				"""//
		).show();

		spark.stop();
	}
}