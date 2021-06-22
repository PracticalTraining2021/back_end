package demo.service;

import demo.domain.Comment;
import demo.domain.UserLikesComment;
import demo.mapper.CommentMapper;
import demo.vo.Result;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommentService {

    @Resource
    private CommentMapper commentMapper;

    @Resource
    private GameService gameService;

    public List<Comment> getAllCommentsByGameId(String gameId) {
        return commentMapper.getAllCommentsByGameId(gameId);
    }

    //    增加评价
    public Result addComment(Comment comment) {
        boolean isExist = commentMapper.getCountByUserIdAndGameId(comment.getUserId(), comment.getGameId()) == 1;
        if (isExist)
            return Result.BAD().data("该用户已评价该游戏").build();


        comment.setCommentId(null);
        comment.setLikesCount(0);
        if (comment.getScore() == 0)
            comment.setScore(10);
        if (StringUtils.isEmpty(comment.getContent()))
            comment.setContent("体验很好");
        if (comment.getCommentAt() == null)
            comment.setCommentAt(new Date());

        commentMapper.insert(comment);

        gameService.computeAvgScore(comment.getGameId(), comment.getScore());

        return Result.OK().data(comment).build();
    }

    //    用户 点赞或取消点赞 评价
    public Result handleUserLikesComment(UserLikesComment ulc) {
        boolean isExist = commentMapper.getCountByUlc(ulc) == 1;
        if (isExist) {
            commentMapper.deleteByUlc(ulc);
            commentMapper.decreLikesCountByCommentId(ulc.getCommentId());
            Map<String, Object> map = new HashMap<>();
            map.put("likes", false);
            map.put("details", "用户成功取消点赞该评价");
            return Result.OK().data(map).build();
        } else {
            commentMapper.insertUlc(ulc);
            commentMapper.increLikesCountByCommentId(ulc.getCommentId());
            Map<String, Object> map = new HashMap<>();
            map.put("likes", true);
            map.put("details", "用户成功点赞该评价");
            return Result.OK().data(map).build();
        }

    }

}
