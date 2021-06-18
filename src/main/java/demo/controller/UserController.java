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
@Api("用户相关接口")
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

    @GetMapping("/auth/test")
    public Result test() {
        return Result.OK().data("auth---test").build();
    }

    @GetMapping("/hello")
    public Result hello() {
        return Result.OK().data("hello").build();
    }

}
