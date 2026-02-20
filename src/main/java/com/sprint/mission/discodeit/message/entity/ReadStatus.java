package com.sprint.mission.discodeit.message.entity;

import lombok.Getter;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Getter
public class ReadStatus implements Serializable {
    private static final long serialVersionUID = 1L;

    private UUID id;
    private Instant createdAt;
    private Instant updatedAt;

    private UUID userId;
    private UUID channelId;
    private Instant lastReadAt;

    public ReadStatus(UUID userId, UUID channelId, Instant lastReadAt){
        this.userId = userId;
        this.channelId = channelId;
        this.id = UUID.randomUUID();
        this.createdAt = Instant.now();
        this.lastReadAt = lastReadAt;
    }

    public void updateLastRead() {
        this.lastReadAt = Instant.now();
        this.updatedAt = this.lastReadAt;
    }
}
