package com.cf.taptap.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cf.taptap.domain.UserLikesDynamic;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserLikesDynamicMapper extends BaseMapper<UserLikesDynamic> {
}
