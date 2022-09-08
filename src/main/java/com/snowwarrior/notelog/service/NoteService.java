package com.snowwarrior.notelog.service;

import com.snowwarrior.notelog.exception.NotFoundException;
import com.snowwarrior.notelog.exception.UnauthorizedException;
import com.snowwarrior.notelog.mapper.LogMapper;
import com.snowwarrior.notelog.mapper.NoteMapper;
import com.snowwarrior.notelog.mapper.UserMapper;
import com.snowwarrior.notelog.model.Log;
import com.snowwarrior.notelog.model.Note;
import com.snowwarrior.notelog.util.ObjectHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class NoteService {
    private final NoteMapper noteMapper;
    private final LogMapper logMapper;

    private final UserMapper userMapper;

    @Autowired
    public NoteService(NoteMapper noteMapper, UserMapper userMapper, LogMapper logMapper) {
        this.noteMapper = noteMapper;
        this.userMapper = userMapper;
        this.logMapper = logMapper;
    }

    public List<Note> getNote() {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Long> userIdOptional = userMapper.getUserIdByUsername(username);
        if (userIdOptional.isEmpty()) {
            throw new UsernameNotFoundException("can't get your note.");
        }
        Long userId = userIdOptional.get();
        return noteMapper.getNoteByUserId(userId);
    }

    @Transactional
    public void addNote(Note note, String location) {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Long> userIdOptional = userMapper.getUserIdByUsername(username);
        if (userIdOptional.isEmpty()) {
            throw new UsernameNotFoundException("can't find you account.");
        }
        Long userId = userIdOptional.get();
        note.setUserId(userId);
        Log log = new Log();
        noteMapper.addNote(note);
        log.setCreatedAt(Date.from(Instant.now()));
        log.setContent(note.getContent());
        log.setNoteId(note.getId());
        logMapper.insertLog(log);
        note.setLatestTag(log.getId());
        noteMapper.updateNoteById(note);
    }

    @Transactional
    public void updateNote(Note note, String location) {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Long> userIdOptional = userMapper.getUserIdByUsername(username);
        if (userIdOptional.isEmpty()) {
            throw new UsernameNotFoundException("can't find you account.");
        }
        Long userId = userIdOptional.get();
        var oldNote = noteMapper.getNoteById(note.getId());
        if (oldNote.isEmpty()) throw new IllegalStateException("note not found.");

        var newNote = oldNote.get();
        if (newNote.getDeleteAt() != null) throw new IllegalStateException("note has been deleted.");
        if (!Objects.equals(newNote.getUserId(), userId))
            throw new UnauthorizedException("no privilege to update this note.");
        ObjectHelper.copyNoNullProperties(note, newNote);
        var log = new Log();
        log.setNoteId(newNote.getId());
        log.setLocation(location);
        log.setContent(newNote.getContent());
        log.setCreatedAt(Date.from(Instant.now()));
        logMapper.insertLog(log);
        newNote.setLatestTag(log.getId());
        noteMapper.updateNoteById(newNote);
    }

    public void deleteNote(Long noteId) {
        if (noteId == null) throw new IllegalStateException("note id can't be null.");
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Long> userIdOptional = userMapper.getUserIdByUsername(username);
        if (userIdOptional.isEmpty()) {
            throw new UsernameNotFoundException("can't find you account.");
        }
        Long userId = userIdOptional.get();
        Optional<Note> noteOptional = noteMapper.getNoteById(noteId);
        if (noteOptional.isEmpty()) {
            throw new NotFoundException("can't find this note");
        }
        var note = noteOptional.get();
        if (note.getDeleteAt() != null) throw new IllegalStateException("note has been deleted.");
        if (!Objects.equals(note.getUserId(), userId)) {
            throw new UnauthorizedException("no privilege to delete this note.");
        }
        noteMapper.deleteNoteById(noteId);
    }
}
