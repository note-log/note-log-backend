package com.snowwarrior.notelog.dto;

import javax.xml.crypto.Data;

public class LogDTO {
    private Long id;

    private Long noteId;

    private String location;
    private Data createAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNoteId() {
        return noteId;
    }

    public void setNoteId(Long noteId) {
        this.noteId = noteId;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setCreateAt(Data createAt) {
        this.createAt = createAt;
    }

    public String getLocation() {
        return location;
    }

    public Data getCreateAt() {
        return createAt;
    }
}
