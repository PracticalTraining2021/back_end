package demo.vo;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DynamicVO implements Serializable {

    private String gameId;

    private String content;

    private String imgUrls;

    public void setImgUrls(String imgUrls) {
        this.imgUrls = imgUrls;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImgUrls() {
        return imgUrls;
    }

    public String getGameId() {
        return gameId;
    }

    public String getContent() {
        return content;
    }
}
