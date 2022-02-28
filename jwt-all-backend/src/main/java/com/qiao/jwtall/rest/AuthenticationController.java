package com.qiao.jwtall.rest;

import com.qiao.jwtall.dto.UserDTO;
import com.qiao.jwtall.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @RequestMapping("login")
    public String login(@RequestBody UserDTO userDTO) {
        return authenticationService.login(userDTO.getUsername(), userDTO.getPassword());
    }

}
