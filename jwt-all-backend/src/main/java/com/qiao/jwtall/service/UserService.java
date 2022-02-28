package com.qiao.jwtall.service;

import com.qiao.jwtall.domain.User;
import com.qiao.jwtall.dto.UserDTO;
import com.qiao.jwtall.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserDTO create(UserDTO userDTO) {
        User user = new User(userDTO.getUsername(), userDTO.getPassword(), userDTO.getPassword());
        userRepository.save(user);
        return new UserDTO(user.getUsername(), user.getEmail());
    }

    public List<UserDTO> list() {
        List<User> userList = userRepository.findAll();
        return userList.stream().map(user -> new UserDTO(user.getUsername(), user.getEmail())).collect(Collectors.toList());
    }

}
