package demo.controller;

import demo.bo.GameBO;
import demo.domain.Game;
import demo.domain.UserLikesGame;
import demo.exception.BusinessException;
import demo.exception.ErrorCode;
import demo.service.GameService;
import demo.vo.GameVO;
import demo.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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

    @ApiOperation("随机获取指定数量的游戏")
    @GetMapping("/random")
    public Result getRandomGame(@RequestParam("count") Integer count) {
        List<Game> games = gameService.getRandomGames(count);
        return Result.OK().data(games).build();
    }

    @ApiOperation("获取指定游戏实体的信息")
    @GetMapping("/one")
    public Result getOneGame(@RequestParam("gameId") String gameId, HttpServletRequest request) {
        Game one = gameService.getById(gameId);
        GameVO gameVO = new GameVO();
        BeanUtils.copyProperties(one, gameVO);
        String userId = (String) request.getSession().getAttribute("user");
        if (StringUtils.isEmpty(userId))
            return Result.OK().data(gameVO).build();

        boolean isCurUserLikeTheGame = gameService.isUserLikeGame(gameId, userId);
        gameVO.setCurrentUserLikes(isCurUserLikeTheGame);
        return Result.OK().data(gameVO).build();
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
    public Result handleUserFollows(@RequestParam(value = "gameId", required = true) String gameId, HttpServletRequest request) {
        String userId = (String) request.getSession().getAttribute("user");
        if (StringUtils.isEmpty(userId))
            throw new BusinessException(ErrorCode.BAD_REQUEST_COMMON, "用户未登录");

        UserLikesGame ulg = new UserLikesGame(userId, gameId);
        Result result = gameService.handleUserLikesGame(ulg);
        return result;
    }

    @ApiOperation("添加游戏（版本1）")
    @PostMapping("/addGame")
    public Result insertGame(@RequestBody Game game) {
        game.setGameId(null);
        gameService.insertGame(game);
        return Result.OK().data("添加游戏成功").build();
    }

    @ApiOperation("添加游戏（版本2）")
    @PostMapping("/addGame2")
    public Result insertGame2(GameBO gameBO) {
        Integer result = gameService.insertGame2(gameBO);
        if (result > 0) return Result.OK().data("添加游戏成功").build();
        return Result.BAD().data("添加游戏过程中出现未知错误===>insertGame2").build();
    }

    @ApiOperation("删除游戏")
    @DeleteMapping("/")
    public Result deleteGame(String gameId) {
        Integer res = gameService.deleteGame(gameId);
        if (res > 0) return Result.OK().data("删除游戏成功").build();
        return Result.BAD().data("游戏不存在或删除游戏过程中出现未知错误===>deleteGame").build();
    }

}
