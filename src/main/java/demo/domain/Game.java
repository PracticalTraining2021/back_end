package demo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("游戏实体")
public class Game {
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty("游戏ID")
    private String gameId;
    @ApiModelProperty("名称")
    private String name;
    @ApiModelProperty("下载包大小")
    private String size;
    @ApiModelProperty("发行商")
    private String issuer;
    @ApiModelProperty("下载量")
    private Integer downloads;
    @ApiModelProperty("平均评分")
    private Integer avgScore;
    @ApiModelProperty("评价人数")
    private Integer commentCount;
    @ApiModelProperty("关注量")
    private Integer interestCount;
    @ApiModelProperty("图标路径")
    private String icon;
    @ApiModelProperty("展示图文件路径")
    private String displayDrawings;
    @ApiModelProperty("简介")
    private String briefIntro;
}
