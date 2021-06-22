package demo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserLikesComment {
    private String userId;
    private String commentId;
}
