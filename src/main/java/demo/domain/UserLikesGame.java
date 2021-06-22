package demo.domain;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@TableName("user_likes_game")
@ToString
@Builder
@Data
public class UserLikesGame implements Serializable {

    private String userId;

    private String gameId;
}
