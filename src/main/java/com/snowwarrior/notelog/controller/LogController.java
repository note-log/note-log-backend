package com.snowwarrior.notelog.controller;

import com.snowwarrior.notelog.dto.Response;
import com.snowwarrior.notelog.model.Log;
import com.snowwarrior.notelog.service.LogService;
import com.snowwarrior.notelog.util.ResponseEntityHelper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/log")
public class LogController {
    private final LogService logService;

    public LogController(LogService logService) {
        this.logService = logService;
    }
    @GetMapping("")
    public ResponseEntity<Response<List<Log>>> getLog() {
        try {
            var logs = logService.getLog();
            return ResponseEntityHelper.ok("success", "logs", logs);
        } catch (Exception e) {
            return ResponseEntityHelper.fail(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
