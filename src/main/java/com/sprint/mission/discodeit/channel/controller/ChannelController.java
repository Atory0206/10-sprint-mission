package com.sprint.mission.discodeit.channel.controller;

import com.sprint.mission.discodeit.channel.dto.*;
import com.sprint.mission.discodeit.channel.entity.Channel;
import com.sprint.mission.discodeit.channel.service.ChannelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/channel")
@RequiredArgsConstructor
public class ChannelController {
    private final ChannelService channelService;

    @RequestMapping(method = RequestMethod.POST,
            params = "type=PUBLIC")
    public ResponseEntity<Channel> createPublicChannel (@RequestBody ChannelCreatePublicRequest request){
        Channel createdChannel = channelService.create(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdChannel);
    }

    @RequestMapping(method = RequestMethod.POST,
            params = "type=PRIVATE")
    public ResponseEntity<Channel> createPrivateChannel(@RequestBody ChannelCreatePrivateRequest request){
        Channel createdChannel =  channelService.create(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdChannel);
    }

    @RequestMapping(method = RequestMethod.PATCH)
    public ResponseEntity<Channel> updatePublicChannel(@RequestBody ChannelUpdateRequest request) {
        Channel updatedChannel = channelService.update(request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedChannel);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteChannel(@RequestParam("channelId") UUID channelId) {
        channelService.delete(channelId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ChannelDto>> findAll(@RequestParam("userId") UUID userId) {
        List<ChannelDto> channels = channelService.findAllByUserId(userId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(channels);
    }

}
