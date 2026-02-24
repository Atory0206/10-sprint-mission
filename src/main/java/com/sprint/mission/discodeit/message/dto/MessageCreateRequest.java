package com.sprint.mission.discodeit.message.dto;

import java.util.List;
import java.util.UUID;

public record MessageCreateRequest(
    String content,
    UUID channelId,
    UUID authorId
) {

}
