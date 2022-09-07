package com.snowwarrior.notelog.controller;

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

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/hello")
    public ResponseEntity<HashMap<String, String>> hello() {
        return ResponseEntityHelper.ok(userService.hello());
    }

    @PostMapping("/register")
    public ResponseEntity<HashMap<String, String>> register(@RequestBody @Valid UserRegisterDTO userRegister) {
        userService.register(userRegister);
        return ResponseEntityHelper.ok("注册成功");
    }

    @GetMapping("/profile")
    public ResponseEntity<UserDTO> profile() {
        var result = userService.profile();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
