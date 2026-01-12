package com.sprint.mission.discodeit.entity;

import java.util.UUID;

public class Message extends BaseEntity{
    private String content;
    private UUID channelId;
    private UUID userId;

    public String getContent() {
        return content;
    }

    public UUID getChannelId() {
        return channelId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void updateContent(String newContent){
        this.content = newContent;
        super.setUpdatedAt(System.currentTimeMillis());
    }

    public Message(String content, UUID channelId, UUID userId) {
        this.content = content;
        this.channelId = channelId;
        this.userId = userId;
    }


}
