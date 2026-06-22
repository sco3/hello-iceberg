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
find warehouse 
warehouse
warehouse/db
warehouse/db/users
warehouse/db/users/metadata
warehouse/db/users/metadata/v1.metadata.json
warehouse/db/users/metadata/.v1.metadata.json.crc
warehouse/db/users/metadata/efab923e-e938-4adc-81b5-7e08d2bd527e-m0.avro
warehouse/db/users/metadata/.efab923e-e938-4adc-81b5-7e08d2bd527e-m0.avro.crc
warehouse/db/users/metadata/snap-533092365699838724-1-efab923e-e938-4adc-81b5-7e08d2bd527e.avro
warehouse/db/users/metadata/.snap-533092365699838724-1-efab923e-e938-4adc-81b5-7e08d2bd527e.avro.crc
warehouse/db/users/metadata/v2.metadata.json
warehouse/db/users/metadata/.v2.metadata.json.crc
warehouse/db/users/metadata/version-hint.text
warehouse/db/users/metadata/.version-hint.text.crc
warehouse/db/users/data
warehouse/db/users/data/00000-6-5d913a44-a719-4c8a-9d4a-fa374acc1fe5-0-00001.parquet
warehouse/db/users/data/.00000-6-5d913a44-a719-4c8a-9d4a-fa374acc1fe5-0-00001.parquet.crc
warehouse/db/users/data/00001-7-5d913a44-a719-4c8a-9d4a-fa374acc1fe5-0-00001.parquet
warehouse/db/users/data/.00001-7-5d913a44-a719-4c8a-9d4a-fa374acc1fe5-0-00001.parquet.crc

```

### Test insert - tag - delete - select tag

```

Spark version: 3.5.5
Catalogs: [Catalog[name='spark_catalog', ]]
+-------------+
|      catalog|
+-------------+
|spark_catalog|
+-------------+

+---------+
|namespace|
+---------+
|       db|
+---------+

Create result:[]
Exists: true
+--------+--------------------------------+-------+
|col_name|                       data_type|comment|
+--------+--------------------------------+-------+
|Location|/home/iceberg/warehouse/db/users|       |
+--------+--------------------------------+-------+

Insert result:[]
Select after insert
+---+-----+-----------+
| id| name|signup_date|
+---+-----+-----------+
|  1|Alice| 2026-06-22|
|  2|  Bob| 2026-06-22|
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
|  1|Alice| 2026-06-21|
|  2|  Bob| 2026-06-21|
|  1|Alice| 2026-06-21|
|  2|  Bob| 2026-06-21|
|  1|Alice| 2026-06-21|
|  2|  Bob| 2026-06-21|
+---+-----+-----------+

```