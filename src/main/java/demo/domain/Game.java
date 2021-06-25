package demo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@TableName("game")
@ToString
@Builder
public class Game implements Serializable {
    @TableId(type = IdType.ASSIGN_ID)
    private String gameId;
    private String name;
    private int size;
    private String issuer;
    private int downloads;
    private double avgScore;
    private int commentCount;
    private int interestCount;

    private String icon;
    private String displayDrawings;
    private String briefIntro;

    public String getGameId() {
        return gameId;
    }

    public String getName() {
        return name;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAvgScore() {
        return avgScore;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public int getDownloads() {
        return downloads;
    }

    public int getInterestCount() {
        return interestCount;
    }


    public int getSize() {
        return size;
    }

    public String getBriefIntro() {
        return briefIntro;
    }

    public String getDisplayDrawings() {
        return displayDrawings;
    }

    public String getIcon() {
        return icon;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setAvgScore(double avgScore) {
        this.avgScore = avgScore;
    }

    public void setBriefIntro(String briefIntro) {
        this.briefIntro = briefIntro;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public void setDisplayDrawings(String displayDrawings) {
        this.displayDrawings = displayDrawings;
    }

    public void setDownloads(int downloads) {
        this.downloads = downloads;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setInterestCount(int interestCount) {
        this.interestCount = interestCount;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }


    public void setSize(int size) {
        this.size = size;
    }
}
