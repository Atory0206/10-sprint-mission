package com.sprint.mission.discodeit.message.service;

import com.sprint.mission.discodeit.message.dto.ReadStatusCreateRequest;
import com.sprint.mission.discodeit.message.dto.ReadStatusResponse;
import com.sprint.mission.discodeit.message.dto.ReadStatusUpdateRequest;
import com.sprint.mission.discodeit.message.entity.ReadStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface ReadStatusService {

  ReadStatus create(ReadStatusCreateRequest request);

  ReadStatus find(UUID id);

  List<ReadStatus> findAllByUserId(UUID userId);

  ReadStatus update(UUID readStatusId, ReadStatusUpdateRequest request);

  void delete(UUID id);
}
