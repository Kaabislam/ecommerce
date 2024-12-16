package com.kaab.ecommerce.service;

import com.kaab.ecommerce.entity.UserInfo;
import com.kaab.ecommerce.exception.BadRequestException;
import com.kaab.ecommerce.repository.UserInfoRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserInfoRepository repository;
    public String addUser(UserInfo userInfo) {
        if (userInfo == null) {
            throw new BadRequestException("Invalid user creation request.");
        }
        if (isExistingUser(userInfo.getName())) {
            throw new RuntimeException("Username '" + userInfo.getName() + "' already exists!");
        }
        repository.save(userInfo);
        return "user added to system ";
    }


    public boolean isExistingUser(String username) {
        return repository.findByName(username).isPresent();
    }

    public List<UserInfo> getAllUser() {
        return repository.findAll();
    }
}
