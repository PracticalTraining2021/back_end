package com.cf.taptap.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cf.taptap.domain.Dynamic;
import com.cf.taptap.domain.UserLikesDynamic;
import com.cf.taptap.mapper.DynamicMapper;
import com.cf.taptap.mapper.UserLikesDynamicMapper;
import com.cf.taptap.vo.Convert;
import com.cf.taptap.vo.DynamicVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class DynamicService {

    private Logger log= LoggerFactory.getLogger(getClass());

    @Resource
    private DynamicMapper dynamicMapper;

    @Resource
    private UserLikesDynamicMapper userLikesDynamicMapper;

    public void createDynamic(DynamicVO dynamicVO,String userId){
        Dynamic dynamic= Convert.INSTANCE.convert(dynamicVO);
        dynamic.setUserId(userId);
        dynamic.setLikesCount(0);
        dynamic.setPublishAt(new Date());
        dynamicMapper.insert(dynamic);
    }


    public List<Dynamic> selectAllDynamic()
    {
        List<Dynamic> list=dynamicMapper.selectList(new QueryWrapper<Dynamic>());
        list.sort(new Comparator<Dynamic>() {
            @Override
            public int compare(Dynamic o1, Dynamic o2) {
                return o2.getPublishAt().compareTo(o1.getPublishAt());
            }
        });
        return  list;
    }

    public  List<Dynamic> getUserDynamic(String userId)
    {
        return dynamicMapper.selectList(new QueryWrapper<Dynamic>().eq("user_id",userId));
    }

    public void updateUserDynamic(String userId,String dynamicId,String content) throws Exception {
        Dynamic dynamic=dynamicMapper.selectById(dynamicId);
        if(!userId.equals(dynamic.getUserId())) throw new Exception("非用户自己所属的动态");
        dynamic.setContent(content);
        dynamic.setPublishAt(new Date());
        dynamicMapper.update(dynamic,new QueryWrapper<Dynamic>().eq("dynamic_id",dynamicId));
    }

    public void deleteUserDynamic(String userId,String dynamicId) throws Exception {
        Dynamic dynamic=dynamicMapper.selectById(dynamicId);
        if(dynamic==null) throw new Exception("没有这条动态");
        if(!userId.equals(dynamic.getUserId())) throw new Exception("非用户自己所属的动态");
        dynamicMapper.delete(new QueryWrapper<Dynamic>().eq("dynamic_id",dynamicId));
    }

    public List<Dynamic> findDynamicByGameId(String gameId)
    {
        List<Dynamic> list=dynamicMapper.selectList(new QueryWrapper<Dynamic>().eq("game_id",gameId));
        list.sort(new Comparator<Dynamic>() {
            @Override
            public int compare(Dynamic o1, Dynamic o2) {
                return o2.getPublishAt().compareTo(o1.getPublishAt());
            }
        });
        return list;
    }

    public void giveDynamicLikes(String userId,String dynamicId) throws Exception {
        UserLikesDynamic userLikesDynamic=new UserLikesDynamic();
        userLikesDynamic.setDynamicId(dynamicId);
        userLikesDynamic.setUserId(userId);
        if(null==userLikesDynamicMapper.selectOne(new QueryWrapper<UserLikesDynamic>().eq("user_id",userId).eq("dynamic_id",dynamicId))){
            Dynamic dynamic=dynamicMapper.selectById(dynamicId);
            dynamic.setLikesCount(dynamic.getLikesCount()+1);
            dynamicMapper.update(dynamic,new QueryWrapper<Dynamic>().eq("dynamic_id",dynamicId));
            userLikesDynamicMapper.insert(userLikesDynamic);
        }
        else{
            throw  new Exception("已点赞");
        }


    }

}
