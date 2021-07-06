package demo.controller;


import demo.bo.PasswordBO;
import demo.exception.BusinessException;
import demo.exception.ErrorCode;
import demo.service.UserService;
import demo.vo.Result;
import demo.vo.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@Api(tags = {"用户相关接口"})
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/userLogin")
    @ApiOperation("用户登录")
    public Result userLogin(@RequestParam("username") String username, @RequestParam("password") String password, HttpServletRequest request) {
        System.out.println("/userLogin");
        Result result = userService.validateUser(username, password);
        if (result.getData().getClass() == UserVO.class)
            request.getSession().setAttribute("user", ((UserVO) result.getData()).getUserId());
        return result;
    }

    @PostMapping("/userRegister")
    @ApiOperation("用户注册")
    public Result userRegister(@RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("nickname") String nickname) {
        System.out.println("/userRegister");
        Result result = userService.userRegister(username, password, nickname);
        return result;
    }

    @GetMapping("/userMessage")
    @ApiOperation("获取用户信息")
    public Result getUserMessage(HttpServletRequest request) {
        String userId = (String) request.getSession().getAttribute("user");
        if (StringUtils.isEmpty(userId))
            throw new BusinessException(ErrorCode.BAD_REQUEST_COMMON, "用户未登录");
        return userService.getUserMessage(userId);
    }

    @GetMapping("/getUserFollow")
    @ApiOperation("获取用户关注游戏列表")
    public Result getUserFollowGame(HttpServletRequest request) {
        String userId = (String) request.getSession().getAttribute("user");
        if (StringUtils.isEmpty(userId))
            throw new BusinessException(ErrorCode.BAD_REQUEST_COMMON, "用户未登录");
        return Result.OK().data(userService.getUserLikeGame(userId)).build();
    }

    @PostMapping("/avatar")
    @ApiOperation("修改用户头像")
    public Result uploadAvatar(MultipartFile file, HttpServletRequest request) {
        String userId = (String) request.getSession().getAttribute("user");
        if (StringUtils.isEmpty(userId))
            throw new BusinessException(ErrorCode.BAD_REQUEST_COMMON, "用户未登录");

        Integer result = userService.changeAvatar(file, userId);
        if (result > 0) return Result.OK().data("修改用户头像成功").build();

        return Result.BAD().data("修改用户头像过程出现未知错误").build();
    }

    @PostMapping("/changeNickname")
    @ApiOperation("修改用户昵称")
    public Result changeInfo(String nickname, HttpServletRequest request) {
        String userId = (String) request.getSession().getAttribute("user");
        if (StringUtils.isEmpty(userId))
            throw new BusinessException(ErrorCode.BAD_REQUEST_COMMON, "用户未登录");

        Integer result = userService.changeNickname(userId, nickname);
        if (result > 0) return Result.OK().data("修改用户昵称成功").build();

        return Result.BAD().data("修改昵称过程出现未知错误").build();
    }

    @PostMapping("/changePassword")
    @ApiOperation("修改用户密码")
    public Result changePwd(@RequestBody PasswordBO pwdBO, HttpServletRequest request) {
        String userId = (String) request.getSession().getAttribute("user");
        if (StringUtils.isEmpty(userId))
            throw new BusinessException(ErrorCode.BAD_REQUEST_COMMON, "用户未登录");

        Integer result = userService.changePwd(pwdBO.getOldPwd(), pwdBO.getNewPwd(), userId);
        if (result > 0) return Result.OK().data("修改密码成功").build();

        return Result.BAD().data("修改密码过程中出现未知错误").build();
    }

    @ApiOperation("绑定用户和游戏")
    @PostMapping("/bindToGame")
    public Result bindToGame(@RequestParam(value = "gameId", required = false) String gameId, HttpServletRequest request) {
        String userId = (String) request.getSession().getAttribute("user");
        if (StringUtils.isEmpty(userId))
            throw new BusinessException(ErrorCode.BAD_REQUEST_COMMON, "用户未登录");

        Integer res = userService.bindUserToGame(userId, gameId);
        if (res > 0) return Result.OK().data("成功绑定用户到指定游戏").build();

        return Result.BAD().data("绑定用户到游戏过程出现未知错误").build();
    }
}
