package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    void createUser(User user);

    User findUserById(UUID id);

    List<User> findAllUsers();

    void updateUser(UUID id, User user);

    void changePassword(UUID id, String newPassword);

    void deleteUser(UUID id);

}
