package com.sprint.mission.discodeit.user.service;

import com.sprint.mission.discodeit.binarycontent.dto.BinaryContentCreateRequest;
import com.sprint.mission.discodeit.binarycontent.entity.BinaryContent;
import com.sprint.mission.discodeit.binarycontent.repository.JPABinaryContentRepository;
import com.sprint.mission.discodeit.storage.BinaryContentStorage;
import com.sprint.mission.discodeit.user.dto.UserCreateRequest;
import com.sprint.mission.discodeit.user.dto.UserDto;
import com.sprint.mission.discodeit.user.dto.UserUpdateRequest;
import com.sprint.mission.discodeit.user.entity.User;
import com.sprint.mission.discodeit.user.mapper.UserMapper;
import com.sprint.mission.discodeit.user.repository.JPAUserRepository;
import com.sprint.mission.discodeit.user.entity.UserStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BasicUserService implements UserService {

  private final JPAUserRepository jpaUserRepository;
  private final JPABinaryContentRepository JPABinaryContentRepository;
  private final PasswordEncoder passwordEncoder;
  private final UserMapper userMapper;
  private final BinaryContentStorage binaryContentStorage;


  @Override
  @Transactional
  public UserDto create(UserCreateRequest request,
      Optional<BinaryContentCreateRequest> optionalProfileCreateRequest) {

    if (jpaUserRepository.existsByUsername(request.username())) {
      throw new IllegalArgumentException("이미 존재하는 유저네임입니다.");
    }
    if (jpaUserRepository.existsByEmail(request.email())) {
      throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
    }

    BinaryContent profile = optionalProfileCreateRequest
        .map(profileRequest -> {
          BinaryContent binaryContent = new BinaryContent(
              profileRequest.fileName(),
              (long) profileRequest.bytes().length,
              profileRequest.contentType()
          );
          BinaryContent savedBinaryContent = JPABinaryContentRepository.save(binaryContent);
          binaryContentStorage.put(savedBinaryContent.getId(), profileRequest.bytes());
          return savedBinaryContent;
        })
        .orElse(null);

    String encodedPassword = passwordEncoder.encode(request.password());

    User user = new User(
        request.username(),
        request.email(),
        encodedPassword,
        profile
    );
    UserStatus userStatus = new UserStatus(user);
    user.setUserStatus(userStatus);

    User savedUser = jpaUserRepository.save(user);

    return userMapper.toDto(savedUser);
  }

  @Override
  @Transactional(readOnly = true)
  public UserDto find(UUID userId) {
    return jpaUserRepository.findById(userId)
        .map(userMapper::toDto)
        .orElseThrow(() -> new NoSuchElementException("User with id " + userId + " not found"));
  }

  @Override
  @Transactional(readOnly = true)
  public List<UserDto> findAll() {
    return jpaUserRepository.findAll()
        .stream()
        .map(userMapper::toDto)
        .toList();
  }

  @Override
  @Transactional
  public UserDto update(UUID userId, UserUpdateRequest request,
      Optional<BinaryContentCreateRequest> optionalProfileCreateRequest) {
    User user = jpaUserRepository.findById(userId)
        .orElseThrow(() -> new NoSuchElementException("User with id " + userId + " not found"));

    String name = Optional.ofNullable(request.newUsername()).orElse(user.getUsername());
    String email = Optional.ofNullable(request.newEmail()).orElse(user.getEmail());
    String password = Optional.ofNullable(request.newPassword())
        .map(passwordEncoder::encode)
        .orElse(user.getPassword());

    optionalProfileCreateRequest.ifPresent(profileRequest -> {
      BinaryContent newProfile = new BinaryContent(
          profileRequest.fileName(),
          (long) profileRequest.bytes().length,
          profileRequest.contentType()
      );
      BinaryContent savedBinaryContent = JPABinaryContentRepository.save(newProfile);
      binaryContentStorage.put(savedBinaryContent.getId(), profileRequest.bytes());
      user.setProfile(savedBinaryContent);
    });

    user.update(name, email, password);
    return userMapper.toDto(user);
  }

  @Override
  @Transactional
  public void delete(UUID userId) {
    User user = jpaUserRepository.findById(userId)
        .orElseThrow(() -> new NoSuchElementException("User with id " + userId + " not found"));
    jpaUserRepository.delete(user);
  }
}


