package com.snowwarrior.notelog.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

public class UpdateNoteDTO {
    private Long id;
    private String content;
    private String location;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}