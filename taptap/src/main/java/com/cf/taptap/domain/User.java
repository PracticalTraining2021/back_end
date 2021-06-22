package com.cf.taptap.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @TableId(type = IdType.ASSIGN_ID)
    private String userId;
    private String nickname;
    private String username;
    private String password;
    private String avatar;
    private Integer gender;
    private Date createAt;
    private Date lastLoginAt;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public Date getLastLoginAt() {
        return lastLoginAt;
    }

    public Integer getGender() {
        return gender;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getNickname() {
        return nickname;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public void setLastLoginAt(Date lastLoginAt) {
        this.lastLoginAt = lastLoginAt;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
