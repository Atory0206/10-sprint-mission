package com.sprint.mission.discodeit.message.service;

import com.sprint.mission.discodeit.message.dto.ReadStatusCreateRequest;
import com.sprint.mission.discodeit.message.dto.ReadStatusResponse;
import com.sprint.mission.discodeit.message.dto.ReadStatusUpdateRequest;
import com.sprint.mission.discodeit.channel.entity.Channel;
import com.sprint.mission.discodeit.message.entity.ReadStatus;
import com.sprint.mission.discodeit.message.mapper.ReadStatusMapper;
import com.sprint.mission.discodeit.message.repository.ReadStatusRepository;
import com.sprint.mission.discodeit.user.entity.User;
import com.sprint.mission.discodeit.channel.repository.ChannelRepository;
import com.sprint.mission.discodeit.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class BasicReadStatusService implements ReadStatusService {

  private final UserRepository userRepository;
  private final ChannelRepository channelRepository;
  private final ReadStatusRepository readStatusRepository;
  private final ReadStatusMapper readStatusMapper;


  @Override
  public ReadStatus create(ReadStatusCreateRequest request) {
    UUID userId = request.userId();
    UUID channelId = request.channelId();

    if (!userRepository.existsById(userId)) {
      throw new NoSuchElementException("User with id " + userId + " does not exist");
    }
    if (!channelRepository.existsById(channelId)) {
      throw new NoSuchElementException("Channel with id " + channelId + " does not exist");
    }

    return readStatusRepository.findAllByUserId(userId).stream()
        .filter(readStatus -> readStatus.getChannelId().equals(channelId))
        .findFirst()
        .orElseGet(
            () -> {
              Instant lastReadAt = request.lastReadAt();
              ReadStatus readStatus = new ReadStatus(userId, channelId, lastReadAt);
              return readStatusRepository.save(readStatus);
            }
        );
  }

  @Override
  public ReadStatusResponse find(UUID id) {
    ReadStatus readStatus = readStatusRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("해당 읽음 객체가 존재하지 않습니다"));

    return readStatusMapper.convertToResponse(readStatus);
  }

  @Override
  public List<ReadStatus> findAllByUserId(UUID userId) {
    return readStatusRepository.findAllByUserId(userId).stream()
        .toList();

  }

  @Override
  public ReadStatus update(UUID readStatusId, ReadStatusUpdateRequest request) {
    Instant newLastReadAt = request.newLastReadAt();
    ReadStatus readStatus = readStatusRepository.findById(readStatusId)
        .orElseThrow(() -> new IllegalArgumentException("해당 읽음 객체가 존재하지 않습니다"));

    readStatus.updateLastRead();
    return readStatusRepository.save(readStatus);
  }

  @Override
  public void delete(UUID id) {
    readStatusRepository.deleteById(id);
  }
}
