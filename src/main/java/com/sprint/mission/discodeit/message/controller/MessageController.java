package com.sprint.mission.discodeit.message.controller;


import com.sprint.mission.discodeit.binarycontent.dto.BinaryContentCreateRequest;
import com.sprint.mission.discodeit.message.dto.MessageCreateRequest;
import com.sprint.mission.discodeit.message.dto.MessageResponse;
import com.sprint.mission.discodeit.message.dto.MessageUpdateRequest;
import com.sprint.mission.discodeit.message.entity.Message;
import com.sprint.mission.discodeit.message.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.html.Option;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/message")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Message> createMessage (@RequestPart MessageCreateRequest request,
                                                  @RequestPart(value = "attachments", required = false)
                                                  List<MultipartFile> attachments){

        List<BinaryContentCreateRequest> attachmentRequests = Optional.ofNullable(attachments)
                .map(files -> files.stream()
                        .map(file -> {
                            try {
                                return new BinaryContentCreateRequest(
                                        file.getOriginalFilename(),
                                        file.getContentType(),
                                        file.getBytes()
                                );
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        })
                        .toList())
                .orElse(new ArrayList<>());


        Message createdMessage = messageService.create(request,attachmentRequests);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdMessage);
    }

    @RequestMapping(method = RequestMethod.PATCH)
    public ResponseEntity<Message> updateMessage (
            @RequestParam UUID messageId,
            @RequestBody MessageUpdateRequest request){
        Message updatedMessage = messageService.update(messageId,request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedMessage);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteMessage(@RequestParam UUID id){
        messageService.delete(id);
        return ResponseEntity.
                status(HttpStatus.NO_CONTENT)
                .build();
    }

    @RequestMapping(method = RequestMethod.GET, params = "channelId")
    public ResponseEntity<List<Message>> findAllByChannelId(@RequestParam UUID channelId){
        List<Message> messages = messageService.findAllByChannelId(channelId);
        return ResponseEntity.
                status(HttpStatus.OK)
                .body(messages);
    }
}
