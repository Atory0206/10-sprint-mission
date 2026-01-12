package com.sprint.mission.discodeit;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.UserService;
import com.sprint.mission.discodeit.service.jcf.JCFChannelService;
import com.sprint.mission.discodeit.service.jcf.JCFMessageService;
import com.sprint.mission.discodeit.service.jcf.JCFUserService;

import java.util.List;
import java.util.UUID;

public class JavaApplication {
    public static void main(String[] args) {
        System.out.println("========== 디스코드 서비스 테스트 시작 ==========\n");

        UserService userService = new JCFUserService();
        ChannelService channelService = new JCFChannelService();

        // User 테스트
        System.out.println("유저 기능 테스트");

        // 등록
        User newUser = new User("jiho0420","1234" , "jiho@codeit.kr");
        userService.createUser(newUser);
        UUID createdUserId = newUser.getId();

        // 단건 조회
        System.out.println("단건 조회 시도:");
        User foundUser = userService.findUserById(createdUserId);
        System.out.println("조회된 유저: " + foundUser.getUsername());

        // 다건 조회
        System.out.println("전체 조회 시도:");
        List<User> allUsers = userService.findAllUsers();
        System.out.println("총 유저 수: " + allUsers.size() + "명");

        // 수정
        System.out.println(">> 유저 정보 수정 시도:");

        User updateData = new User("SuperJiho", null, "super@codeit.kr");
        userService.updateUser(createdUserId, updateData);

        // 수정된 데이터 조회
        User updatedUser = userService.findUserById(createdUserId);
        System.out.println("수정된 이름: " + updatedUser.getUsername());
        System.out.println("수정된 이메일: " + updatedUser.getEmail());

        // 비밀번호 변경 테스트
        userService.changePassword(createdUserId, "5678");
        System.out.println("비밀번호 변경 완료");

        // 삭제
        System.out.println(">> 유저 삭제 시도:");
        userService.deleteUser(createdUserId);

        // 삭제 확인
        System.out.println(">> 삭제 확인:");
        try {
            userService.findUserById(createdUserId);
        } catch (IllegalArgumentException e) {
            System.out.println("에러 발생: " + e.getMessage());
        }

        System.out.println("\n------------------------------------------\n");

        // Channel 테스트
        System.out.println("채널 기능 테스트");

        // 등록
        Channel newChannel = new Channel("본방");
        channelService.createChannel(newChannel);
        UUID createdChannelId = newChannel.getId();

        // 조회
        Channel foundChannel = channelService.findChannelById(createdChannelId);
        System.out.println("생성된 채널명: " + foundChannel.getChannelName());

        // 수정
        Channel updateChannelData = new Channel("공지사항");
        channelService.updateChannel(createdChannelId, updateChannelData);

        // 수정 확인
        Channel updatedChannel = channelService.findChannelById(createdChannelId);
        System.out.println("변경된 채널명: " + updatedChannel.getChannelName());

        // 삭제
        channelService.deleteChannel(createdChannelId);

        // 삭제 확인
        try {
            channelService.findChannelById(createdChannelId);
        } catch (IllegalArgumentException e) {
            System.out.println("에러 발생: " + e.getMessage());
        }

        System.out.println("\n------------------------------------------\n");

        // 메시지 기능 테스트
        System.out.println("\n[3] 메시지 기능 테스트");
        MessageService messageService = new JCFMessageService();

        // 생성된 유저가 채널에 글 작성
        Message msg1 = new Message("안녕하세요", createdChannelId, createdUserId);
        messageService.createMessage(msg1);

        Message msg2 = new Message("반갑습니다", createdChannelId, createdUserId);
        messageService.createMessage(msg2);

        // 특정 채널의 메시지 목록 조회
        System.out.println(">> [" + foundChannel.getChannelName() + "] 채널의 메시지 목록:");
        List<Message> channelMessages = messageService.findMessagesByChannelId(createdChannelId);

        for (Message msg : channelMessages) {
            System.out.println("- 내용: " + msg.getContent());
        }

        // 메시지 수정
        messageService.updateMessage(msg1.getId(), "안녕하세요! (수정됨)");
        System.out.println("수정 후 내용: " + messageService.findMessageById(msg1.getId()).getContent());

        // 메시지 삭제
        messageService.deleteMessage(msg2.getId());

        System.out.println("\n========== 테스트 종료 ==========");
    }
}
