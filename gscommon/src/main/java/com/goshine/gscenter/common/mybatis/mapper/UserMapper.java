package com.goshine.gscenter.common.mybatis.mapper;

import com.goshine.gscenter.common.model.User;
import com.goshine.gscenter.common.mybatis.sqlprovider.UserSQLProvider;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

public interface UserMapper {
    @Select("select * from user")
    List<User> getAll();

    @Select("select count(id) from user")
    int getTotalUserCount();

    @SelectProvider(type = UserSQLProvider.class,method = "getUserByConditional")
    List<User> getUserByConditional(User user);

    @SelectProvider(type = UserSQLProvider.class,method = "getTotalConditionalUserCount")
    int getTotalConditionalUserCount(User user);


}
