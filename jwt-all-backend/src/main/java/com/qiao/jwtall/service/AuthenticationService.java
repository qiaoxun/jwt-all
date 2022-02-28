package com.qiao.jwtall.service;

import com.qiao.jwtall.domain.User;
import com.qiao.jwtall.dto.UserDTO;
import com.qiao.jwtall.exception.CustomException;
import com.qiao.jwtall.repository.UserRepository;
import com.qiao.jwtall.security.JWTTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTTokenProvider jwtTokenProvider;

    public String login(String username, String password) {
        try {
            User user = userRepository.findByUsernameAndPassword(username, password);
            UserDTO userDTO = new UserDTO(user.getUsername(), user.getEmail());
            return jwtTokenProvider.createToken(userDTO);
        } catch (Exception e) {
            throw new CustomException("Invalid username/password supplied", HttpStatus.BAD_REQUEST);
        }
    }

}
