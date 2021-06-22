package demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import demo.domain.Comment;
import demo.domain.UserLikesComment;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CommentMapper extends BaseMapper<Comment> {

    @Select("select * from comment where game_id = #{gameId}")
    List<Comment> getAllCommentsByGameId(String gameId);

    @Select("select count(*) from comment where user_id = #{userId} and game_id = #{gameId}")
    Integer getCountByUserIdAndGameId(@Param("userId") String userId, @Param("gameId") String gameId);

    @Update("update comment set likes_count = likes_count+1 " +
            "where comment_id = #{commentId}")
    Integer increLikesCountByCommentId(@Param("commentId") String commentId);

    @Update("update comment set likes_count = likes_count-1 " +
            "where comment_id = #{commentId}")
    Integer decreLikesCountByCommentId(@Param("commentId") String commentId);

//  ================================================================================
//    以下接口操作的是 user_likes_comment 表


    @Select("select count(*) from user_likes_comment " +
            "where user_id = #{ulc.userId} and comment_id = #{ulc.commentId}")
    Integer getCountByUlc(@Param("ulc") UserLikesComment ulc);

    @Insert("insert into user_likes_comment (user_id, comment_id) " +
            "values(#{ulc.userId}, #{ulc.commentId})")
    Integer insertUlc(@Param("ulc") UserLikesComment ulc);

    @Delete("delete from user_likes_comment " +
            "where user_id = #{ulc.userId} and comment_id = #{ulc.commentId}")
    Integer deleteByUlc(@Param("ulc") UserLikesComment ulc);
}
