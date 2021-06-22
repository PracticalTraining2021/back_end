package com.cf.taptap.vo;

import com.cf.taptap.domain.Dynamic;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface Convert {

     Convert INSTANCE= Mappers.getMapper(Convert.class);

     Dynamic convert(DynamicVO dynamicVO);

}
