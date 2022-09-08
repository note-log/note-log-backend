package com.snowwarrior.notelog.model;

import com.snowwarrior.notelog.dto.NoteDTO;
import com.snowwarrior.notelog.dto.UpdateNoteDTO;
import com.snowwarrior.notelog.dto.UserRegisterDTO;
import org.springframework.beans.BeanUtils;

import java.util.Date;

public class Note {
    private Long id;
    private Long userId;
    private String content;
    private Date deleteAt;
    private Long latestTag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDeleteAt() {
        return deleteAt;
    }

    public void setDeleteAt(Date deleteAt) {
        this.deleteAt = deleteAt;
    }

    public Long getLatestTag() {
        return latestTag;
    }

    public void setLatestTag(Long latestTag) {
        this.latestTag = latestTag;
    }

    public void fromNoteDTO(NoteDTO dto) {
        BeanUtils.copyProperties(dto, this);
    }

    public void fromUpdateNoteDTO(UpdateNoteDTO dto) {
        BeanUtils.copyProperties(dto, this);
    }
}
