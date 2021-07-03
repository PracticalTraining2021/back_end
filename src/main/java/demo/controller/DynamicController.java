package demo.controller;


import demo.domain.Dynamic;
import demo.exception.BusinessException;
import demo.exception.ErrorCode;
import demo.service.DynamicService;
import demo.service.ImageService;
import demo.vo.DynamicUserGameVO;
import demo.vo.DynamicVO;
import demo.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

@RestController
@Api(tags = {"动态管理"})
public class DynamicController {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Resource
    private DynamicService dynamicService;

    @Resource
    private ImageService imageService;

    @PostMapping("/createDynamic")
    @ApiOperation("创建动态")
    public Result createDynamic(DynamicVO dynamic, MultipartFile multipartFile, HttpServletRequest request) {
        String userId = (String) request.getSession().getAttribute("user");
        if (StringUtils.isEmpty(userId))
            throw new BusinessException(ErrorCode.BAD_REQUEST_COMMON, "用户未登录");

        String dynamicMidPath = "dynamic/";
        imageService.uploadImg(multipartFile, dynamicMidPath);
        dynamic.setImgUrls("http://119.91.130.198/images/" + dynamicMidPath + multipartFile.getOriginalFilename());
        dynamicService.createDynamic(dynamic, userId);
        return Result.OK().data("动态创建成功").build();
    }

    @GetMapping("/getAllDynamic")
    @ApiOperation("按时间获取所有动态")
    public Result getAllDynamic(HttpServletRequest request) {
        String userId = (String) request.getSession().getAttribute("user");
        if (StringUtils.isEmpty(userId))
            throw new BusinessException(ErrorCode.BAD_REQUEST_COMMON, "用户未登录");
        return Result.OK().data(dynamicService.selectAllDynamic(userId)).build();
    }

    @GetMapping("/getUserDynamic")
    @ApiOperation("查看用户自己的动态")
    public Result getUserDynamic(HttpServletRequest request) {
        String userId = (String) request.getSession().getAttribute("user");
        if (StringUtils.isEmpty(userId))
            throw new BusinessException(ErrorCode.BAD_REQUEST_COMMON, "用户未登录");
        return Result.OK().data(dynamicService.getUserDynamic(userId)).build();
    }

    @PostMapping("/updateDynamic")
    @ApiOperation("用户更新自己的动态")
    public Result updateUserDynamic(HttpServletRequest request, @RequestParam("dynamicId") String dynamicId, @RequestParam("content") String content) throws Exception {
        String userId = (String) request.getSession().getAttribute("user");
        if (StringUtils.isEmpty(userId))
            throw new BusinessException(ErrorCode.BAD_REQUEST_COMMON, "用户未登录");

        Dynamic dynamic = dynamicService.getDynamicByDynamicId(dynamicId);
        if (Objects.isNull(dynamic))
            throw new BusinessException(ErrorCode.BAD_REQUEST_COMMON, "该动态不存在");
        if (!Objects.equals(dynamic.getUserId(), userId))
            throw new BusinessException(ErrorCode.BAD_REQUEST_COMMON, "该用户没有权限修改其他用户的动态");

        dynamicService.updateUserDynamic(userId, dynamicId, content);
        return Result.OK().data("动态修改成功").build();
    }

    @PostMapping("/deleteDynamic")
    @ApiOperation("删除用户的动态")
    public Result deleteUserDynamic(HttpServletRequest request, @RequestParam("dynamicId") String dynamicId) throws Exception {
        String userId = (String) request.getSession().getAttribute("user");
        if (StringUtils.isEmpty(userId))
            throw new BusinessException(ErrorCode.BAD_REQUEST_COMMON, "用户未登录");
        dynamicService.deleteUserDynamic(userId, dynamicId);
        return Result.OK().build();
    }

    @GetMapping("/getDynamicByGameId")
    @ApiOperation("按游戏获取动态")
    public Result findDynamicByGameId(@RequestParam("gameId") String gameId, HttpServletRequest request) {
        String userId = (String) request.getSession().getAttribute("user");
        if (StringUtils.isEmpty(userId))
            throw new BusinessException(ErrorCode.BAD_REQUEST_COMMON, "用户未登录");
        return Result.OK().data(dynamicService.findDynamicByGameId(gameId, userId)).build();
    }

    @PostMapping("/giveDynamicLike")
    @ApiOperation("点赞")
    public Result giveDynamicLike(HttpServletRequest request, @RequestParam("dynamicId") String dynamicId) throws Exception {
        String userId = (String) request.getSession().getAttribute("user");
        if (StringUtils.isEmpty(userId))
            throw new BusinessException(ErrorCode.BAD_REQUEST_COMMON, "用户未登录");
        dynamicService.giveDynamicLikes(userId, dynamicId);
        return Result.OK().build();
    }

    @GetMapping("/getDynamicById")
    @ApiOperation("按动态id拿动态")
    public Result getDynamicById(@RequestParam("dynamicId") String dynamicId, HttpServletRequest request) {
        String userId = (String) request.getSession().getAttribute("user");
        if (StringUtils.isEmpty(userId))
            throw new BusinessException(ErrorCode.BAD_REQUEST_COMMON, "用户未登录");
        return Result.OK().data(dynamicService.getDynamicById(dynamicId, userId)).build();
    }

    @PostMapping("/favouritesDynamic")
    @ApiOperation("收藏动态")
    public Result favouritesDynamic(@RequestParam("dynamicId") String dynamicId, HttpServletRequest request) throws Exception {
        String userId = (String) request.getSession().getAttribute("user");
        if (StringUtils.isEmpty(userId))
            throw new BusinessException(ErrorCode.BAD_REQUEST_COMMON, "用户未登录");
        dynamicService.favoritesDynamic(userId, dynamicId);
        return Result.OK().build();
    }

    @PostMapping("/cancelFavouritesDynamic")
    @ApiOperation("取消收藏")
    public Result cancelFavouritesDynamic(@RequestParam("dynamicId") String dynamicId, HttpServletRequest request) throws Exception {
        String userId = (String) request.getSession().getAttribute("user");
        if (StringUtils.isEmpty(userId))
            throw new BusinessException(ErrorCode.BAD_REQUEST_COMMON, "用户未登录");
        dynamicService.deleteFavouritesDynamic(userId, dynamicId);
        return Result.OK().build();
    }

    @GetMapping("/getMyFavouritesDynamic")
    @ApiOperation("获取我的收藏动态")
    public Result getMyFavouritesDynamic(HttpServletRequest request) {
        String userId = (String) request.getSession().getAttribute("user");
        if (StringUtils.isEmpty(userId))
            throw new BusinessException(ErrorCode.BAD_REQUEST_COMMON, "用户未登录");
        List<DynamicUserGameVO> result = dynamicService.getMyFavouritesDynamic(userId);
        return Result.OK().data(result).build();
    }

    @GetMapping("/getMyFavouritesGameDynamic")
    @ApiOperation("获取我的收藏游戏的动态")
    public Result getMyFavouritesGameDynamic(HttpServletRequest request) {
        String userId = (String) request.getSession().getAttribute("user");
        if (StringUtils.isEmpty(userId))
            throw new BusinessException(ErrorCode.BAD_REQUEST_COMMON, "用户未登录");
        List<DynamicUserGameVO> result = dynamicService.getMyFavouritesGameDynamic(userId);
        return Result.OK().data(result).build();
    }
}
