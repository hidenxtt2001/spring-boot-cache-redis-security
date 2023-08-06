package com.nxhung.redisjwt;

import com.github.javafaker.Faker;
import com.nxhung.redisjwt.services.role.RoleRepository;
import com.nxhung.redisjwt.services.role.entities.RoleEntity;
import com.nxhung.redisjwt.services.user.UserRepository;
import com.nxhung.redisjwt.services.user.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

@Configuration
@RequiredArgsConstructor
public class FakeData {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner runner() {
        return args -> {
            // Init Roles
            RoleEntity roleAdmin = RoleEntity.builder().name("ADMIN").build();
            RoleEntity roleUser = RoleEntity.builder().name("USER").build();
            roleRepository.saveAll(List.of(roleAdmin, roleUser));

            // Init User
            List<UserEntity> users = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                var user = UserEntity.builder()
                        .email(Faker.instance().internet().emailAddress())
                        .password(passwordEncoder.encode("123456"))
                        .name(Faker.instance().name().fullName())
                        .roles(Set.of(roleUser)).build();
                users.add(user);
            }
            var user = UserEntity.builder()
                    .email("example@gmail.com")
                    .password(passwordEncoder.encode("123456"))
                    .name(Faker.instance().name().fullName())
                    .roles(Set.of(roleAdmin)).build();
            userRepository.save(user);
            userRepository.saveAll(users);

        };
    }
}
