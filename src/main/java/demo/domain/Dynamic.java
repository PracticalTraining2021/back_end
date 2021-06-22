package demo.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel("动态实体")
public class Dynamic {
    @ApiModelProperty("动态ID")
    private String dynamicId;
    @ApiModelProperty("游戏ID")
    private String gameId;
    @ApiModelProperty("用户ID")
    private String userId;
    @ApiModelProperty("动态内容")
    private String content;
    @ApiModelProperty("动态图片路径")
    private String imgUrls;
    @ApiModelProperty("点赞数")
    private Integer likesCount;
    @ApiModelProperty("发布时间")
    private Date publishAt;
}
