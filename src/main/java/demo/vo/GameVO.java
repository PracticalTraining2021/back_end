package demo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameVO {
    private String gameId;
    private String name;
    private int size;
    private String issuer;
    private int downloads;
    private double avgScore;
    private int commentCount;
    private int interestCount;
    private boolean isCurrentUserLikes;
}
