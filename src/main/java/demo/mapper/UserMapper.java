package demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import demo.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper extends BaseMapper<User> {

    @Select("select * from user where username = #{username}")
    User getUserByUsername(@Param("username") String username);

    @Select("select password from user where username = #{username}")
    String getPasswordByUsername(@Param("username") String username);

}
