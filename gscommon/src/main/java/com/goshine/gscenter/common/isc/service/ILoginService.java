package com.goshine.gscenter.common.isc.service;

import com.goshine.gscenter.common.isc.model.IscUser;

public interface ILoginService {
    /**
     * 登陆
     * @param loginName
     * @param loginPwd
     * @return
     * @throws Exception
     */
    public IscUser login(String loginName, String  loginPwd) throws Exception;

}
