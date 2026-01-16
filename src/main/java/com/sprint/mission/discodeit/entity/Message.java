package com.sprint.mission.discodeit.entity;

import java.util.UUID;

public class Message extends BaseEntity {
    private final Channel channel;
    private final User user;
    private String content;


    public Message (User user, Channel channel, String content) {
        super();
        this.user = user;
        this.channel = channel;
        this.content = content;
    }

    public void setContent(String setMessage) {
        content = setMessage;
        setUpdatedAt(System.currentTimeMillis());
    }

    public String getContent() {
        return content;
    }

    public Channel getChannel() {
        return channel;
    }

    public UUID getChannelId() { return channel.getId(); }
    public UUID getUserId() { return user.getId(); }

}
