package demo.service;

import demo.domain.Game;
import demo.domain.UserLikesGame;
import demo.exception.BusinessException;
import demo.exception.ErrorCode;
import demo.mapper.GameMapper;
import demo.vo.Result;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GameService {

    @Resource
    private GameMapper gameMapper;

    public Game getById(String gameId) {
        return gameMapper.selectById(gameId);
    }

    public List<Game> getAllGames() {
        return gameMapper.getAll();
    }

    //    递增下载量
    public Integer increDownloads(String gameId) {
        return gameMapper.increDownloads(gameId);
    }

    //    计算平均评分
    public Map computeAvgScore(String gameId, Integer addedScore) {
        Game game = gameMapper.selectById(gameId);
        if (game == null) {
            throw new BusinessException(ErrorCode.BAD_REQUEST_COMMON, "找不到指定的游戏(gameId不存在)");
        }
        Integer commentCount = game.getCommentCount();
        double avgScore = game.getAvgScore();
        avgScore = (avgScore * commentCount + addedScore) / (commentCount + 1);
        Integer computeRes = gameMapper.computeAvgScore(gameId, avgScore);

        Map<String, Number> result = new HashMap<>();
        result.put("avg_score", avgScore);
        result.put("comment_count", commentCount + 1);
        return result;
    }

    //    递增关注量
    public Integer increInterestCount(String gameId) {
        return gameMapper.increInterestCount(gameId);
    }

    //    用户 关注或取消关注 游戏
    public Result handleUserLikesGame(UserLikesGame ulg) {
        boolean isUserLikesTheGame = gameMapper.getCountByUlg(ulg) == 1;
        if (isUserLikesTheGame) {
            gameMapper.deleteByUlg(ulg);
            gameMapper.decreInterestCount(ulg.getGameId());
            Map<String, Object> map = new HashMap<>();
            map.put("likes", false);
            map.put("details", "用户成功取消关注该游戏");
            return Result.OK().data(map).build();
        } else {
            gameMapper.insertByUlg(ulg);
            gameMapper.increInterestCount(ulg.getGameId());
            Map<String, Object> map = new HashMap<>();
            map.put("likes", true);
            map.put("details", "用户成功关注该游戏");
            return Result.OK().data(map).build();
        }
    }

    public Integer insertGame(Game game) {
        return gameMapper.insert(game);
    }

    public boolean isUserLikeGame(String gameId, String userId) {
        UserLikesGame ulg = new UserLikesGame(userId, gameId);
        return gameMapper.getCountByUlg(ulg) > 0;
    }
}
