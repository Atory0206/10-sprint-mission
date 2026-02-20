package com.sprint.mission.discodeit.channel.service;

import com.sprint.mission.discodeit.channel.dto.*;
import com.sprint.mission.discodeit.channel.entity.Channel;

import java.util.List;
import java.util.UUID;

public interface ChannelService {
    Channel create(ChannelCreatePublicRequest request);
    Channel create(ChannelCreatePrivateRequest request);
    ChannelDto find(UUID channelId);
    List<ChannelDto> findAllByUserId(UUID userId);
    Channel update(ChannelUpdateRequest request);
    void delete(UUID channelId);
}
