package demo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator;
import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@TableName("game")
@ToString
@Builder
@Data
public class Game implements Serializable {
    @TableId(type = IdType.ASSIGN_ID)
    private String gameId;
    private String name;
    private int size;
    private String issuer;
    private int downloads;
    private double avgScore;
    private int commentCount;
    private int interestCount;
    private String icon;
    private String displayDrawings;
    private String briefIntro;
    private String category;
    private Double heat;

    public static void main(String[] args) {
        System.out.println(new DefaultIdentifierGenerator().nextId(null));
    }
}
