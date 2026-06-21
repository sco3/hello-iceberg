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
