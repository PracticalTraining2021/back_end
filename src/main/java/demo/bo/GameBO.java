package demo.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("前端添加游戏传递的实体内容")
public class GameBO implements Serializable {
    @ApiModelProperty("游戏名称")
    private String name;
    @ApiModelProperty(value = "游戏大小", example = "99")
    private Integer size;
    @ApiModelProperty("游戏发行商")
    private String issuer;
    @ApiModelProperty("游戏简介")
    private String briefIntro;
    private MultipartFile icon;
    private MultipartFile displayDrawings;
}
