package demo.vo;

import demo.domain.Game;
import demo.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

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

    private long publishAt;

    private int isLike;

    private int isCollect;


}
