package demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import demo.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper extends BaseMapper<User> {

    @Select("select * from user where username = #{username}")
    User getUserByUsername(@Param("username") String username);

    @Select("select password from user where username = #{username}")
    String getPasswordByUsername(@Param("username") String username);

    @Update("update user set nickname = #{nickname} where user_id = #{userId}")
    Integer changeNickname(@Param("userId") String userId, @Param("nickname") String nickname);

    @Update("update user set avatar = #{avatar} where user_id = #{userId}")
    Integer changeAvatar(@Param("userId") String userId, @Param("avatar") String avatar);

    @Update("update user set password = #{newPwd} where user_id = #{userId}")
    Integer updatePwd(@Param("newPwd") String newPwd, @Param("userId") String userId);

    @Select("select count(*) from user where nickname = #{nickname}")
    Integer getCountByNickname(@Param("nickname") String nickname);
}
