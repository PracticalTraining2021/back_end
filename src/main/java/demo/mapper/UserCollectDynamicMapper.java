package demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import demo.domain.UserCollectDynamic;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserCollectDynamicMapper extends BaseMapper<UserCollectDynamic> {
}
