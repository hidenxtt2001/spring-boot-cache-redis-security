package com.nxhung.redisjwt.controllers.user;

import com.nxhung.redisjwt.services.user.UserService;
import com.nxhung.redisjwt.services.user.dtos.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Collection;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or @userSecurity.hasUserId(authentication,#id)")
    public UserResponse getUserById(@PathVariable("id") Long id) {
        return userService.findUserById(id);
    }


    @GetMapping()
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Collection<UserResponse> getUsers() {
        return userService.getAllUsers();
    }
}
