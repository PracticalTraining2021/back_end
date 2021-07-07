package demo.utils;

import demo.service.DynamicService;
import demo.service.GameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Component
@EnableScheduling
public class DeleteDynamicImagesUtil {

    private Logger logger = LoggerFactory.getLogger(DeleteDynamicImagesUtil.class);

    @Resource
    private DynamicService dynamicService;

    @Resource
    private GameService gameService;

    @Value("${prefix}")
    private String prefix;

    //    定时任务：每隔1小时计算一次游戏热度
    @Scheduled(fixedRate = 3600000)
    public void updateGameHeat() {
        logger.info("==================定时任务开始：计算游戏热度====================");
//        当前的分钟数
        Long currentMinute = new Date().getTime() / 3600000;

        List<String> gameIdList = gameService.getAllGameId();
        for (String gameId : gameIdList) {
//            获取指定游戏的所有动态的发布时间列表
            List<Long> publishAtLiOfTheGame = dynamicService.getPublishAtListByGameId(gameId);
//            计算游戏热度
            Double heat = Double.valueOf(0);
            for (Long time : publishAtLiOfTheGame) {
                time /= 3600000;
                Long temp = currentMinute - time;
                System.out.println("==========" + temp + "===========" + (double) 1 / temp + "================");
                if (temp == 0) {
                    heat += 1;
                    continue;
                }
                heat += (double) 1 / temp;
            }

            gameService.updateHeatByGameId(gameId, heat);
        }

        logger.info("==================定时任务结束：计算游戏热度====================");
    }


    //    定时任务：每隔一天清除冗余的动态图片
    @Scheduled(fixedRate = 86400000)
    public void deleteRedundancyImages() {
        logger.info("==================开始：清除冗余动态图片====================");
//        存在数据库动态表中的图片url
        Set<String> existImgUrls = dynamicService.getAllImgUrls();
        logger.info("existsImgUrls:");
        logger.info(existImgUrls.toString());

//        file指向文件系统中存放动态图片的目录
        File dir = new File(prefix + "dynamic/");
//        获取动态目录下文件列表
        File[] files = dir.listFiles();
        for (File file : files) {
//            如果图片非冗余则不删除
            if (existImgUrls.contains(file.getAbsolutePath())) {
                continue;
            }
//            删除冗余图片
            logger.info("deletePath:" + file.getAbsoluteFile());
//            file.delete();
        }

        logger.info("==================结束：清除冗余动态图片====================");
    }

    public static void main(String[] args) {
//        Long c = Long.valueOf(19);
//        List<Long> times = new ArrayList<>();
//        times.add(Long.valueOf(12));
//        times.add(Long.valueOf(12));
//        times.add(Long.valueOf(12));
//        Long heat = times.stream().reduce(Long.valueOf(0), (a, b) -> a + c - b);
//        System.out.println(heat);

//        File dir = new File("./images/" + "dynamic/");
//        File[] files = dir.listFiles();
//        for (File file : files) {
//            System.out.println(file.getAbsoluteFile());
//        }

//        Set<String> urls = new HashSet<>();
//        urls.add("aaa");
//        urls.add("bbb");
//        System.out.println(urls.toString());
    }
}
