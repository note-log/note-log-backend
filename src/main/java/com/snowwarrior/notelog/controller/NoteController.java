package com.snowwarrior.notelog.controller;

import com.snowwarrior.notelog.dto.NoteDTO;
import com.snowwarrior.notelog.dto.Response;
import com.snowwarrior.notelog.model.Note;
import com.snowwarrior.notelog.service.NoteService;
import com.snowwarrior.notelog.util.ResponseEntityHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api/note")
public class NoteController {
    private final NoteService noteService;

    @Autowired
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping("")
    public ResponseEntity<Response<List<NoteDTO>>> getNote() {
        try {
            List<Note> notes = noteService.getNote();
            List<NoteDTO> noteDTOS = new ArrayList<>();
            for (var note: notes) noteDTOS.add(note.convertToNoteDTO());
            return ResponseEntityHelper.ok("success", "notes", noteDTOS);
        } catch (Exception e) {
            return ResponseEntityHelper.fail(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("")
    public ResponseEntity<Response<Integer>> addNote(@RequestBody @Valid NoteDTO noteDTO) {
        var note = new Note();
        note.fromNoteDTO(noteDTO);
        try {
            noteService.addNote(note, noteDTO.getLocation());
            return ResponseEntityHelper.ok("success");
        } catch (Exception e) {
            return ResponseEntityHelper.fail(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("")
    public ResponseEntity<Response<Integer>> updateNote(@RequestBody @Valid NoteDTO noteDTO) {
        var note = new Note();
        note.fromNoteDTO(noteDTO);
        try {
            noteService.updateNote(note, noteDTO.getLocation());
            return ResponseEntityHelper.ok("success");
        } catch (Exception e) {
            return ResponseEntityHelper.fail(e.getMessage(), HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE);
        }
    }
}
