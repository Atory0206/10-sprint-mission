package com.sprint.mission.discodeit.message.dto;

import jakarta.validation.constraints.NotBlank;
import java.util.List;
import java.util.UUID;

public record MessageCreateRequest(
    @NotBlank(message = "메시지내용 은 비어 있을 수 없습니다.")
    String content,
    @NotBlank(message = "채널ID는 비어 있을 수 없습니다.")
    UUID channelId,
    @NotBlank(message = "유저ID는 비어 있을 수 없습니다.")
    UUID authorId
) {

}
