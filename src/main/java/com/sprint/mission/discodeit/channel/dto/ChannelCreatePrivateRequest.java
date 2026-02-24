package com.sprint.mission.discodeit.channel.dto;

import com.sprint.mission.discodeit.channel.entity.ChannelType;

import java.util.List;
import java.util.UUID;

public record ChannelCreatePrivateRequest(
    List<UUID> participantIds

) {

}
