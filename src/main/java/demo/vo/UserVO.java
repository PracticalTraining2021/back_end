package demo.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserVO {
    private String userId;
    private String nickname;
    private String username;
    private String avatar;
    private Integer gender;
}
