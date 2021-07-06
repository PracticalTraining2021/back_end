package demo.service;

import com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import demo.bo.GameBO;
import demo.domain.Game;
import demo.domain.UserLikesGame;
import demo.exception.BusinessException;
import demo.exception.ErrorCode;
import demo.mapper.GameMapper;
import demo.vo.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GameService {

    @Resource
    private GameMapper gameMapper;

    @Resource
    private ImageService imageService;

    @Value("${prefix}")
    private String prefix;

    public Game getById(String gameId) {
        return gameMapper.selectById(gameId);
    }

    public List<Game> getAllGames() {
        return gameMapper.getAll();
    }

    public List<Game> getRandomGames(Integer count) {
        return gameMapper.getRandomGames(count);
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

    public Integer insertGame2(GameBO gameBO) {
        String imgPrefix = "http://119.91.130.198/images/";
        IdentifierGenerator idGen = new DefaultIdentifierGenerator();

        Game game = new Game();
        BeanUtils.copyProperties(gameBO, game);
        String gameId = idGen.nextId(game).toString();
        game.setGameId(gameId);

//        上传图标并设置game的icon属性为图标可从服务器获取的url
//        如，http://119.91.130.198/images/games/123123/icon-aaa.png
        String iconMidPath = "games/" + gameId + "-icon-" + gameBO.getIcon().getOriginalFilename();
        imageService.uploadImg(gameBO.getIcon(), iconMidPath);
        game.setIcon(imgPrefix + iconMidPath);

//        上传展示图并设置game的displayDrawings为展示图可从服务器获取的url
//        多张展示图路径以 | 符号分隔
//        如，http://119.91.130.198/images/games/123123/dd-1-aaa.png|http://119.91.130.198/images/games/123123/dd-2-bbb.png
        Integer count = 1;
        StringBuilder ddPaths = new StringBuilder();
        String ddMidPath = "games/" + gameId + "-dd-";
        for (MultipartFile file : gameBO.getDisplayDrawings()) {
            String path = ddMidPath + (count++) + "-" + file.getOriginalFilename();
            imageService.uploadImg(file, path);
            ddPaths.append(imgPrefix).append(path).append("|");
        }
        if (ddPaths.length() > 0)
            ddPaths.deleteCharAt(ddPaths.length() - 1);
        game.setDisplayDrawings(ddPaths.toString());


        return gameMapper.insert(game);
    }

    public Integer deleteGame(String gameId) {
        return gameMapper.deleteById(gameId);
    }

    public List<Game> getAllGamesByAvgScoreDesc() {
        return gameMapper.getAllGamesByAvgScoreDesc();
    }

    public List<Game> getAllGamesByInterestCountDesc() {
        return gameMapper.getAllGamesByInterestCountDesc();
    }

    public List<Game> getGamesByCategory(String category) {
        return gameMapper.getGamesByCategory(category);
    }

    public Integer updateGame(Game game, GameBO gameBO) {
        String imgPrefix = "http://119.91.130.198/images/";

        if (!StringUtils.isEmpty(gameBO.getName())) game.setName(gameBO.getName());
        if (!StringUtils.isEmpty(gameBO.getIssuer())) game.setIssuer(gameBO.getIssuer());
        if (!StringUtils.isEmpty(gameBO.getBriefIntro())) game.setBriefIntro(gameBO.getBriefIntro());
        if (!StringUtils.isEmpty(gameBO.getCategory())) game.setCategory(gameBO.getCategory());
        if (gameBO.getSize() != null) game.setSize(gameBO.getSize());
        String gameId = game.getGameId();

//        上传图标并设置game的icon属性为图标可从服务器获取的url
//        如，http://119.91.130.198/images/games/123123/icon-aaa.png
        if (gameBO.getIcon() != null) {
            String iconMidPath = "games/" + gameId + "-icon-" + gameBO.getIcon().getOriginalFilename();
            imageService.uploadImg(gameBO.getIcon(), iconMidPath);
            game.setIcon(imgPrefix + iconMidPath);
        }

//        上传展示图并设置game的displayDrawings为展示图可从服务器获取的url
//        多张展示图路径以 | 符号分隔
//        如，http://119.91.130.198/images/games/123123/dd-1-aaa.png|http://119.91.130.198/images/games/123123/dd-2-bbb.png
        if (gameBO.getDisplayDrawings() != null) {
            Integer count = 1;
            StringBuilder ddPaths = new StringBuilder();
            String ddMidPath = "games/" + gameId + "-dd-";
            for (MultipartFile file : gameBO.getDisplayDrawings()) {
                String path = ddMidPath + (count++) + "-" + file.getOriginalFilename();
                imageService.uploadImg(file, path);
                ddPaths.append(imgPrefix).append(path).append("|");
            }
            if (ddPaths.length() > 0) {
                ddPaths.deleteCharAt(ddPaths.length() - 1);
            }
            game.setDisplayDrawings(ddPaths.toString());
        }

        gameMapper.deleteById(game.getGameId());
        return gameMapper.insert(game);
    }

    public List<String> getAllGameId() {
        return gameMapper.getAllGameId();
    }

    public Integer updateHeatByGameId(String gameId, Double heat) {
        return gameMapper.updateHeatByGameId(gameId, heat);
    }

    public List<Game> getAllGamesByHeat() {
        return gameMapper.getAllGamesByHeat();
    }
}
