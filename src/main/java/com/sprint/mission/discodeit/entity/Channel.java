package com.sprint.mission.discodeit.entity;

import java.util.*;

public class Channel extends BaseEntity {
    private String name;
    private String intro;
    private List <User> userList = new ArrayList<>();
    private List <UUID> messageList = new ArrayList<>();



    public Channel(String name, String intro) {
        super();
        this.name = name;
        this.intro = intro;
    }

    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
        setUpdatedAt(System.currentTimeMillis());
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro){
        this.intro = intro;
        setUpdatedAt(System.currentTimeMillis());
    }

    public List<User> getUserList() {
        return userList;
    }
    public List<UUID> getMessageList() { return messageList; }



}
