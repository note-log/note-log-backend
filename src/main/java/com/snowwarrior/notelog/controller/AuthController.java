package com.snowwarrior.notelog.controller;

import com.snowwarrior.notelog.auth.JwtUser;
import com.snowwarrior.notelog.dto.Response;
import com.snowwarrior.notelog.dto.UserLoginDTO;
import com.snowwarrior.notelog.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<Response<String>> login(@RequestBody UserLoginDTO userLogin) {
        try {
            JwtUser jwtUser = authService.authLogin(userLogin);

            HttpHeaders httpHeaders = new HttpHeaders();

            httpHeaders.set("Authorization", "Bearer " + jwtUser.getToken());
            var map = new HashMap<String, String>();
            map.put("token", jwtUser.getToken());
            return new ResponseEntity<>(new Response<>(1, "login success", map), httpHeaders, HttpStatus.OK);
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(new Response<>(-1, e.getMessage(), new HashMap<>()), HttpStatus.BAD_REQUEST);
        }
    }
}
