package com.cf.taptap.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cf.taptap.domain.UserLikesGame;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserLikesGameMapper extends BaseMapper<UserLikesGame> {

    @Select("SELECT game_id FROM user_likes_game WHERE user_id=#{userId}")
    public List<String> selectGameId(@Param("userId") String userId);
}
