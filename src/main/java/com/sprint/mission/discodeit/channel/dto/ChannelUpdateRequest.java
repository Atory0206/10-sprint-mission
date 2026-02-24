package com.sprint.mission.discodeit.channel.dto;

import java.util.UUID;

public record ChannelUpdateRequest(
    String newName,
    String newDescription
) {

}
