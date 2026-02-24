package com.sprint.mission.discodeit.binarycontent.repository;

import com.sprint.mission.discodeit.binarycontent.entity.BinaryContent;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface BinaryContentRepository {

  Optional<BinaryContent> findById(UUID id);

  BinaryContent save(BinaryContent binaryContent);

  void deleteById(UUID id);

  List<BinaryContent> findAllByIdIn(List<UUID> ids);
}
