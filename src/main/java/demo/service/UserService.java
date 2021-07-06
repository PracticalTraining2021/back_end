package demo.service;

import com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import demo.domain.Game;
import demo.domain.User;
import demo.domain.UserLikesGame;
import demo.exception.BusinessException;
import demo.exception.ErrorCode;
import demo.mapper.GameMapper;
import demo.mapper.UserLikesGameMapper;
import demo.mapper.UserMapper;
import demo.vo.Result;
import demo.vo.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class UserService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private UserLikesGameMapper userLikesGameMapper;
    @Resource
    private GameMapper gameMapper;

    @Resource
    private ImageService imageService;

    @Value("${prefix}")
    private String prefix;

    public User getUserByUsername(String username) {
        return userMapper.getUserByUsername(username);
    }

    //    验证用户名和密码
    public Result validateUser(String username, String password) {
        boolean ok = true;
        User user = userMapper.getUserByUsername(username);
        if (user == null)
            throw new BusinessException(ErrorCode.BAD_REQUEST_COMMON, "不存在该用户");


        String encodedPassword = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!encodedPassword.equals(user.getPassword()))
            ok = false;

        if (ok) {
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(user, userVO);

            return Result.OK().data(userVO).build();
        }

        return Result.BAD().data("用户名或密码不正确").build();
    }

    public Result userRegister(String username, String password, String nickname) {
        User user = userMapper.getUserByUsername(username);
        if (user != null)
            return Result.BAD().data("用户名已存在").build();

        user = new User();
        user.setUsername(username);
        user.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
        user.setNickname(nickname);
        Integer insertRes = userMapper.insert(user);

        if (insertRes == 0) return Result.BAD().data("注册失败").build();

        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return Result.OK().data(userVO).build();
    }

    public Result getUserMessage(String userId) {
        User user = userMapper.selectById(userId);
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return Result.OK().data(userVO).build();
    }

    public void userFollowGame(String userId, String gameId) throws Exception {
        UserLikesGame userLikesGame = new UserLikesGame();
        userLikesGame.setGameId(gameId);
        userLikesGame.setUserId(userId);
        int insertLine = userLikesGameMapper.insert(userLikesGame);
        if (insertLine == 0) throw new Exception("你已关注");
    }

    public List<Game> getUserLikeGame(String userId) {
        List<String> gameIdList = userLikesGameMapper.selectGameId(userId);
        if (gameIdList == null || gameIdList.size() == 0) return new ArrayList<>();
        return gameMapper.selectBatchIds(gameIdList);
    }

    public List<String> getUserLikeGameIdList(String userId) {
        List<String> gameIdList = userLikesGameMapper.selectGameId(userId);
        if (gameIdList == null || gameIdList.size() == 0) return new ArrayList<>();
        return gameIdList;
    }

    public Integer changeAvatar(MultipartFile file, String userId) {
        String imgPrefix = "http://119.91.130.198/images/";
        IdentifierGenerator idGen = new DefaultIdentifierGenerator();
        String filePath = "avatar/" + idGen.nextUUID(userId) + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        imageService.uploadImg(file, filePath);

        return userMapper.changeAvatar(userId, imgPrefix + filePath);
    }

    public Integer changeNickname(String userId, String nickname) {
        User user = userMapper.selectById(userId);
        if (user == null)
            throw new BusinessException(ErrorCode.BAD_REQUEST_COMMON, "该用户不存在");

        Integer isExist = userMapper.getCountByNickname(nickname);
        if (isExist > 0)
            throw new BusinessException(ErrorCode.BAD_REQUEST_COMMON, "该昵称已存在");

        return userMapper.changeNickname(userId, nickname);
    }

    public Integer changePwd(String oldPwd, String newPwd, String userId) {
        User user = userMapper.selectById(userId);
        if (user == null)
            throw new BusinessException(ErrorCode.BAD_REQUEST_COMMON, "不存在该用户");

        String encodedOldPwd = DigestUtils.md5DigestAsHex(oldPwd.getBytes());
        if (!Objects.equals(encodedOldPwd, user.getPassword()))
            throw new BusinessException(ErrorCode.BAD_REQUEST_COMMON, "原密码不正确");

        return userMapper.updatePwd(DigestUtils.md5DigestAsHex(newPwd.getBytes()), userId);
    }

    public Integer bindUserToGame(String userId, String gameId) {
        User user = userMapper.selectById(userId);
        if (user == null)
            throw new BusinessException(ErrorCode.BAD_REQUEST_COMMON, "不存在该用户");

//        如果gameId为空，则更新为空
        if (StringUtils.isEmpty(gameId))
            return userMapper.bindUserToGame(userId, gameId);

        Game game = gameMapper.selectById(gameId);
        if (game == null)
            throw new BusinessException(ErrorCode.BAD_REQUEST_COMMON, "不存在该游戏");

        return userMapper.bindUserToGame(userId, gameId);
    }
}
