package com.sprint.mission.discodeit.entity;

public class Channel extends BaseEntity{
    private String channelName;

    public String getChannelName() {
        return channelName;
    }

    public void updateChannelInfo(String newChannelName){
        this.channelName = newChannelName;
    }

    public Channel(String channelName) {
        this.channelName = channelName;
    }
}
