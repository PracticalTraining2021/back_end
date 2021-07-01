package demo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@TableName("dynamic")
@Data
public class Dynamic implements Serializable {

    @TableId(type = IdType.ASSIGN_ID)
    private String dynamicId;

    private String gameId;

    private String userId;

    private String content;

    private String title;

    private String imgUrls;

    private int likesCount;

    private long publishAt;
}
