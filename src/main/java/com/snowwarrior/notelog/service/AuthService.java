package com.snowwarrior.notelog.service;

import com.snowwarrior.notelog.auth.JwtUser;
import com.snowwarrior.notelog.constant.UserRoleConstants;
import com.snowwarrior.notelog.dto.UserDTO;
import com.snowwarrior.notelog.dto.UserLoginDTO;
import com.snowwarrior.notelog.model.User;
import com.snowwarrior.notelog.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

import static com.snowwarrior.notelog.constant.UserRoleConstants.ROLE_USER;

@Service
public class AuthService {

    private final UserService userService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public AuthService(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public JwtUser authLogin(UserLoginDTO userLogin) {
        String username = userLogin.getUsername();
        String password = userLogin.getPassword();

        Optional<User> userOptional = userService.getUserByName(username);
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        User user = userOptional.get();
        if (this.bCryptPasswordEncoder.matches(password, user.getPassword())) {
            String token = JwtUtils.generateToken(username, ROLE_USER);

            UserDTO userDTO = new UserDTO();
            userDTO.setUsername(username);

            return new JwtUser(token, userDTO);
        }
        throw new BadCredentialsException("The username or password error.");
    }
}
