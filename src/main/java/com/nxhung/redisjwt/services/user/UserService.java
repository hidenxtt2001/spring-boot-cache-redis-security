package com.nxhung.redisjwt.services.user;

import com.nxhung.redisjwt.exceptions.NotFoundException;
import com.nxhung.redisjwt.services.user.dtos.UserResponse;
import com.nxhung.redisjwt.services.user.dtos.UserUpdateRequest;

import java.util.List;

public interface UserService {
    List<UserResponse> getAllUsers();

    UserResponse findUserById(Long id) throws NotFoundException;

    void updateUserById(Long id, UserUpdateRequest data) throws NotFoundException;
}
