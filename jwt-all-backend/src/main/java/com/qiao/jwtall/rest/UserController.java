package com.qiao.jwtall.rest;

import com.qiao.jwtall.dto.UserDTO;
import com.qiao.jwtall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Authentication needed
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("create")
    public UserDTO createUser(UserDTO userDTO) {
        return userService.create(userDTO);
    }

    @RequestMapping("list")
    public List<UserDTO> listUsers() {
        return userService.list();
    }

}
