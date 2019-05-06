package com.bdhanbang.weixin.entity;


import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.databind.JsonNode;


public class Friend implements Serializable {

    private static final long serialVersionUID = 1517581680;

    private UUID          id;
    private String        userId;
    private String        friendId;
    private String        friendName;
    private LocalDateTime buildTime;
    private String        avatar;
    private JsonNode      jsonb;

    public Friend() {}

    public Friend(Friend value) {
        this.id = value.id;
        this.userId = value.userId;
        this.friendId = value.friendId;
        this.friendName = value.friendName;
        this.buildTime = value.buildTime;
        this.avatar = value.avatar;
        this.jsonb = value.jsonb;
    }

    public Friend(
        UUID          id,
        String        userId,
        String        friendId,
        String        friendName,
        LocalDateTime buildTime,
        String        avatar,
        JsonNode      jsonb
    ) {
        this.id = id;
        this.userId = userId;
        this.friendId = friendId;
        this.friendName = friendName;
        this.buildTime = buildTime;
        this.avatar = avatar;
        this.jsonb = jsonb;
    }

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFriendId() {
        return this.friendId;
    }

    public void setFriendId(String friendId) {
        this.friendId = friendId;
    }

    public String getFriendName() {
        return this.friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    public LocalDateTime getBuildTime() {
        return this.buildTime;
    }

    public void setBuildTime(LocalDateTime buildTime) {
        this.buildTime = buildTime;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public JsonNode getJsonb() {
        return this.jsonb;
    }

    public void setJsonb(JsonNode jsonb) {
        this.jsonb = jsonb;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Friend (");

        sb.append(id);
        sb.append(", ").append(userId);
        sb.append(", ").append(friendId);
        sb.append(", ").append(friendName);
        sb.append(", ").append(buildTime);
        sb.append(", ").append(avatar);
        sb.append(", ").append(jsonb);

        sb.append(")");
        return sb.toString();
    }
}
