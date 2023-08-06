package com.nxhung.redisjwt.services.user.dtos;

import com.nxhung.redisjwt.services.user.entities.UserEntity;
import lombok.Builder;

/**
 * DTO for {@link UserEntity}
 */

@Builder
public record UserResponse(Long id,
                           String email) {
}