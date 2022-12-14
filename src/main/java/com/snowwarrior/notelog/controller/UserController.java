package com.snowwarrior.notelog.controller;

import com.snowwarrior.notelog.dto.Response;
import com.snowwarrior.notelog.dto.UserDTO;
import com.snowwarrior.notelog.dto.UserRegisterDTO;
import com.snowwarrior.notelog.service.UserService;
import com.snowwarrior.notelog.util.ResponseEntityHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;

import static com.snowwarrior.notelog.constant.Exception.NOT_ACCEPTABLE;
import static com.snowwarrior.notelog.constant.Exception.REQUEST_PROBLEM;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/hello")
    public ResponseEntity<Response<String>> hello() {
        return ResponseEntityHelper.ok("success", "username", userService.hello());
    }

    @PostMapping("/register")
    public ResponseEntity<Response<String>> register(@RequestBody @Valid UserRegisterDTO userRegister) {
        try {
            userService.register(userRegister);
            return ResponseEntityHelper.ok("registration success");
        } catch (Exception e) {
            return ResponseEntityHelper.fail(e.getMessage(), HttpStatus.NOT_ACCEPTABLE, NOT_ACCEPTABLE);
        }

    }

    @GetMapping("/profile")
    public ResponseEntity<Response<UserDTO>> profile() {
        try {
            var result = userService.profile();
            return ResponseEntityHelper.ok("success", "profile", result);
        } catch (Exception e) {
            return ResponseEntityHelper.fail(e.getMessage(), HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE, REQUEST_PROBLEM);
        }
    }
}
