package demo.vo;

import demo.domain.Dynamic;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface Convert {

    Convert INSTANCE = Mappers.getMapper(Convert.class);

    Dynamic convert(DynamicVO dynamicVO);

}
