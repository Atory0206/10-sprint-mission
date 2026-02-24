package com.sprint.mission.discodeit.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserCreateRequest(
    String username,
    String email,
    String password
) {

}

