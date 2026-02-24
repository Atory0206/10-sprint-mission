package com.sprint.mission.discodeit.message.controller;


import com.sprint.mission.discodeit.binarycontent.dto.BinaryContentCreateRequest;
import com.sprint.mission.discodeit.message.dto.MessageCreateRequest;
import com.sprint.mission.discodeit.message.dto.MessageResponse;
import com.sprint.mission.discodeit.message.dto.MessageUpdateRequest;
import com.sprint.mission.discodeit.message.entity.Message;
import com.sprint.mission.discodeit.message.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Tag(name = "Message")
@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {

  private final MessageService messageService;

  @Operation(summary = "Message 생성",
      operationId = "create_2")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201",
          description = "Message가 성공적으로 생성됨"),
      @ApiResponse(responseCode = "404",
          description = "Channel 또는 User를 찾을 수 없음",
          content = @Content(
              examples = @ExampleObject(value = "Channel | Author with id {channelId | authorId} not found")
          ))
  }
  )
  @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<Message> createMessage(
      @RequestPart
      @Parameter(description = "Message 생성 정보")
      MessageCreateRequest messageCreateRequest,

      @Parameter(description = "Message 첨부 파일들")
      @RequestPart(value = "attachments", required = false)
      List<MultipartFile> attachments) {

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

    Message createdMessage = messageService.create(messageCreateRequest, attachmentRequests);

    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(createdMessage);
  }

  @Operation(summary = "Message 내용 수정",
      operationId = "update_2")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200",
          description = "Message가 성공적으로 수정됨"),
      @ApiResponse(responseCode = "404",
          description = "Message를 찾을 수 없음",
          content = @Content(
              examples = @ExampleObject(value = "Message with id {messageId} not found")
          )
      )
  })

  @PatchMapping("/{messageId}")
  public ResponseEntity<Message> updateMessage(
      @Parameter(description = "수정할 Message ID")
      @PathVariable UUID messageId,
      @Parameter(description = "수정할 Message 내용")
      @RequestBody MessageUpdateRequest request) {
    Message updatedMessage = messageService.update(messageId, request);
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(updatedMessage);
  }

  @Operation(summary = "Message 삭제",
      operationId = "delete_1")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204",
          description = "Message가 성공적으로 삭제됨"),
      @ApiResponse(responseCode = "404",
          description = "Message를 찾을 수 없음",
          content = @Content(
              examples = @ExampleObject(value = "Message with id {messageId} not found")
          )
      )}
  )
  @DeleteMapping("/{messageId}")
  public ResponseEntity<Void> deleteMessage(
      @Parameter(description = "삭제할 Message ID")
      @PathVariable UUID messageId) {
    messageService.delete(messageId);
    return ResponseEntity.
        status(HttpStatus.NO_CONTENT)
        .build();
  }

  @Operation(summary = "Channel의 Message 목록 조회")
  @ApiResponses(
      @ApiResponse(
          responseCode = "200",
          description = "Message 목록 조회 성공"
      )
  )
  @GetMapping
  public ResponseEntity<List<Message>> findAllByChannelId(
      @Parameter(description = "조회할 Channel ID") @RequestParam UUID channelId) {
    List<Message> messages = messageService.findAllByChannelId(channelId);
    return ResponseEntity.
        status(HttpStatus.OK)
        .body(messages);
  }
}
