package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JCFUserService implements UserService {
    private final List<User> userList;
    public JCFUserService(){
        this.userList = new ArrayList<>();
    }

    @Override
    public void createUser(User user) {
        userList.add(user);
        System.out.println(user.getUsername() + "님 회원가입 완료되었습니다.");
    }

    @Override
    public User findUserById(UUID id){
        return userList.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자 없음"));
    }

    @Override
    public List<User> findAllUsers(){
        return userList;
    }

    @Override
    public void updateUser(UUID id, User user){
        User targetUser = findUserById(id);

        if(targetUser != null){
            targetUser.updateUserInfo(user.getUsername(), user.getEmail());
        } else {
            System.out.println("수정할 유저를 찾을 수 없습니다");
        }
    }

    public void deleteUser(UUID id){
        userList.removeIf(user -> user.getId().equals(id));
    }

    @Override
    public void changePassword(UUID id, String newPassword) {
        User targetUser = findUserById(id);
        if (targetUser != null) {
            targetUser.updatePassword(newPassword);
        } else {
            System.out.println("해당 유저를 찾을 수 없습니다.");
        }
    }
}
