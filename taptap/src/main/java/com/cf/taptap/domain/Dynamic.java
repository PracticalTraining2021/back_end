package com.cf.taptap.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@TableName("dynamic")
public class Dynamic implements Serializable {

    @TableId(type = IdType.ASSIGN_ID)
    private String dynamicId;

    private String gameId;

    private  String userId;

    private  String content;

    private  String imgUrls;

    private int likesCount;

    private Date publishAt;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDynamicId() {
        return dynamicId;
    }

    public String getContent() {
        return content;
    }

    public String getGameId() {
        return gameId;
    }

    public String getImgUrls() {
        return imgUrls;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDynamicId(String dynamicId) {
        this.dynamicId = dynamicId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public void setImgUrls(String imgUrls) {
        this.imgUrls = imgUrls;
    }

    public void setPublishAt(Date publishAt) {
        this.publishAt = publishAt;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public Date getPublishAt() {
        return publishAt;
    }

    public int getLikesCount() {
        return likesCount;
    }
}
