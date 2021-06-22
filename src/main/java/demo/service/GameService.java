package demo.service;

import demo.domain.Game;
import demo.domain.UserLikesGame;
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
        Integer commentCount = game.getCommentCount();
        Integer avgScore = game.getAvgScore();
        avgScore = (avgScore * commentCount + addedScore) / (commentCount + 1);
        Integer computeRes = gameMapper.computeAvgScore(gameId, avgScore);

        Map<String, Integer> result = new HashMap<>();
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

//    TODO: 获取游戏图标

//    TODO: 获取展示图

}
