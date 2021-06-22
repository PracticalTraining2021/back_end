package demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import demo.domain.Dynamic;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface DynamicMapper extends BaseMapper<Dynamic> {
}
