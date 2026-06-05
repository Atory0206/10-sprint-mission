package com.sprint.mission.discodeit.notification.service;

import com.sprint.mission.discodeit.notification.dto.NotificationDto;
import java.util.List;
import java.util.UUID;

public interface NotificationService {

  List<NotificationDto> findNotificationsByReceiverId(UUID receiverId);

  void confirmNotification(UUID notificationId, UUID userId);

}
