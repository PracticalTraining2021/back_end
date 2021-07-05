package demo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("评价实体")
public class Comment {
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty("评价ID")
    private String commentId;
    @ApiModelProperty("游戏ID")
    private String gameId;
    @ApiModelProperty("用户ID")
    private String userId;
    @ApiModelProperty("评价内容")
    private String content;
    @ApiModelProperty("评分")
    private Integer score;
    @ApiModelProperty("点赞数")
    private Integer likesCount;
    @ApiModelProperty("评价时间")
    private Long commentAt;
}
