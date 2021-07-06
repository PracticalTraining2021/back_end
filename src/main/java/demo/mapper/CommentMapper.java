package demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import demo.domain.Comment;
import demo.domain.UserLikesComment;
import demo.vo.CommentVO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CommentMapper extends BaseMapper<Comment> {

    @Select("select c.comment_id,c.game_id,c.user_id,c.content,c.score,c.likes_count,c.comment_at,u.avatar,u.nickname from comment c,user u where c.game_id = #{gameId} and c.user_id = u.user_id")
    List<CommentVO> getAllCommentsByGameId(String gameId);

    @Select("select count(*) from comment where user_id = #{userId} and game_id = #{gameId}")
    Integer getCountByUserIdAndGameId(@Param("userId") String userId, @Param("gameId") String gameId);

    @Update("update comment set likes_count = likes_count+1 " +
            "where comment_id = #{commentId}")
    Integer increLikesCountByCommentId(@Param("commentId") String commentId);

    @Update("update comment set likes_count = likes_count-1 " +
            "where comment_id = #{commentId}")
    Integer decreLikesCountByCommentId(@Param("commentId") String commentId);

    @Update("update comment set content = #{content} where comment_id = #{commentId}")
    Integer updateComment1(@Param("commentId") String commentId, @Param("content") String content);

    @Update("update comment set content = #{content}, score = #{score} where comment_id = #{commentId}")
    Integer updateComment2(@Param("commentId") String commentId, @Param("content") String content, @Param("score") Integer score);
//  ================================================================================
//    以下接口操作的是 user_likes_comment 表


    @Select("select count(*) from user_likes_comment " +
            "where user_id = #{ulc.userId} and comment_id = #{ulc.commentId}")
    Integer getCountByUlc(@Param("ulc") UserLikesComment ulc);

    @Select("select count(*) from user_likes_comment where user_id=#{userId} and comment_id=#{commentId}  ")
    Integer getCountByUserIdAndCommentIdFromULC(@Param("userId") String userId, @Param("commentId") String commentId);

    @Insert("insert into user_likes_comment (user_id, comment_id) " +
            "values(#{ulc.userId}, #{ulc.commentId})")
    Integer insertUlc(@Param("ulc") UserLikesComment ulc);

    @Delete("delete from user_likes_comment " +
            "where user_id = #{ulc.userId} and comment_id = #{ulc.commentId}")
    Integer deleteByUlc(@Param("ulc") UserLikesComment ulc);

    @Select("select c.comment_id,c.game_id,c.user_id,c.content,c.score,c.likes_count,c.comment_at,u.avatar,u.nickname,(select count(*) from user_likes_comment s where s.user_id=#{userId} and s.comment_id=c.comment_id) as is_like from comment c,user u where c.game_id = #{gameId} and c.user_id = u.user_id")
    List<CommentVO> getAllCommentsByGameIdTest(@Param("gameId") String gameId, @Param("userId") String userId);


}
