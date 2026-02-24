package com.sprint.mission.discodeit.user.service;


import com.sprint.mission.discodeit.binarycontent.dto.BinaryContentCreateRequest;
import com.sprint.mission.discodeit.user.dto.UserDto;
import com.sprint.mission.discodeit.user.dto.UserUpdateRequest;
import com.sprint.mission.discodeit.user.dto.UserCreateRequest;
import com.sprint.mission.discodeit.user.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {

  User create(UserCreateRequest request,
      Optional<BinaryContentCreateRequest> optionalProfileCreateRequest);

  UserDto find(UUID userId);

  List<UserDto> findAll();

  User update(UUID userId, UserUpdateRequest request,
      Optional<BinaryContentCreateRequest> optionalProfileCreateRequest);

  void delete(UUID userId);
}
