package demo.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentBO {
    @ApiModelProperty("游戏ID")
    private String gameId;
    @ApiModelProperty("评价内容")
    private String content;
    @ApiModelProperty("评分")
    private Integer score;
}
