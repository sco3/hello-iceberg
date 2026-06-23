# Hello Iceberg

A local Apache Iceberg environment using Docker Compose.

## Services

- **MinIO** — S3-compatible object storage (ports 9000, 9001)
- **REST** — Iceberg REST catalog
- **Spark** — Spark with Iceberg and Jupyter Notebook (port 8888)

## Getting Started

```bash
docker compose up -d
```

Open Jupyter Notebook at http://localhost:8888.

## Credentials

| Key | Value |
|-----|-------|
| MinIO User | admin |
| MinIO Password | password |


### Test results

```
find /tmp/iceberg_warehouse
/tmp/iceberg_warehouse
/tmp/iceberg_warehouse/default
/tmp/iceberg_warehouse/db
/tmp/iceberg_warehouse/db/users
/tmp/iceberg_warehouse/db/users/metadata
/tmp/iceberg_warehouse/db/users/metadata/v1.metadata.json
/tmp/iceberg_warehouse/db/users/metadata/.v1.metadata.json.crc
/tmp/iceberg_warehouse/db/users/metadata/6f67670f-97b1-4ae5-a92f-735a9ea11733-m0.avro
/tmp/iceberg_warehouse/db/users/metadata/.6f67670f-97b1-4ae5-a92f-735a9ea11733-m0.avro.crc
/tmp/iceberg_warehouse/db/users/metadata/snap-7708295380166535493-1-6f67670f-97b1-4ae5-a92f-735a9ea11733.avro
/tmp/iceberg_warehouse/db/users/metadata/.snap-7708295380166535493-1-6f67670f-97b1-4ae5-a92f-735a9ea11733.avro.crc
/tmp/iceberg_warehouse/db/users/metadata/v2.metadata.json
/tmp/iceberg_warehouse/db/users/metadata/.v2.metadata.json.crc
/tmp/iceberg_warehouse/db/users/metadata/v3.metadata.json
/tmp/iceberg_warehouse/db/users/metadata/.v3.metadata.json.crc
/tmp/iceberg_warehouse/db/users/metadata/2ae32893-407b-4a1d-b353-4b566e9335e0-m0.avro
/tmp/iceberg_warehouse/db/users/metadata/.2ae32893-407b-4a1d-b353-4b566e9335e0-m0.avro.crc
/tmp/iceberg_warehouse/db/users/metadata/snap-2596711535458396075-1-2ae32893-407b-4a1d-b353-4b566e9335e0.avro
/tmp/iceberg_warehouse/db/users/metadata/.snap-2596711535458396075-1-2ae32893-407b-4a1d-b353-4b566e9335e0.avro.crc
/tmp/iceberg_warehouse/db/users/metadata/v4.metadata.json
/tmp/iceberg_warehouse/db/users/metadata/.v4.metadata.json.crc
/tmp/iceberg_warehouse/db/users/metadata/version-hint.text
/tmp/iceberg_warehouse/db/users/metadata/.version-hint.text.crc
/tmp/iceberg_warehouse/db/users/data
/tmp/iceberg_warehouse/db/users/data/00001-7-b6bd1d79-b1d7-4fe4-af1d-9c8429e89306-0-00001.parquet
/tmp/iceberg_warehouse/db/users/data/00000-6-b6bd1d79-b1d7-4fe4-af1d-9c8429e89306-0-00001.parquet
/tmp/iceberg_warehouse/db/users/data/.00001-7-b6bd1d79-b1d7-4fe4-af1d-9c8429e89306-0-00001.parquet.crc
/tmp/iceberg_warehouse/db/users/data/.00000-6-b6bd1d79-b1d7-4fe4-af1d-9c8429e89306-0-00001.parquet.crc

```

### Test insert - tag - delete - select tag

```

Spark version: 3.5.5
Catalogs: [Catalog[name='local', ], Catalog[name='spark_catalog', ]]
+-------------+
|      catalog|
+-------------+
|        local|
|spark_catalog|
+-------------+

+---------+
|namespace|
+---------+
|  default|
|       db|
+---------+

Create result:[]
Exists: true
+--------+-------------------------------+-------+
|col_name|                      data_type|comment|
+--------+-------------------------------+-------+
|Location|/tmp/iceberg_warehouse/db/users|       |
+--------+-------------------------------+-------+

Insert result:[]
Select after insert
+---+-----+-----------+
| id| name|signup_date|
+---+-----+-----------+
|  1|Alice| 2026-06-23|
|  2|  Bob| 2026-06-23|
+---+-----+-----------+

Select after delete
+---+----+-----------+
| id|name|signup_date|
+---+----+-----------+
+---+----+-----------+

Select version V1
+---+-----+-----------+
| id| name|signup_date|
+---+-----+-----------+
|  1|Alice| 2026-06-23|
|  2|  Bob| 2026-06-23|
+---+-----+-----------+

```
