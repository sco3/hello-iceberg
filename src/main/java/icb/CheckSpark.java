package icb;

import org.apache.spark.sql.SparkSession;
import org.apache.iceberg.spark.extensions.IcebergSparkSessionExtensions;
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

		System.out.println("Spark version: " + spark.version());
		System.out.println("Catalogs: " + spark.catalog().listCatalogs().collectAsList());

		spark.stop();
	}
}