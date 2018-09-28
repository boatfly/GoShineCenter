package com.goshine.gscenter.common.isc.service;

import com.goshine.gscenter.common.isc.model.Menu;
import com.goshine.gscenter.common.isc.vo.BpmNodeVo;
import com.goshine.gscenter.common.isc.vo.TreeIsc;
import com.goshine.gscenter.common.model.Staff;
import com.goshine.gscenter.common.model.Unit;
import com.sgcc.isc.core.orm.complex.FunctionNode;
import com.sgcc.isc.core.orm.identity.User;

import java.util.List;
import java.util.Set;

public interface IUserAuthorService {
    /**
     * 获取顶级菜单
     * @param roleId 登录用户ID
     */
    public List<Menu> getAllTreeByUserId(String roleId) throws Exception;


    /**
     * 获取子菜单
     * @param userId
     * @param funcId
     * @return
     * @throws Exception
     */
    public void getSubMenu(String userId, String funcId,List<Menu> list) throws Exception;

    /**
     * 获取顶级菜单
     * @param userId 登录用户ID
     * @return
     * @throws Exception
     */
    public List<Menu> getTopMenu(String userId) throws Exception;

    /**
     * 获取子菜单
     * @param userId
     * @param funcId
     * @return
     * @throws Exception
     */
    public List<Menu> getSubMenu(String userId, String funcId) throws Exception;

    /**
     * 对url的访问权限
     * @param userId
     * @param menuUrl
     * @return
     * @throws Exception
     */
    public boolean isPermitFuncByUser(String userId, String menuUrl) throws Exception;

    /**
     * url是否在白名单中
     * @param url
     * @return
     */
    public boolean isIncludeWhiteList(String url) throws Exception;

    /**
     * 用户对URL是否有访问权限
     * @param menuUrl
     * @return
     * @throws Exception
     */
    public boolean isPermitFuncByUrl(String userId, String menuUrl) throws Exception;

    /**
     * 从ISC向本地同步用户信息
     * @return
     * @throws Exception
     */
    public List<Staff> syncUserFromIscToLocal() throws Exception;

    /**
     * 根据登录用户获取业务组织
     * @param userId
     * @return
     * @throws Exception
     */
    public List<String> getBusinessOrganizationByUserId(String userId) throws Exception;

    /**
     * 业务单位信息
     * @return
     * @throws Exception
     */
    public List<TreeIsc> getServiceOrg() throws Exception;

    /**
     * 获取用户所在的业务单位
     * @return
     * @throws Exception
     */
    public List<TreeIsc> getCurrentUserServiceOrg() throws Exception;

    /**
     * 获取用户所在的业务单位
     * @return
     * @throws Exception
     */
    public List<TreeIsc> getUserServiceOrg(String userid) throws Exception;

    /**
     * 基准单位信息
     * @param baseOrgId
     * @return
     * @throws Exception
     */
    public List<TreeIsc> getBaseOrg(String baseOrgId) throws Exception;

    /**
     * 根据userId获取资源列表
     * @param userId
     * @return
     * @throws Exception
     */
    public List<FunctionNode> getResourceByUserIdAndAppId(String userId) throws Exception;

    public List<User> getUsersByOrg() throws Exception;

    public List<Unit> syncOrg() throws Exception;

    /**
     * 根据角色获取  用户ID 集合
     */
    public Set<String> getUserIdsByRole(List<BpmNodeVo> listRole, String nodeId) throws Exception;

    /**
     *<pre>
     * 描述: 获取所有业务组织
     * @return
     * @throws Exception
     *</pre>
     */
    public List<TreeIsc> getAppAllBusinessOrg() throws Exception;

    /**
     *<pre>
     * 描述: 获取指定的组织及下属组织
     * @param orgId
     * @return
     * @throws Exception
     *</pre>
     */
    public List<String> getBusinessOrgByOrgId(String orgId) throws Exception;
}
