package demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import demo.domain.UserCollectDynamic;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserCollectDynamicMapper extends BaseMapper<UserCollectDynamic> {

    @Select("select dynamic_id from user_collect_dynamic where user_id = #{userId}")
    List<String> getMyFavouritesDynamic(@Param("userId") String userId);
}
