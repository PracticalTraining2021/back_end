package demo.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentVO {
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
    private Date commentAt;
    private String avatar;
    private String nickname;
}
