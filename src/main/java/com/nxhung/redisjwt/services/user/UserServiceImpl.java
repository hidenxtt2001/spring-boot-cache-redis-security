package com.nxhung.redisjwt.services.user;

import com.nxhung.redisjwt.exceptions.NotFoundException;
import com.nxhung.redisjwt.services.user.dtos.UserResponse;
import com.nxhung.redisjwt.services.user.dtos.UserUpdateRequest;
import com.nxhung.redisjwt.services.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper mapper;

    @Override
    public List<UserResponse> getAllUsers() {
        var users = userRepository.findAll();
        return users.stream().map(mapper::toDto).toList();
    }

    @Override
    public UserResponse findUserById(Long id) throws NotFoundException {

        var user = userRepository.findUserById(id);
        if (user.isEmpty()) {
            throw new NotFoundException();
        } else {
            return mapper.toDto(user.get());
        }

    }

    @Override
    public void updateUserById(Long id, UserUpdateRequest data) throws NotFoundException {

        var user = userRepository.findUserById(id);
        if (user.isEmpty()) {
            throw new NotFoundException();
        } else {
            var userData = user.get();
            BeanUtils.copyProperties(data, userData);
            userRepository.save(userData);
        }
    }
}
