package com.sprint.mission.discodeit.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "로그인 정보")
public record UserLoginRequest(
    String username,
    String password
) {

}
