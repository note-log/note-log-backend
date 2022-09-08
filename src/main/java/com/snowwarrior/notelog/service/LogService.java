package com.snowwarrior.notelog.service;

import com.snowwarrior.notelog.mapper.LogMapper;
import com.snowwarrior.notelog.mapper.NoteMapper;
import com.snowwarrior.notelog.mapper.UserMapper;
import com.snowwarrior.notelog.model.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LogService {
    private final NoteMapper noteMapper;
    private final LogMapper logMapper;

    private final UserMapper userMapper;

    @Autowired
    public LogService(NoteMapper noteMapper, UserMapper userMapper, LogMapper logMapper) {
        this.noteMapper = noteMapper;
        this.userMapper = userMapper;
        this.logMapper = logMapper;
    }

    public List<Log> getLog() {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Long> userIdOptional = userMapper.getUserIdByUsername(username);
        if (userIdOptional.isEmpty()) {
            throw new UsernameNotFoundException("can't get your note.");
        }
        Long userId = userIdOptional.get();
        var notes = noteMapper.getNoteByUserId(userId);
        List<Log> logs = new ArrayList<>();
        for (var note : notes) {
            var per_note_logs = logMapper.getLogByNoteId(note.getId());
            logs.addAll(per_note_logs);
        }
        logs.sort((a, b) -> b.getCreatedAt().compareTo(a.getCreatedAt()));
        return logs;
    }

    public List<Log> filterLog() {
        List<Log> logs = new ArrayList<>();
        return logs;
    }
}
