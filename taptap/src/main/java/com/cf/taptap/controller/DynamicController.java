package com.cf.taptap.controller;


import com.cf.taptap.domain.Dynamic;
import com.cf.taptap.service.DynamicService;
import com.cf.taptap.vo.DynamicVO;
import com.cf.taptap.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@Api("动态管理")
public class DynamicController {

    private Logger log= LoggerFactory.getLogger(getClass());

    @Resource
    private DynamicService dynamicService;


    @PostMapping("/createDynamic")
    @ApiOperation("创建动态")
    public Result createDynamic(@RequestBody DynamicVO dynamic, HttpServletRequest request)
    {
        String userId=request.getSession().getAttribute("user").toString();
        dynamicService.createDynamic(dynamic,userId);
        return Result.OK().build();
    }

    @GetMapping("/getAllDynamic")
    @ApiOperation("按时间过去所有动态")
    public Result getAllDynamic()
    {
        return Result.OK().data(dynamicService.selectAllDynamic()).build();
    }

    @GetMapping("/getUserDynamic")
    @ApiOperation("查看用户自己的动态")
    public  Result getUserDynamic(HttpServletRequest request)
    {
        String userId=request.getSession().getAttribute("user").toString();
         return Result.OK().data(dynamicService.getUserDynamic(userId)).build();
    }

    @PostMapping("/updateDynamic")
    @ApiOperation("用户更新自己的动态")
    public Result updateUserDynamic(HttpServletRequest request, @RequestParam("dynamicId") String dynamicId,@RequestParam("content")String content) throws Exception {
        String userId=request.getSession().getAttribute("user").toString();
           dynamicService.updateUserDynamic(userId,dynamicId,content);
           return  Result.OK().build();
    }

    @PostMapping("/deleteDynamic")
    @ApiOperation("删除用户的动态")
    public Result deleteUserDynamic(HttpServletRequest request,@RequestParam("dynamicId")String dynamicId) throws Exception {
        String userId=request.getSession().getAttribute("user").toString();
        dynamicService.deleteUserDynamic(userId,dynamicId);
        return Result.OK().build();
    }

    @GetMapping("/getDynamicByGameId")
    @ApiOperation("按游戏获取动态")
    public Result findDynamicByGameId(@RequestParam("gameId") String gameId)
    {
        return Result.OK().data(dynamicService.findDynamicByGameId(gameId)).build();
    }

    @PostMapping("/giveDynamicLike")
    @ApiOperation("点赞")
    public Result giveDynamicLike(HttpServletRequest request,@RequestParam("dynamicId") String dynamicId) throws Exception {
        String userId=request.getSession().getAttribute("user").toString();
        dynamicService.giveDynamicLikes(userId,dynamicId);
        return Result.OK().build();
    }

}
