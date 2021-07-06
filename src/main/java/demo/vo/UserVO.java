package demo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserVO {
    private String userId;
    private String nickname;
    private String username;
    private String avatar;
    private String gameId;
}
