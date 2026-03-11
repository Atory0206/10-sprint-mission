package com.sprint.mission.discodeit.message.service;

import com.sprint.mission.discodeit.binarycontent.dto.BinaryContentCreateRequest;
import com.sprint.mission.discodeit.binarycontent.entity.BinaryContent;
import com.sprint.mission.discodeit.binarycontent.repository.JPABinaryContentRepository;
import com.sprint.mission.discodeit.channel.entity.Channel;
import com.sprint.mission.discodeit.message.dto.MessageCreateRequest;
import com.sprint.mission.discodeit.message.dto.MessageDto;
import com.sprint.mission.discodeit.message.dto.MessageUpdateRequest;
import com.sprint.mission.discodeit.message.entity.Message;
import com.sprint.mission.discodeit.message.mapper.MessageMapper;
import com.sprint.mission.discodeit.message.repository.JPAMessageRepository;
import com.sprint.mission.discodeit.channel.repository.JPAChannelRepository;
import com.sprint.mission.discodeit.paging.dto.PageResponse;
import com.sprint.mission.discodeit.paging.mapper.PageResponseMapper;
import com.sprint.mission.discodeit.storage.BinaryContentStorage;
import com.sprint.mission.discodeit.user.entity.User;
import com.sprint.mission.discodeit.user.repository.JPAUserRepository;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;


import java.util.*;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BasicMessageService implements MessageService {

  private final JPAMessageRepository jpaMessageRepository;
  private final JPAChannelRepository jpaChannelRepository;
  private final JPAUserRepository jpaUserRepository;
  private final MessageMapper messageMapper;
  private final BinaryContentStorage binaryContentStorage;
  private final JPABinaryContentRepository jpaBinaryContentRepository;
  private final PageResponseMapper pageResponseMapper;

  @Override
  @Transactional
  public MessageDto create(MessageCreateRequest request,
      List<BinaryContentCreateRequest> binaryContentCreateRequests) {

    Channel channel = jpaChannelRepository.findById(request.channelId())
        .orElseThrow(() -> new NoSuchElementException("Channel not found"));
    User author = jpaUserRepository.findById(request.authorId())
        .orElseThrow(() -> new NoSuchElementException("User not found"));

    List<BinaryContent> attachments = binaryContentCreateRequests.stream()
        .map(req -> {
          BinaryContent binaryContent = new BinaryContent(
              req.fileName(),
              (long) req.bytes().length,
              req.contentType()
          );
          BinaryContent savedBinaryContent = jpaBinaryContentRepository.save(binaryContent);
          binaryContentStorage.put(savedBinaryContent.getId(), req.bytes());
          return savedBinaryContent;
        })
        .toList();

    Message message = new Message(request.content(), channel, author,
        attachments);
    Message savedMessage = jpaMessageRepository.save(message);
    return messageMapper.toDto(savedMessage);
  }

  @Override
  @Transactional(readOnly = true)
  public MessageDto find(UUID messageId) {
    return jpaMessageRepository.findById(messageId)
        .map(messageMapper::toDto)
        .orElseThrow(
            () -> new NoSuchElementException("Message with id " + messageId + " not found"));
  }

  @Override
  @Transactional(readOnly = true)
  public PageResponse<MessageDto> findAllByChannelId(UUID channelId, Instant cursor,
      Pageable pageable) {
    Slice<MessageDto> slice = jpaMessageRepository
        .findByChannelIdWithCursor(channelId, cursor, pageable)
        .map(messageMapper::toDto);

    Object nextCursor = slice.hasNext() && !slice.getContent().isEmpty()
        ? slice.getContent().get(slice.getContent().size() - 1).createdAt()
        : null;

    return pageResponseMapper.slice(slice, nextCursor);
  }

  @Override
  @Transactional
  public MessageDto update(UUID messageId, MessageUpdateRequest request) {
    Message message = jpaMessageRepository.findById(messageId)
        .orElseThrow(() -> new NoSuchElementException("Message not found"));
    message.update(request.newContent());
    return messageMapper.toDto(message);
  }

  @Override
  @Transactional
  public void delete(UUID messageId) {
    Message message = jpaMessageRepository.findById(messageId)
        .orElseThrow(() -> new NoSuchElementException("Message not found"));
    jpaMessageRepository.delete(message);
  }

}
