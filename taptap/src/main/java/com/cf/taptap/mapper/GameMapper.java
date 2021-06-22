package com.cf.taptap.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cf.taptap.domain.Game;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface GameMapper extends BaseMapper<Game> {
}
