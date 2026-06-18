package com.sprint.mission.discodeit.sse.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Repository
public class SseEmitterRepository {

  private final ConcurrentMap<UUID, List<SseEmitter>> data = new ConcurrentHashMap<>();

  public void save(UUID userId, SseEmitter emitter) {
    data.computeIfAbsent(userId, k -> new ArrayList<>()).add(emitter);
  }

  public List<SseEmitter> get(UUID userId) {
    return data.getOrDefault(userId, new ArrayList<>());
  }

  public void remove(UUID userId, SseEmitter emitter) {
    List<SseEmitter> emitters = data.get(userId);
    if (emitters != null) {
      emitters.remove(emitter);
      if (emitters.isEmpty()) {
        data.remove(userId);
      }
    }
  }

  public Map<UUID, List<SseEmitter>> getAll() {
    return new HashMap<>(data);
  }

}
