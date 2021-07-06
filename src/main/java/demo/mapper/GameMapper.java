package demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import demo.domain.Game;
import demo.domain.UserLikesGame;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface GameMapper extends BaseMapper<Game> {

    @Select("select * from game")
    List<Game> getAll();

    @Select("select * from game  order by  RAND() LIMIT #{count}")
    List<Game> getRandomGames(@Param("count") Integer count);

    @Update("update game set downloads = downloads +1 where game_id = #{gameId}")
    Integer increDownloads(@Param("gameId") String gameId);

    @Update("update game " +
            "set avg_score = #{avgScore}, comment_count = comment_count + 1 " +
            "where game_id = #{gameId}")
    Integer computeAvgScore(@Param("gameId") String gameId, @Param("avgScore") double avgScore);

    @Update("update game set interest_count = interest_count + 1 where game_id = #{gameId}")
    Integer increInterestCount(@Param("gameId") String gameId);

    @Update("update game set interest_count = interest_count - 1 where game_id = #{gameId}")
    Integer decreInterestCount(@Param("gameId") String gameId);


    //    =====================================================================================
    //    以下接口操作的是 user_likes_game 表
    @Select("select count(*) from user_likes_game " +
            "where user_id = #{ulg.userId} and game_id = #{ulg.gameId}")
    Integer getCountByUlg(@Param("ulg") UserLikesGame ulg);

    @Insert("insert into user_likes_game (game_id, user_id) " +
            "values(#{ulg.gameId},#{ulg.userId})")
    Integer insertByUlg(@Param("ulg") UserLikesGame ulg);

    @Delete("delete from user_likes_game " +
            "where game_id = #{ulg.gameId} and user_id = #{ulg.userId}")
    Integer deleteByUlg(@Param("ulg") UserLikesGame ulg);

    @Select("select * from game order by interest_count desc")
    List<Game> getAllGamesByInterestCountDesc();

    @Select("select * from game order by avg_score desc")
    List<Game> getAllGamesByAvgScoreDesc();

    @Select("select * from game where category like CONCAT('%',#{category},'%')")
    List<Game> getGamesByCategory(@Param("category") String category);

    @Select("select game_id from game")
    List<String> getAllGameId();

    @Update("update game set heat = #{heat} where game_id = #{gameId}")
    Integer updateHeatByGameId(@Param("gameId") String gameId, @Param("heat") Double heat);

    @Select("select * from game order by heat desc")
    List<Game> getAllGamesByHeat();
}
