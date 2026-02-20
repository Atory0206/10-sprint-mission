package com.sprint.mission.discodeit.user.mapper;

import com.sprint.mission.discodeit.user.dto.UserDto;
import com.sprint.mission.discodeit.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserDto convertToDto (User user, Boolean isOnline){
        return new UserDto(
                user.getId(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                user.getUsername(),
                user.getEmail(),
                user.getProfileId(),
                user.isOnline()
        );
    }
}
