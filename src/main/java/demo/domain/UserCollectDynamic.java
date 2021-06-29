package demo.domain;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
@TableName("user_collect_dynamic")
public class UserCollectDynamic implements Serializable {

    private String userId;

    private String dynamicId;

    public void setDynamicId(String dynamicId) {
        this.dynamicId = dynamicId;
    }

    public String getDynamicId() {
        return dynamicId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }
}
