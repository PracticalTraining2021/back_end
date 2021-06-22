package demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import demo.domain.UserLikesDynamic;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserLikesDynamicMapper extends BaseMapper<UserLikesDynamic> {
}
