package icb;

import static icb.Check.runChecks;

import org.apache.iceberg.spark.SparkCatalog;
import org.apache.iceberg.spark.extensions.IcebergSparkSessionExtensions;
import org.apache.spark.sql.SparkSession;

public class CheckSparkS3 {
	public static void main(String[] args) {
		var icebergExt = IcebergSparkSessionExtensions.class.getName();
		SparkSession spark = SparkSession.builder().master("local[*]")//
				.config("spark.sql.extensions", icebergExt)
				.config("spark.sql.catalog.local", SparkCatalog.class.getName())
				.config("spark.sql.catalog.local.type", "hadoop")
				.config("spark.sql.catalog.local.warehouse", "s3a://warehouse/db") // MINIO location
				.config("spark.hadoop.fs.s3a.endpoint", "http://minio:9000") // S3A CONFIGURATION
				.config("spark.hadoop.fs.s3a.access.key", "admin") // credentials
				.config("spark.hadoop.fs.s3a.secret.key", "password") // credentials
				.config("spark.hadoop.fs.s3a.path.style.access", "true")
				.config("spark.hadoop.fs.s3a.impl", "org.apache.hadoop.fs.s3a.S3AFileSystem")//
				.getOrCreate();

		runChecks(spark);
		spark.close();

	}
}
