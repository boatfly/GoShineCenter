package com.goshine.gscenter.common.mybatis.sqlprovider;

import com.goshine.gscenter.common.model.User;
import org.apache.ibatis.jdbc.SQL;

public class UserSQLProvider {
    public String getTotalConditionalUserCount(final User user){
        return new SQL(){{
            SELECT("count(id)");
            FROM("user");
            if(user.getLoginname()!=null){
                WHERE("loginname like \"%\"#{loginname}\"%\"");
            }
            if(user.getEmpNo()!=null){
                WHERE("empNo like \"%\"#{empNo}\"%\"");
            }
        }}.toString();
    }

    public String getUserByConditional(final User user){
        return new SQL(){{
            SELECT("*");
            FROM("user");
            if(user.getLoginname()!=null){
                WHERE("loginname like \"%\"#{loginname}\"%\"");
            }
            if(user.getEmpNo()!=null){
                WHERE("empNo like \"%\"#{empNo}\"%\"");
            }
        }}.toString();
    }
}
