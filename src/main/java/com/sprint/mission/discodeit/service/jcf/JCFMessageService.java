package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.service.MessageService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class JCFMessageService implements MessageService {
    private final List<Message> messageList;
    public JCFMessageService(){
        this.messageList = new ArrayList<>();
    }

    public void createMessage(Message message){
        messageList.add(message);
        System.out.println("메시지가 전송되었습니다. " + message.getContent());
    }

    public List<Message> findMessagesByChannelId(UUID channelId){
        return messageList.stream()
                .filter(message -> message.getChannelId().equals(channelId))
                .collect(Collectors.toList());
    }

    public List<Message> findAllMessages(){
        return messageList;
    }

    public Message findMessageById(UUID id){
        return messageList.stream()
                .filter(msg -> msg.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void updateMessage(UUID id, String newContent){
        Message targetMessage = findMessageById(id);
        if(targetMessage != null){
            targetMessage.updateContent(newContent);
            System.out.println("메시지가 수정되었습니다");
        }else{
            System.out.println("수정할 메시지가 없습니다");
        }
    }

    public void deleteMessage(UUID id){
        messageList.removeIf(msg -> msg.getId().equals(id));
        System.out.println("메시지가 삭제되었습니다");
    }

}
