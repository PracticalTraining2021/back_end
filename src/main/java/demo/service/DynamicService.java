package demo.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import demo.domain.*;
import demo.exception.BusinessException;
import demo.exception.ErrorCode;
import demo.mapper.*;
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
    @Resource
    private UserCollectDynamicMapper userCollectDynamicMapper;

    @Resource
    private UserService userService;

    public void createDynamic(DynamicVO dynamicVO, String userId) {
        Game game = gameMapper.selectById(dynamicVO.getGameId());
        if (game == null) {
            throw new BusinessException(ErrorCode.BAD_REQUEST_COMMON, "找不到指定的游戏(gameId不存在)");
        }
        Dynamic dynamic = new Dynamic();
        BeanUtils.copyProperties(dynamicVO, dynamic);
        dynamic.setUserId(userId);
        dynamic.setLikesCount(0);
        dynamic.setPublishAt((new Date()).getTime());
        System.out.println(dynamic.getPublishAt());
        dynamicMapper.insert(dynamic);
    }

    private List<DynamicUserGameVO> transferDynamic(List<Dynamic> list, String userId) {
        List<DynamicUserGameVO> transferList = new ArrayList<>();
        for (Dynamic i : list) {
            DynamicUserGameVO dynamicUserGameVO = new DynamicUserGameVO();
            User user = userMapper.selectById(i.getUserId());
            Game game = gameMapper.selectById(i.getGameId());
            user.setPassword("***");
            BeanUtils.copyProperties(i, dynamicUserGameVO);
            dynamicUserGameVO.setGame(game);
            dynamicUserGameVO.setUser(user);
            dynamicUserGameVO.setIsLike(UserIsLike(userId, i.getDynamicId()));
            dynamicUserGameVO.setIsCollect(UserIsCollect(userId, i.getDynamicId()));
            transferList.add(dynamicUserGameVO);
        }
        return transferList;
    }


    public List<DynamicUserGameVO> selectAllDynamic(String userId) {
        List<Dynamic> list = dynamicMapper.selectList(new QueryWrapper<Dynamic>());
        list.sort(new Comparator<Dynamic>() {
            @Override
            public int compare(Dynamic o1, Dynamic o2) {

                return (int) (o2.getPublishAt() - o1.getPublishAt());
            }
        });
        return transferDynamic(list, userId);
    }

    public List<DynamicUserGameVO> getUserDynamic(String userId) {
        return transferDynamic(dynamicMapper.selectList(new QueryWrapper<Dynamic>().eq("user_id", userId)), userId);
    }

    public void updateUserDynamic(String userId, String dynamicId, String content) throws Exception {
        Dynamic dynamic = dynamicMapper.selectById(dynamicId);
        if (!userId.equals(dynamic.getUserId())) throw new Exception("非用户自己所属的动态");
        dynamic.setContent(content);
        dynamic.setPublishAt((new Date()).getTime());
        dynamicMapper.update(dynamic, new QueryWrapper<Dynamic>().eq("dynamic_id", dynamicId));
    }

    public void deleteUserDynamic(String userId, String dynamicId) throws Exception {
        Dynamic dynamic = dynamicMapper.selectById(dynamicId);
        if (dynamic == null) throw new Exception("没有这条动态");
        if (!userId.equals(dynamic.getUserId())) throw new Exception("非用户自己所属的动态");
        dynamicMapper.delete(new QueryWrapper<Dynamic>().eq("dynamic_id", dynamicId));
    }

    public List<DynamicUserGameVO> findDynamicByGameId(String gameId, String userId) {
        List<Dynamic> list = dynamicMapper.selectList(new QueryWrapper<Dynamic>().eq("game_id", gameId));
        list.sort(new Comparator<Dynamic>() {
            @Override
            public int compare(Dynamic o1, Dynamic o2) {
                return (int) (o2.getPublishAt() - o1.getPublishAt());
            }
        });
        return transferDynamic(list, userId);
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

    public List<DynamicUserGameVO> getDynamicById(String dynamicId, String userId) {
        List<Dynamic> list = dynamicMapper.selectList(new QueryWrapper<Dynamic>().eq("dynamic_id", dynamicId));
        return transferDynamic(list, userId);
    }

    public void favoritesDynamic(String userId, String dynamicId) throws Exception {
        if (UserIsCollect(userId, dynamicId) == 0) {
            UserCollectDynamic userCollectDynamic = new UserCollectDynamic();
            userCollectDynamic.setDynamicId(dynamicId);
            userCollectDynamic.setUserId(userId);
            userCollectDynamicMapper.insert(userCollectDynamic);
        } else {
            throw new Exception("已收藏");
        }
    }

    public void deleteFavouritesDynamic(String userId, String dynamicId) throws Exception {
        if (UserIsCollect(userId, dynamicId) == 1) {
            userCollectDynamicMapper.delete(new QueryWrapper<UserCollectDynamic>().eq("user_id", userId).eq("dynamic_id", dynamicId));
        } else {
            throw new Exception("没有收藏");
        }
    }

    private int UserIsLike(String userId, String dynamicId) {
        if (null == userLikesDynamicMapper.selectOne(new QueryWrapper<UserLikesDynamic>().eq("user_id", userId).eq("dynamic_id", dynamicId))) {
            return 0;
        } else return 1;
    }

    private int UserIsCollect(String userId, String dynamicId) {
        if (null == userCollectDynamicMapper.selectOne(new QueryWrapper<UserCollectDynamic>().eq("user_id", userId).eq("dynamic_id", dynamicId))) {
            return 0;
        } else return 1;
    }

    public List<DynamicUserGameVO> getMyFavouritesDynamic(String userId) {
        List<String> dynamicIds = userCollectDynamicMapper.getMyFavouritesDynamic(userId);
        if (dynamicIds == null || dynamicIds.size() == 0) return new ArrayList<>();
        List<Dynamic> dynamicList = dynamicMapper.selectBatchIds(dynamicIds);
        dynamicList.sort(new Comparator<Dynamic>() {
            @Override
            public int compare(Dynamic o1, Dynamic o2) {

                return (int) (o2.getPublishAt() - o1.getPublishAt());
            }
        });
        return transferDynamic(dynamicList, userId);
    }

    public List<DynamicUserGameVO> getMyFavouritesGameDynamic(String userId) {
        List<String> gameIdList = userService.getUserLikeGameIdList(userId);
        List<Dynamic> dynamicList = new ArrayList<>();
        for (String gameId : gameIdList) {
            List<Dynamic> list = dynamicMapper.getMyFavouritesGameDynamicByGameId(gameId);
            dynamicList.addAll(list);
        }
        dynamicList.sort(new Comparator<Dynamic>() {
            @Override
            public int compare(Dynamic o1, Dynamic o2) {

                return (int) (o2.getPublishAt() - o1.getPublishAt());
            }
        });
        return transferDynamic(dynamicList, userId);
    }

//    public static void main(String[] args) {
//        System.out.println((new Date()).getTime());
//        System.out.println(new java.sql.Date((new Date()).getTime()));
//    }
}
