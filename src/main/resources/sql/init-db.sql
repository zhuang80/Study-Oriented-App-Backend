-- First to create role wequan_admin and database wequan

-- Role: wequan_admin
-- DROP ROLE wequan_admin;

CREATE ROLE wequan_admin WITH
  LOGIN
  SUPERUSER
  INHERIT
  CREATEDB
  CREATEROLE
  REPLICATION;

COMMENT ON ROLE wequan_admin IS 'this is the administrator for wequan database';

ALTER USER wequan_admin WITH PASSWORD 'wequan@bu';

-- Database: wequan
-- DROP DATABASE wequan;

CREATE DATABASE wequan
    WITH
    OWNER = wequan_admin
    ENCODING = 'UTF8'
    LC_COLLATE = 'English_United States.1252'
    LC_CTYPE = 'English_United States.1252'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;
