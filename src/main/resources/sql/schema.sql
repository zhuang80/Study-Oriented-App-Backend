-- First to create database wequan, schema bu and role wequan_admin

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

COMMENT ON DATABASE wequan
    IS 'this is the database for wequan app';

-- SCHEMA: bu
-- DROP SCHEMA bu ;

CREATE SCHEMA bu
    AUTHORIZATION wequan_admin;

COMMENT ON SCHEMA bu
    IS 'this is the schema for wequan app';

GRANT ALL ON SCHEMA bu TO wequan_admin WITH GRANT OPTION;

---------------------Tables---------------------
-- Table: bu.wq_user_profile

-- DROP TABLE bu.wq_user_profile;

CREATE TABLE bu.wq_user_profile
(
    id integer NOT NULL,
    name character varying(20) COLLATE pg_catalog."default",
    nickname character varying(20) COLLATE pg_catalog."default" NOT NULL,
    email character varying(50) COLLATE pg_catalog."default" NOT NULL,
    phone character varying(15) COLLATE pg_catalog."default",
    address character varying(100) COLLATE pg_catalog."default",
    zip_code character varying(10) COLLATE pg_catalog."default",
    credential character varying(50) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT user_pk_id PRIMARY KEY (id),
    CONSTRAINT user_unique_email UNIQUE (email)

)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE bu.wq_user_profile
    OWNER to wequan_admin;
COMMENT ON TABLE bu.wq_user_profile
    IS 'register user table';

-- Table: bu.wq_tutor

-- DROP TABLE bu.wq_tutor;

CREATE TABLE bu.wq_tutor
(
    id integer NOT NULL DEFAULT nextval('bu.wq_tutor_id_seq'::regclass),
    user_id integer NOT NULL,
    CONSTRAINT tutor_pk_id PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE bu.wq_tutor
    OWNER to wequan_admin;