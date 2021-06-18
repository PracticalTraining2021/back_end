package demo.service;

import demo.domain.User;
import demo.mapper.UserMapper;
import demo.vo.Result;
import demo.vo.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserService {
    @Resource
    private UserMapper userMapper;

    public User getUserByUsername(String username) {
        return userMapper.getUserByUsername(username);
    }

    //    验证用户名和密码
    public Result validateUser(String username, String password) {
        boolean ok = true;
        User user = userMapper.getUserByUsername(username);
        if (user == null)
            ok = false;

        if (!password.equals(userMapper.getPasswordByUsername(username)))
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
}
