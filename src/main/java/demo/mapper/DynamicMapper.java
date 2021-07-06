package demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import demo.domain.Dynamic;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DynamicMapper extends BaseMapper<Dynamic> {

    @Select("select * from dynamic where game_id = #{gameId}")
    List<Dynamic> getMyFavouritesGameDynamicByGameId(@Param("gameId") String gameId);

    @Select("select img_urls from dynamic")
    List<String> getAllImgUrls();

    @Select("select publish_at from dynamic where game_id = #{gameId}")
    List<Long> getPublishAtListByGameId(@Param("gameId") String gameId);
}
