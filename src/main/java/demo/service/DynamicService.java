package demo.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import demo.domain.*;
import demo.exception.BusinessException;
import demo.exception.ErrorCode;
import demo.mapper.*;
import demo.vo.DynamicUserGameVO;
import demo.vo.DynamicVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.util.*;


@Service
public class DynamicService {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Resource
    private ImageService imageService;

    @Value("${prefix}")
    private String prefix;

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

    public Dynamic getDynamicByDynamicId(String dynamicId) {
        return dynamicMapper.selectById(dynamicId);
    }

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

    public String uploadImg(MultipartFile file) {
        String imgPrefix = "http://119.91.130.198/images/";
        IdentifierGenerator idGen = new DefaultIdentifierGenerator();
        String filePath = "dynamic/" + idGen.nextUUID(file) + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        imageService.uploadImg(file, filePath);
        return imgPrefix + filePath;
    }

    public void deleteImg(String url) {
        int index = url.indexOf("dynamic");
        if (index < 0) return;
        String filePath = prefix + url.substring(index);
        File file = new File(filePath);
        log.info("===========手动删除动态图片：" + file.getAbsolutePath() + "================");
        if (file.exists())
            file.delete();
    }

    public Set<String> getAllImgUrls() {
        List<String> originUrls = dynamicMapper.getAllImgUrls();

        File file = null;
        Set<String> result = new HashSet<>();
        if (originUrls != null) {
            for (String img_urls : originUrls) {
                String[] temp = img_urls.split("\\|");
                for (String singleUrl : temp) {
                    int index = singleUrl.lastIndexOf("dynamic");
                    if (index > 0) {
                        file = new File(prefix + singleUrl.substring(index));
                        result.add(file.getAbsolutePath());
                    }
                }
            }
        }

        return result;
    }

    public List<Long> getPublishAtListByGameId(String gameId) {
        return dynamicMapper.getPublishAtListByGameId(gameId);
    }

//    public static void main(String[] args) {
//        String url = "http://119.91.130.198/images/dynamic/1625470512520smduck.png";
//        System.out.println(url.substring(url.indexOf("dynamic")));
//    }
}
