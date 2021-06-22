package demo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserLikesGame {
    private String gameId;
    private String userId;
}
