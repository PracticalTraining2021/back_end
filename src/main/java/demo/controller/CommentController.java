package demo.controller;

import demo.domain.Comment;
import demo.domain.UserLikesComment;
import demo.exception.BusinessException;
import demo.exception.ErrorCode;
import demo.service.CommentService;
import demo.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@Api(tags = {"评论相关接口"})
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Resource
    private CommentService commentService;

    @ApiOperation("获取指定游戏的所有评论")
    @GetMapping("/all")
    public Result allComments(@RequestParam(value = "gameId", required = true) String gameId) {
        List<Comment> comments = commentService.getAllCommentsByGameId(gameId);
        return Result.OK().data(comments).build();
    }

    @ApiOperation("为指定游戏添加评论")
    @PostMapping("/addComment")
    public Result insertComment(@RequestBody Comment comment, HttpSession session) {
        String userId = (String) session.getAttribute("user");
        if (StringUtils.isEmpty(userId))
            throw new BusinessException(ErrorCode.BAD_REQUEST_COMMON, "用户未登录");
        if (StringUtils.isEmpty(comment.getGameId()))
            throw new BusinessException(ErrorCode.BAD_REQUEST_COMMON, "请求体中缺少参数：gameId");
//        return Result.BAD().data("缺少gameId").build();

        comment.setUserId(userId);
        Result result = commentService.addComment(comment);
        return result;
    }

    @ApiOperation("更改评价内容")
    @PostMapping("/content")
    public Result changeContent(@RequestParam(value = "commentId", required = true) String commentId,
                                @RequestParam(value = "content", required = true) String content,
                                @RequestParam(value = "score", required = false) Integer score,
                                HttpSession session) {
        String userId = (String) session.getAttribute("user");
        if (StringUtils.isEmpty(userId))
            throw new BusinessException(ErrorCode.BAD_REQUEST_COMMON, "用户未登录");

        Integer updateRes = commentService.updateComment(commentId, content, score);
        if (updateRes == 0) throw new BusinessException(ErrorCode.BAD_REQUEST_COMMON, "修改评价失败");
//        return Result.BAD().data("修改评价失败").build();
        return Result.OK().data("成功修改评价").build();
    }

    @ApiOperation("用户点赞或取消点赞指定评论")
    @PostMapping("/userLikesComment")
    public Result handleUlc(@RequestParam("commentId") String commentId, HttpSession session) {
        String userId = (String) session.getAttribute("user");
        if (StringUtils.isEmpty(userId))
            throw new BusinessException(ErrorCode.BAD_REQUEST_COMMON, "用户未登录");

        UserLikesComment ulc = new UserLikesComment(userId, commentId);
        Result result = commentService.handleUserLikesComment(ulc);
        return result;
    }

    @GetMapping("/test")
    public Result test() {
        return Result.OK().data("/comment/test").build();
    }
}
