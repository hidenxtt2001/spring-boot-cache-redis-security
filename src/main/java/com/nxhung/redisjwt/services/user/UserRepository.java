package com.nxhung.redisjwt.services.user;

import com.nxhung.redisjwt.services.user.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    boolean deleteUserById(Long id);

    Optional<UserEntity> findUserById(Long id);

    Optional<UserEntity> findUserByEmail(String email);
}
