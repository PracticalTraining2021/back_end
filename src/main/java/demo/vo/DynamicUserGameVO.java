package demo.vo;

import demo.domain.Game;
import demo.domain.User;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DynamicUserGameVO implements Serializable {
    private String dynamicId;

    private String gameId;

    private Game game;

    private User user;

    private String userId;

    private String content;

    private String title;

    private String imgUrls;

    private int likesCount;

    private Date publishAt;

    private int isLike;

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getGameId() {
        return gameId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDynamicId() {
        return dynamicId;
    }

    public void setDynamicId(String dynamicId) {
        this.dynamicId = dynamicId;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public Date getPublishAt() {
        return publishAt;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public void setPublishAt(Date publishAt) {
        this.publishAt = publishAt;
    }

    public String getContent() {
        return content;
    }

    public String getImgUrls() {
        return imgUrls;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setImgUrls(String imgUrls) {
        this.imgUrls = imgUrls;
    }

    public Game getGame() {
        return game;
    }

    public User getUser() {
        return user;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getIsLike() {
        return isLike;
    }

    public void setIsLike(int isLike) {
        this.isLike = isLike;
    }
}
