package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.UserService;

import java.time.LocalDate;
import java.util.*;

public class JCFUserService implements UserService {
    private final Map<UUID, User> userStore;

    public JCFUserService(){
        this.userStore = new HashMap<>();
    }

    public User registerUser(String name, String email, java.time.LocalDate birthDate, String phoneNumber, String password){
            if(userStore.values().stream()
                    .anyMatch(u -> u.getEmail().equals(email))) {
                throw new IllegalArgumentException("다른 유저가 사용 중인 이메일입니다");
            }

            User newUser = new User(name, email, birthDate, phoneNumber, password);
            userStore.put(newUser.getId(), newUser);
            return newUser;
        }
     public User findUserById(UUID userId) {
         User user = userStore.get(userId);
         if(user == null){
             throw new IllegalArgumentException("해당 유저를 찾을 수 없습니다");
         }

            return userStore.get(userId);
        }

    @Override
    public List<User> findAllUser() {
        return new ArrayList<>(userStore.values());
    }

    public void deleteUser(UUID userId){
        User user = userStore.get(userId);
        if(user == null){
            throw new IllegalArgumentException("유저를 찾을 수 없습니다");
        }

        userStore.remove(userId);
        }

    public int userCount () {return userStore.size();}

    public User updateName (UUID userId,String newName){
        User user = userStore.get(userId);

        if (user == null) {
            throw new IllegalArgumentException("유저를 찾을 수 없습니다");
        }
        if (user.getName().equals(newName)) {
            throw new IllegalArgumentException("현재 사용 중인 이름입니다");
        }

        user.setName(newName);
        user.setUpdatedAt(System.currentTimeMillis());
        return user;
    }

    public User updateEmail (UUID userId, String newEmail) {
        User user = userStore.get(userId);
        if (user == null) {
            throw new IllegalArgumentException("유저를 찾을 수 없습니다");
        }

        if(user.getEmail().equals(newEmail)){
            throw new IllegalArgumentException("현재 사용 중인 이메일입니다");
        }

        if (userStore.values().stream()
                .anyMatch(u -> !u.getId().equals(userId) && u.getEmail().equals(newEmail))) {
                 throw new IllegalArgumentException("다른 유저가 사용 중인 이메일입니다");
        }
        user.setEmail(newEmail);
        user.setUpdatedAt(System.currentTimeMillis());
        return user;
    }

    public User updatePhoneNumber(UUID userId, String newPhoneNumber) {
        User user = userStore.get(userId);
        if(user == null) {
            throw new IllegalArgumentException("유저를 찾을 수 없습니다");
        }

        if(user.getPhoneNumber().equals(newPhoneNumber)) {
            throw new IllegalArgumentException("현재 사용 중인 전화번호입니다");
        }

        user.setPhoneNumber(newPhoneNumber);
        user.setUpdatedAt(System.currentTimeMillis());
        return user;
    }

    public User updatePassword(UUID userId, String newPassword) {
        User user = userStore.get(userId);
        if(user == null) {
            throw new IllegalArgumentException("유저를 찾을 수 없습니다");
        }

        if(user.getPassword().equals(newPassword)) {
            throw new IllegalArgumentException("현재 사용 중인 비밀번호입니다");
        }

        user.setPassword(newPassword);
        user.setUpdatedAt(System.currentTimeMillis());
        return user;
    }
}
