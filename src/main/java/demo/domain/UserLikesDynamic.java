package demo.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@TableName("user_likes_dynamic")
@Builder
public class UserLikesDynamic implements Serializable {

    private String userId;

    private String dynamicId;

    public void setDynamicId(String dynamicId) {
        this.dynamicId = dynamicId;
    }

    public String getDynamicId() {
        return dynamicId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
