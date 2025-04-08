CREATE USER delivery WITH PASSWORD 'delivery';
CREATE DATABASE delivery WITH OWNER delivery;

GRANT ALL ON DATABASE delivery TO delivery;
GRANT ALL ON SCHEMA public TO delivery;

CREATE SCHEMA IF NOT EXISTS delivery;