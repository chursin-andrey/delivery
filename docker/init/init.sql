CREATE USER "delivery" WITH PASSWORD 'delivery';
CREATE DATABASE "delivery" WITH OWNER "delivery";

GRANT ALL ON DATABASE "delivery" TO "delivery";

\connect "delivery";
CREATE SCHEMA "delivery" AUTHORIZATION "delivery";