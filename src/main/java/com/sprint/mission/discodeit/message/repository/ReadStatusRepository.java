package com.sprint.mission.discodeit.message.repository;

import com.sprint.mission.discodeit.message.entity.ReadStatus;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReadStatusRepository {
    Optional<ReadStatus> findById(UUID id);
    List<ReadStatus> findAllByUserId(UUID userId);
    List<ReadStatus> findAllByChannelId(UUID channelId);
    List<ReadStatus> findAll();
    ReadStatus save(ReadStatus ReadStatus);
    void deleteById(UUID id);
}
