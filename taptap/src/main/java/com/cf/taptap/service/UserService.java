package com.cf.taptap.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cf.taptap.domain.Game;
import com.cf.taptap.domain.User;
import com.cf.taptap.domain.UserLikesGame;
import com.cf.taptap.mapper.GameMapper;
import com.cf.taptap.mapper.UserLikesGameMapper;
import com.cf.taptap.mapper.UserMapper;
import com.cf.taptap.vo.Result;
import com.cf.taptap.vo.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private UserLikesGameMapper userLikesGameMapper;
    @Resource
    private GameMapper gameMapper;

    public User getUserByUsername(String username) {
        return userMapper.getUserByUsername(username);
    }

    //    验证用户名和密码
    public Result validateUser(String username, String password) {
        boolean ok = true;
        User user = userMapper.getUserByUsername(username);
        if (user == null)
            ok = false;

        assert user != null;
        if (!password.equals(user.getPassword()))
            ok = false;

        if (ok) {
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(user, userVO);

            return Result.OK().data(userVO).build();
        }

        return Result.BAD().data("用户名或密码不正确").build();
    }

    public Result userRegister(String username, String password) {
        User user = userMapper.getUserByUsername(username);
        if (user != null)
            return Result.BAD().data("用户名已存在").build();

        user = new User();
        user.setUsername(username);
        user.setPassword(password);
        Integer insertRes = userMapper.insert(user);

        if (insertRes == 0) return Result.BAD().data("注册失败").build();

        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return Result.OK().data(userVO).build();
    }

    public Result getUserMessage(String userId)
    {
        User user=userMapper.selectById(userId);
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return Result.OK().data(userVO).build();
    }

    public void userFollowGame(String userId,String gameId) throws Exception {
        UserLikesGame userLikesGame=new UserLikesGame();
        userLikesGame.setGame_id(gameId);
        userLikesGame.setUserId(userId);
        int insertLine=userLikesGameMapper.insert(userLikesGame);
        if(insertLine==0) throw  new Exception("你已关注");
    }

    public List<Game> getUserLikeGame(String userId)
    {
        List<String> gameIdList=userLikesGameMapper.selectGameId(userId);
         return gameMapper.selectBatchIds(gameIdList);
    }
}
