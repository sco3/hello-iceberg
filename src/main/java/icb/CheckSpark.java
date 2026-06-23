package icb;

import static icb.Check.runChecks;

import java.io.File;
import org.apache.iceberg.spark.SparkCatalog;
import org.apache.iceberg.spark.extensions.IcebergSparkSessionExtensions;
import org.apache.spark.sql.SparkSession;

public class CheckSpark {
	public static void main(String[] args) {
		String location = "/tmp/iceberg_warehouse";
		new File(location).mkdirs();

		String icebergExt = IcebergSparkSessionExtensions.class.getName();
		String catalog = SparkCatalog.class.getName();
		String cat_type = "hadoop";

		SparkSession spark = SparkSession.builder().master("local[*]").config("spark.sql.extensions", icebergExt)
				.config("spark.sql.catalog.local", catalog).config("spark.sql.catalog.local.type", cat_type)
				.config("spark.sql.catalog.local.warehouse", location).getOrCreate();

		runChecks(spark);

		spark.stop();
	}
}