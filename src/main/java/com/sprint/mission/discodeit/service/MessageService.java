package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.Message;

import java.util.List;
import java.util.UUID;

public interface MessageService {
    void createMessage(Message message);
    void deleteMessage(UUID id);
    Message findMessageById(UUID id);
    void updateMessage(UUID id, String newContent);
    List<Message> findAllMessages();
    List<Message> findMessagesByChannelId(UUID channelId);
}
