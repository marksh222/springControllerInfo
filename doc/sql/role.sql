-- Role: postgres
-- DROP ROLE postgres;

CREATE ROLE postgres WITH
  LOGIN
  SUPERUSER
  INHERIT
  CREATEDB
  CREATEROLE
  REPLICATION
  ENCRYPTED PASSWORD 'md5244af1e2823d5eaeeffc42c5096d8260';