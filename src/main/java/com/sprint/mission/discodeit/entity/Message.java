package com.sprint.mission.discodeit.entity;

import java.util.UUID;

public class Message extends BaseEntity {
    private final Channel channel;
    private final User user;
    private String content;
    private final long sentAt;

    public Message (User user, Channel channel, String content) {
        super();
        this.user = user;
        this.channel = channel;
        this.content = content;
        this.sentAt = System.currentTimeMillis();
    }

    public void setContent(String setMessage) {
        content = setMessage;
        setUpdatedAt(System.currentTimeMillis());
    }

    public String getContent() {
        return content;
    }

    public long getSentAt() {
        return sentAt;
    }

    public Channel getChannel() {
        return channel;
    }

    public UUID getChannelId() { return channel.getId(); }
    public UUID getUserId() { return user.getId(); }

}
