ALTER TABLE log MODIFY COLUMN created_at DATETIME not null;
ALTER TABLE note MODIFY COLUMN delete_at DATETIME null;