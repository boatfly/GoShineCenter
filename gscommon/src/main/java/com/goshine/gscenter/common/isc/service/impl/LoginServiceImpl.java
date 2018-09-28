package com.goshine.gscenter.common.isc.service.impl;

import com.goshine.gscenter.common.isc.model.IscUser;
import com.goshine.gscenter.common.isc.service.ILoginService;
import com.goshine.gscenter.gskit.mapper.BeanMapper;
import com.sgcc.isc.core.orm.identity.User;
import com.sgcc.isc.service.adapter.factory.AdapterFactory;
import com.sgcc.isc.service.adapter.helper.IIdentityService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements ILoginService {
    private static final Logger log= Logger.getLogger(LoginServiceImpl.class);

    /**
     * 登陆
     * @param loginName
     * @param loginPwd
     * @return
     * @throws Exception
     */
    @Override
    public IscUser login(String loginName, String  loginPwd) throws Exception{
        IscUser iscUser=new IscUser();
        try{
            IIdentityService service= AdapterFactory.getIdentityService();
            User user= service.userLoginAuth(loginName, loginPwd);
            //PropertyUtils.copyProperties(iscUser,user);
            iscUser = BeanMapper.map(user,IscUser.class);
            return iscUser;
        }catch(Exception ex){
            log.error("通过用户名和密码登录ISC出错，错误信息：" + ex.getMessage());
        }
        return null;
    }

    public static void main(String[] args) {
        IIdentityService service=AdapterFactory.getIdentityService();
        //service.getUsersByRoleGroupId();
    }



}
