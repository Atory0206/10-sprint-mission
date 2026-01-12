package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.Channel;
import java.util.List;
import java.util.UUID;

public interface ChannelService {
    void createChannel(Channel channelName);

    void deleteChannel(UUID id);

    Channel findChannelById(UUID id);
    List<Channel> findAllChannels();

    void updateChannel(UUID id, Channel channelName);


}
