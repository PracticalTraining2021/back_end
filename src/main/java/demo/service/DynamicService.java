package demo.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import demo.domain.Dynamic;
import demo.domain.Game;
import demo.domain.User;
import demo.domain.UserLikesDynamic;
import demo.mapper.DynamicMapper;
import demo.mapper.GameMapper;
import demo.mapper.UserLikesDynamicMapper;
import demo.mapper.UserMapper;
import demo.vo.DynamicUserGameVO;
import demo.vo.DynamicVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;


@Service
public class DynamicService {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Resource
    private DynamicMapper dynamicMapper;
    @Resource
    private GameMapper gameMapper;
    @Resource
    private UserMapper userMapper;

    @Resource
    private UserLikesDynamicMapper userLikesDynamicMapper;

    public void createDynamic(DynamicVO dynamicVO, String userId) {
        Dynamic dynamic = new Dynamic();
        BeanUtils.copyProperties(dynamicVO, dynamic);
        dynamic.setUserId(userId);
        dynamic.setLikesCount(0);
        dynamic.setPublishAt(new Date());
        dynamicMapper.insert(dynamic);
    }

    private List<DynamicUserGameVO> transferDynamic(List<Dynamic> list,String userId)
    {
        List<DynamicUserGameVO> transferList=new ArrayList<>();
        for(Dynamic i: list)
        {
            DynamicUserGameVO dynamicUserGameVO=new DynamicUserGameVO();
            User user=userMapper.selectById(i.getUserId());
            Game game=gameMapper.selectById(i.getGameId());
            user.setPassword("***");
            BeanUtils.copyProperties(i, dynamicUserGameVO);
            dynamicUserGameVO.setGame(game);
            dynamicUserGameVO.setUser(user);
            dynamicUserGameVO.setIsLike(UserIsLike(userId,i.getDynamicId()));
            transferList.add(dynamicUserGameVO);
        }
        return transferList;
    }


    public List<DynamicUserGameVO> selectAllDynamic(String userId) {
        List<Dynamic> list = dynamicMapper.selectList(new QueryWrapper<Dynamic>());
        list.sort(new Comparator<Dynamic>() {
            @Override
            public int compare(Dynamic o1, Dynamic o2) {
                return o2.getPublishAt().compareTo(o1.getPublishAt());
            }
        });
        return transferDynamic(list,userId);
    }

    public List<DynamicUserGameVO> getUserDynamic(String userId) {
        return transferDynamic(dynamicMapper.selectList(new QueryWrapper<Dynamic>().eq("user_id", userId)),userId);
    }

    public void updateUserDynamic(String userId, String dynamicId, String content) throws Exception {
        Dynamic dynamic = dynamicMapper.selectById(dynamicId);
        if (!userId.equals(dynamic.getUserId())) throw new Exception("非用户自己所属的动态");
        dynamic.setContent(content);
        dynamic.setPublishAt(new Date());
        dynamicMapper.update(dynamic, new QueryWrapper<Dynamic>().eq("dynamic_id", dynamicId));
    }

    public void deleteUserDynamic(String userId, String dynamicId) throws Exception {
        Dynamic dynamic = dynamicMapper.selectById(dynamicId);
        if (dynamic == null) throw new Exception("没有这条动态");
        if (!userId.equals(dynamic.getUserId())) throw new Exception("非用户自己所属的动态");
        dynamicMapper.delete(new QueryWrapper<Dynamic>().eq("dynamic_id", dynamicId));
    }

    public List<DynamicUserGameVO> findDynamicByGameId(String gameId,String userId) {
        List<Dynamic> list = dynamicMapper.selectList(new QueryWrapper<Dynamic>().eq("game_id", gameId));
        list.sort(new Comparator<Dynamic>() {
            @Override
            public int compare(Dynamic o1, Dynamic o2) {
                return o2.getPublishAt().compareTo(o1.getPublishAt());
            }
        });
        return transferDynamic(list,userId);
    }

    public void giveDynamicLikes(String userId, String dynamicId) throws Exception {
        UserLikesDynamic userLikesDynamic = new UserLikesDynamic();
        userLikesDynamic.setDynamicId(dynamicId);
        userLikesDynamic.setUserId(userId);
        if (null == userLikesDynamicMapper.selectOne(new QueryWrapper<UserLikesDynamic>().eq("user_id", userId).eq("dynamic_id", dynamicId))) {
            Dynamic dynamic = dynamicMapper.selectById(dynamicId);
            dynamic.setLikesCount(dynamic.getLikesCount() + 1);
            dynamicMapper.update(dynamic, new QueryWrapper<Dynamic>().eq("dynamic_id", dynamicId));
            userLikesDynamicMapper.insert(userLikesDynamic);
        } else {
            throw new Exception("已点赞");
        }


    }

    public List<DynamicUserGameVO> getDynamicById(String dynamicId,String userId)
    {
        List<Dynamic> list=dynamicMapper.selectList(new QueryWrapper<Dynamic>().eq("dynamic_id",dynamicId));
        return transferDynamic(list,userId);
    }

    private int UserIsLike(String userId,String dynamicId)
    {
        if(null == userLikesDynamicMapper.selectOne(new QueryWrapper<UserLikesDynamic>().eq("user_id", userId).eq("dynamic_id", dynamicId)))
        {
            return 0;
        }
        else return 1;
    }


}
