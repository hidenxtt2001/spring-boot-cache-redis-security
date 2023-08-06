package com.nxhung.redisjwt.services.user.mapper;

import com.nxhung.redisjwt.services.user.dtos.UserResponse;
import com.nxhung.redisjwt.services.user.entities.UserEntity;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {
    public UserResponse toDto(UserEntity user) {
        return UserResponse.builder().id(user.getId()).email(user.getEmail()).build();
    }
}
