package demo.controller;

import demo.domain.Game;
import demo.domain.UserLikesGame;
import demo.service.GameService;
import demo.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@Api(tags = {"游戏相关接口"})
@RequestMapping("/game")
public class GameController {

    @Resource
    private GameService gameService;

    @ApiOperation("获取所有游戏实体的信息")
    @GetMapping("/all")
    public Result getAllGame() {
        List<Game> allGames = gameService.getAllGames();
        return Result.OK().data(allGames).build();
    }

    @ApiOperation("获取指定游戏实体的信息")
    @GetMapping("/one")
    public Result getOneGame(@RequestParam("gameId") String gameId) {
        Game one = gameService.getById(gameId);
        return Result.OK().data(one).build();
    }

    @ApiOperation("下载指定游戏")
    @GetMapping("/download")
    public Result download(@RequestParam("gameId") String gameId) {
        Integer res = gameService.increDownloads(gameId);
        if (res == 1)
            return Result.OK().data("下载成功").build();
        return Result.OK().data("下载过程中出现错误").build();
    }

    @ApiOperation("用户关注或取消关注游戏")
    @PostMapping("/userLikesGame")
    public Result handleUserFollows(@RequestParam(value = "gameId", required = true) String gameId, HttpSession session) {
        String userId = (String) session.getAttribute("user");
        if (StringUtils.isEmpty(userId))
            return Result.BAD().data("用户未登录").build();

        UserLikesGame ulg = new UserLikesGame(gameId, userId);
        Result result = gameService.handleUserLikesGame(ulg);
        return result;
    }

    @GetMapping("/test")
    public Result test() {
        return Result.OK().data("/game/test").build();
    }
}
