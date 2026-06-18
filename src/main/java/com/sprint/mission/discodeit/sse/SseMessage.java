package com.sprint.mission.discodeit.sse;

import java.util.UUID;

public record SseMessage(UUID id, String name, Object data) {

}