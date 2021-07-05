package demo.controller;

import demo.bo.CommentBO;
import demo.domain.Comment;
import demo.domain.UserLikesComment;
import demo.exception.BusinessException;
import demo.exception.ErrorCode;
import demo.service.CommentService;
import demo.vo.CommentVO;
import demo.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Api(tags = {"评论相关接口"})
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Resource
    private CommentService commentService;

    @ApiOperation("获取指定游戏的所有评论")
    @GetMapping("/all")
    public Result allComments(@RequestParam(value = "gameId", required = true) String gameId,
                              HttpServletRequest request) {
        String userId = (String) request.getSession().getAttribute("user");
        List<CommentVO> comments = commentService.getAllCommentsByGameId(gameId, userId);
        return Result.OK().data(comments).build();
    }

    @ApiOperation("为指定游戏添加评论")
    @PostMapping("/addComment")
    public Result insertComment(@RequestBody CommentBO commentBO, HttpServletRequest request) {
        String userId = (String) request.getSession().getAttribute("user");
        if (StringUtils.isEmpty(userId))
            throw new BusinessException(ErrorCode.BAD_REQUEST_COMMON, "用户未登录");
        if (StringUtils.isEmpty(commentBO.getGameId()))
            throw new BusinessException(ErrorCode.BAD_REQUEST_COMMON, "请求体中缺少参数：gameId");

        Comment comment = new Comment();
        BeanUtils.copyProperties(commentBO, comment);
        comment.setUserId(userId);
        comment.setCommentAt((new Date()).getTime());
        Result result = commentService.addComment(comment);
        
        return result;
    }

    @ApiOperation("更改评价内容")
    @PostMapping("/content")
    public Result changeContent(@RequestParam(value = "commentId", required = true) String commentId,
                                @RequestParam(value = "content", required = true) String content,
                                @RequestParam(value = "score", required = false) Integer score,
                                HttpServletRequest request) {
        String userId = (String) request.getSession().getAttribute("user");
        if (StringUtils.isEmpty(userId))
            throw new BusinessException(ErrorCode.BAD_REQUEST_COMMON, "用户未登录");

        Comment comment = commentService.getCommentByCommentId(commentId);
        if (Objects.isNull(comment))
            throw new BusinessException(ErrorCode.BAD_REQUEST_COMMON, "该评论不存在");
        if (!Objects.equals(comment.getUserId(), userId))
            throw new BusinessException(ErrorCode.BAD_REQUEST_COMMON, "该用户没有权限修改其他用户的评论");

        Integer updateRes = commentService.updateComment(commentId, content, score);
        if (updateRes == 0) throw new BusinessException(ErrorCode.BAD_REQUEST_COMMON, "修改评价失败");
//        return Result.BAD().data("修改评价失败").build();
        return Result.OK().data("成功修改评价").build();
    }

    @ApiOperation("用户点赞或取消点赞指定评论")
    @PostMapping("/userLikesComment")
    public Result handleUlc(@RequestParam("commentId") String commentId, HttpServletRequest request) {
        String userId = (String) request.getSession().getAttribute("user");
        if (StringUtils.isEmpty(userId))
            throw new BusinessException(ErrorCode.BAD_REQUEST_COMMON, "用户未登录");

        UserLikesComment ulc = new UserLikesComment(userId, commentId);
        Result result = commentService.handleUserLikesComment(ulc);
        return result;
    }
}
