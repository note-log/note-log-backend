ALTER TABLE note modify column delete_at date null;
ALTER TABLE note modify column latest_tag bigint null;
ALTER TABLE log modify column location varchar(128) null;
ALTER TABLE log modify column content text not null;
ALTER TABLE note modify column content text not null;