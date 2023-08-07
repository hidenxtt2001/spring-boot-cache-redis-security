package com.nxhung.redisjwt.controllers.user;

import com.nxhung.redisjwt.services.user.UserRepository;
import com.nxhung.redisjwt.services.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component("userSecurity")
@RequiredArgsConstructor
public class UserSecurity {
     private final UserService userService;
    public boolean hasUserId(Authentication authentication, Long id) {
        var userData = userService.findUserById(id);
        return authentication.getName().equals(userData.email());
    }

}
