package demo.controller;


import cn.hutool.crypto.asymmetric.RSA;
import demo.bo.LoginBO;
import demo.bo.PasswordBO;
import demo.bo.RegisterBO;
import demo.exception.BusinessException;
import demo.exception.ErrorCode;
import demo.service.UserService;
import demo.utils.RSAUtils;
import demo.vo.Result;
import demo.vo.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Objects;

@RestController
@Api(tags = {"用户相关接口"})
public class UserController {

    private static Logger logger = LoggerFactory.getLogger(UserService.class);

    private static final String KEYPAIR = "KEYPAIR";

    private static final String RSA_KEYPAIR = "rsa";

    @Resource
    private UserService userService;

    @PostMapping("/userLogin")
    @ApiOperation("用户登录")
    public Result userLogin(@RequestParam("username") String username, @RequestParam("password") String password, HttpServletRequest request) {
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

    @ApiOperation("用户注销")
    @PostMapping("/userLogout")
    public Result userLogout(HttpServletRequest request) {
        request.getSession().removeAttribute("user");
        return Result.OK().data("用户注销成功").build();
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

    @ApiOperation("获取-公钥")
    @GetMapping("/publicKey")
    public Result getHuPublicKey(HttpServletRequest request) {
        RSA rsa = new RSA();
        request.getSession().setAttribute(RSA_KEYPAIR, rsa);
        return Result.OK().data(rsa.getPublicKeyBase64()).build();
    }

    @ApiOperation("用户-加密登录")
    @PostMapping("/encryptedLogin")
    public Result huEncryptedLogin(@RequestBody LoginBO loginBO, HttpServletRequest request) {
        RSA rsa = (RSA) request.getSession().getAttribute(RSA_KEYPAIR);
        if (rsa == null) {
            request.getSession().removeAttribute(RSA_KEYPAIR);
            throw new BusinessException(ErrorCode.BAD_REQUEST_COMMON, "请先获取公钥加密后再登录");
        }

        UserVO userVO = userService.huEncryptedLogin(loginBO, rsa.getPrivateKeyBase64());
        request.getSession().removeAttribute(RSA_KEYPAIR);
        request.getSession().setAttribute("user", userVO.getUserId());

        return Result.OK().data(userVO).build();
    }

    @ApiOperation("用户-加密注册")
    @PostMapping("/encryptedRegister")
    public Result huEncryptedRegister(@RequestBody RegisterBO registerBO, HttpServletRequest request) {
        RSA rsa = (RSA) request.getSession().getAttribute(RSA_KEYPAIR);
        if (rsa == null) {
            request.getSession().removeAttribute(RSA_KEYPAIR);
            throw new BusinessException(ErrorCode.BAD_REQUEST_COMMON, "请先获取公钥加密后再登录");
        }

        UserVO userVO = userService.huEncryptedRegister(registerBO, rsa.getPrivateKeyBase64());
        request.getSession().removeAttribute(RSA_KEYPAIR);
        return Result.OK().data(userVO).build();
    }


//    =============================================================================================================

    //    @ApiOperation("获取公钥")
//    @GetMapping("/publicKey")
    public Result getPublicKey(HttpServletRequest request) {
        try {
            logger.error("获取公钥的sessionID：" + request.getSession().getId());
            Map<String, Object> keyPair = RSAUtils.genKeyPair();
            logger.error("keyPair:" + keyPair);
            request.getSession().setAttribute(KEYPAIR, keyPair);
            return Result.OK().data(RSAUtils.getPublicKey(keyPair)).build();
        } catch (NoSuchAlgorithmException e) {
            logger.error("获取密钥出现未知错误:");
            logger.error(e.getLocalizedMessage());
            throw new BusinessException(ErrorCode.SERVER_EXCEPTION, "请重新尝试获取密钥");
        }
    }

    //    @ApiOperation("用户加密登录")
//    @PostMapping("/encryptedLogin")
    public Result encryptedLogin(@RequestBody LoginBO loginBO, HttpServletRequest request) {
        logger.error("用户加密登录的密码：" + loginBO.getPassword());
        logger.error("用户加密登录的sessionID：" + request.getSession().getId());
        Map<String, Object> keyPair = (Map<String, Object>) request.getSession().getAttribute(KEYPAIR);
        logger.error("keyPair:" + keyPair);
        logger.error("privateKey:" + RSAUtils.getPrivateKey(keyPair));
        logger.error("publicKey:" + RSAUtils.getPublicKey(keyPair));
        if (Objects.isNull(keyPair)) {
            request.getSession().removeAttribute(KEYPAIR);
            throw new BusinessException(ErrorCode.BAD_REQUEST_COMMON, "请先获取公钥加密后再登录");
        }
        UserVO userVO = userService.encryptedLogin(loginBO, RSAUtils.getPrivateKey(keyPair));
        request.getSession().removeAttribute(KEYPAIR);
        return Result.OK().data(userVO).build();
    }

    //    @ApiOperation("用户加密注册")
//    @PostMapping("/encryptedRegister")
    public Result encryptedRegister(@RequestBody RegisterBO registerBO, HttpServletRequest request) {
        Map<String, Object> keyPair = (Map<String, Object>) request.getSession().getAttribute(KEYPAIR);
        if (Objects.isNull(keyPair)) {
            request.getSession().removeAttribute(KEYPAIR);
            throw new BusinessException(ErrorCode.BAD_REQUEST_COMMON, "请先获取公钥加密后再注册");
        }
        UserVO userVO = userService.encryptedRegister(registerBO, RSAUtils.getPrivateKey(keyPair));
        request.getSession().removeAttribute(KEYPAIR);
        return Result.OK().data(userVO).build();
    }
}
