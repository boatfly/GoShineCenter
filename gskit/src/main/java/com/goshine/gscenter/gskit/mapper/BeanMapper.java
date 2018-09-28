package com.goshine.gscenter.gskit.mapper;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;


/**
 * 在类复制场景中，从性能考虑上Apache Common BeanUtils远远小于Dozer远远小于orika
 * 在应用orika，可能有场景两个类属性名称不用，可以自定义映射。
 *
 */
public class BeanMapper {
    private static MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();

    /**
     * 简单的复制出新类型对象.
     */
    public static <S, D> D map(S source, Class<D> destinationClass) {
        return mapperFactory.getMapperFacade().map(source, destinationClass);
    }
}
