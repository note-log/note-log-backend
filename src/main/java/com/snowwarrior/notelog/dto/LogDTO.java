package com.snowwarrior.notelog.dto;

import javax.xml.crypto.Data;

public class LogDTO {
    private Long id;

    private String location;
    private Data createAt;

    public Long getId() {
        return id;
    }

    public String getLocation() {
        return location;
    }

    public Data getCreateAt() {
        return createAt;
    }
}
