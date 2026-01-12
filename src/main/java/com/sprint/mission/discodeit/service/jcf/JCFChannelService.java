package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.service.ChannelService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JCFChannelService implements ChannelService {
    private final List<Channel> channelList;

    public JCFChannelService(){
        this.channelList = new ArrayList<>();
    }
    @Override
    public void createChannel(Channel channel) {
        channelList.add(channel);
        System.out.println(channel.getChannelName() + "채널 생성이 완료되었습니다.");
    }

    @Override
    public Channel findChannelById(UUID id){
        return channelList.stream()
                .filter(channel -> channel.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 채널 없음"));
    }

    @Override
    public List<Channel> findAllChannels(){
        return channelList;
    }

    @Override
    public void deleteChannel(UUID id){
        channelList.removeIf(channel -> channel.getId().equals(id));
    }

    public void updateChannel(UUID id, Channel channelName){
        Channel targetChannel = findChannelById(id);

        if(targetChannel != null){
            targetChannel.updateChannelInfo(channelName.getChannelName());
        } else {
            System.out.println("수정할 채널을 찾을 수 없습니다.");
        }
    }

}
