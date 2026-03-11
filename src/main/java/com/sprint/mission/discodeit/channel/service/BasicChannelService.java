package com.sprint.mission.discodeit.channel.service;

import com.sprint.mission.discodeit.channel.dto.*;
import com.sprint.mission.discodeit.channel.entity.Channel;
import com.sprint.mission.discodeit.channel.entity.ChannelType;
import com.sprint.mission.discodeit.channel.mapper.ChannelMapper;
import com.sprint.mission.discodeit.channel.repository.JPAChannelRepository;
import com.sprint.mission.discodeit.message.entity.ReadStatus;
import com.sprint.mission.discodeit.message.repository.JPAMessageRepository;
import com.sprint.mission.discodeit.message.repository.JPAReadStatusRepository;
import com.sprint.mission.discodeit.user.dto.UserDto;
import com.sprint.mission.discodeit.user.entity.User;
import com.sprint.mission.discodeit.user.mapper.UserMapper;
import com.sprint.mission.discodeit.user.repository.JPAUserRepository;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BasicChannelService implements ChannelService {

  private final JPAChannelRepository jpaChannelRepository;
  private final JPAReadStatusRepository jpaReadStatusRepository;
  private final JPAMessageRepository jpaMessageRepository;
  private final JPAUserRepository jpaUserRepository;
  private final ChannelMapper channelMapper;
  private final UserMapper userMapper;

  @Override
  @Transactional
  public ChannelDto create(ChannelCreatePrivateRequest request) {
    Channel channel = new Channel(ChannelType.PRIVATE, null, null);
    Channel createdChannel = jpaChannelRepository.save(channel);

    List<User> participants = jpaUserRepository.findAllById(request.participantIds());

    List<ReadStatus> readStatuses = participants.stream()
        .map(user -> new ReadStatus(user, createdChannel, Instant.now()))
        .toList();

    jpaReadStatusRepository.saveAll(readStatuses);
    return channelMapper.toDto(createdChannel);
  }

  @Override
  @Transactional
  public ChannelDto create(ChannelCreatePublicRequest request) {
    Channel channel = new Channel(ChannelType.PUBLIC, request.name(), request.description());
    jpaChannelRepository.save(channel);
    return channelMapper.toDto(channel);
  }

  @Override
  @Transactional(readOnly = true)
  public ChannelDto find(UUID channelId) {
    Channel channel = jpaChannelRepository.findById(channelId)
        .orElseThrow(
            () -> new NoSuchElementException("Channel with id " + channelId + " not found"));
    return channelMapper.toDto(channel);
  }

  @Override
  @Transactional(readOnly = true)
  public List<ChannelDto> findAllByUserId(UUID userId) {
    List<UUID> subscribedIds = jpaReadStatusRepository.findAllByUserId(userId)
        .stream()
        .map(rs -> rs.getChannel().getId())
        .toList();

    List<Channel> channels = jpaChannelRepository
        .findByTypeOrIdIn(ChannelType.PUBLIC, subscribedIds);
    List<UUID> channelIds = channels.stream()
        .map(Channel::getId)
        .toList();

    Map<UUID, Instant> lastMessageMap = jpaMessageRepository
        .findLastMessageTimesByChannelIds(channelIds)
        .stream()
        .collect(Collectors.toMap(
            row -> (UUID) row[0],
            row -> (Instant) row[1]
        ));

    Map<UUID, List<UserDto>> participantMap = jpaReadStatusRepository
        .findAllByChannelIdIn(channelIds)
        .stream()
        .collect(Collectors.groupingBy(
            rs -> rs.getChannel().getId(),
            Collectors.mapping(rs -> userMapper.toDto(rs.getUser()), Collectors.toList())
        ));

    return channels.stream()
        .map(channel -> new ChannelDto(
            channel.getId(),
            channel.getType(),
            channel.getName(),
            channel.getDescription(),
            participantMap.getOrDefault(channel.getId(), Collections.emptyList()),
            lastMessageMap.getOrDefault(channel.getId(), Instant.MIN)
        ))
        .toList();
  }

  @Override
  @Transactional
  public ChannelDto update(UUID channelId, ChannelUpdateRequest request) {
    Channel channel = jpaChannelRepository.findById(channelId)
        .orElseThrow(() -> new NoSuchElementException("채널을 찾을 수 없습니다."));

    if (channel.getType() == ChannelType.PRIVATE) {
      throw new IllegalArgumentException("PRIVATE 채널은 수정할 수 없습니다");
    }

    channel.update(request.newName(), request.newDescription());
    return channelMapper.toDto(channel);
  }

  @Override
  @Transactional
  public void delete(UUID channelId) {
    Channel channel = jpaChannelRepository.findById(channelId)
        .orElseThrow(() -> new NoSuchElementException("Channel not found"));
    jpaChannelRepository.delete(channel);

  }
}
