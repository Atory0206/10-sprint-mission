package com.sprint.mission.discodeit.jwt;

import java.util.Map;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

@Component
public class InMemoryJwtRegistry implements JwtRegistry {

  //private final Map<UUID, Queue<JwtInformation>> origin = new ConcurrentHashMap<>();
  //private final int maxActiveJwtCount;

  @Override
  public void registerJwtInformation(JwtInformation jwtInformation) {

  }

  @Override
  public void invalidateJwtInformation(UUID userId) {

  }

  @Override
  public boolean hasActiveJwtInformationByUserId(UUID userId) {
    return false;
  }

  @Override
  public boolean hasActiveJwtInformationByAccessToken(String accessToken) {
    return true;
  }

  @Override
  public boolean hasActiveJwtInformationByRefreshToken(String refreshToken) {
    return false;
  }

  @Override
  public void rotateJwtInformation(String refreshToken, JwtInformation newJwtInformation) {

  }

  @Override
  public void clearExpiredJwtInformation() {

  }
}
