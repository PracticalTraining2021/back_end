package demo.controller;

import demo.service.UserService;
import demo.utils.JwtUtil;
import demo.vo.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class UserController {

    @Resource
    private JwtUtil jwtUtil;

    @Resource
    private UserService userService;

    @PostMapping("/userLogin")
    public Result userLogin(@RequestParam("username") String username, @RequestParam("password") String password) {
        System.out.println("/userLogin");
        Result result = userService.validateUser(username, password);
        return result;
    }

    @PostMapping("/userRegister")
    public Result userRegister(@RequestParam("username") String username, @RequestParam("password") String password) {
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
