package demo.controller;


import demo.service.UserService;
import demo.vo.Result;
import demo.vo.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
        request.getSession().setAttribute("user", ((UserVO) result.getData()).getUserId());
        return result;
    }

    @PostMapping("/userRegister")
    @ApiOperation("用户注册")
    public Result userRegister(@RequestParam("username") String username, @RequestParam("password") String password) {
        System.out.println("/userRegister");
        Result result = userService.userRegister(username, password);
        return result;
    }

    @GetMapping("/userMessage")
    @ApiOperation("获取用户信息")
    public Result getUserMessage(HttpServletRequest request) {
        String userId = request.getSession().getAttribute("user").toString();
        return userService.getUserMessage(userId);
    }

    @PostMapping("/gameFollow")
    @ApiOperation("用户关注游戏")
    public Result userFollowGame(HttpServletRequest request, @RequestParam("gameId") String gameId) throws Exception {
        String userId = request.getSession().getAttribute("user").toString();
        userService.userFollowGame(userId, gameId);
        return Result.OK().build();
    }

    @GetMapping("/getUserFollow")
    @ApiOperation("获取用户关注游戏列表")
    public Result getUserFollowGame(HttpServletRequest request) {
        String userId = request.getSession().getAttribute("user").toString();
        return Result.OK().data(userService.getUserLikeGame(userId)).build();
    }

}
