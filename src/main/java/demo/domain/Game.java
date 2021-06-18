package demo.domain;

import lombok.Data;

@Data
public class Game {
    private String gameId;
    private String name;
    private String size;
    private String issuer;
    private String downloads;
    private String avg_score;
    private String scored_count;
    private String comment_count;
    
}
