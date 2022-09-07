CREATE TABLE note
(
    id         BIGINT       not null primary key auto_increment COMMENT 'note id',
    user_id    BIGINT       not null,
    content    VARCHAR(512) not null COMMENT '内容',
    delete_at  date         not null COMMENT '删除时间',
    latest_tag BIGINT       not null COMMENT '最新标签',
    FOREIGN KEY (user_id) references user (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE log
(
    id         BIGINT       not null primary key auto_increment COMMENT 'log id',
    note_id    BIGINT       not null,
    content    VARCHAR(512) not null COMMENT '数据',
    location   VARCHAR(128) not null COMMENT '位置',
    created_at date         not null COMMENT '删除时间',
    FOREIGN KEY (note_id) references note (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

CREATE INDEX idx_location ON log (location);
CREATE INDEX idx_creation ON log (created_at);