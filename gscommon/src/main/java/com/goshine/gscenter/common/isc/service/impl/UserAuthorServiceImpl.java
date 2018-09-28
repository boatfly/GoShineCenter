package com.goshine.gscenter.common.isc.service.impl;

import com.google.common.collect.Lists;
import com.goshine.gscenter.common.isc.model.Menu;
import com.goshine.gscenter.common.isc.service.IUserAuthorService;
import com.goshine.gscenter.common.isc.util.CommFunc;
import com.goshine.gscenter.common.isc.util.FunctionNodeUtil;
import com.goshine.gscenter.common.isc.util.SessionShare;
import com.goshine.gscenter.common.isc.util.SynchroISC;
import com.goshine.gscenter.common.isc.vo.BpmActorVo;
import com.goshine.gscenter.common.isc.vo.BpmNodeVo;
import com.goshine.gscenter.common.isc.vo.BusinessOrg;
import com.goshine.gscenter.common.isc.vo.TreeIsc;
import com.goshine.gscenter.common.model.Staff;
import com.goshine.gscenter.common.model.Unit;
import com.goshine.gscenter.gskit.base.ObjectUtil;
import com.goshine.gscenter.gskit.base.PropertiesUtil;
import com.goshine.gscenter.gskit.mapper.BeanMapper;
import com.sgcc.isc.core.orm.complex.BusiOrgTree;
import com.sgcc.isc.core.orm.complex.FunctionNode;
import com.sgcc.isc.core.orm.complex.FunctionTree;
import com.sgcc.isc.core.orm.identity.Department;
import com.sgcc.isc.core.orm.identity.User;
import com.sgcc.isc.core.orm.organization.BusinessOrganization;
import com.sgcc.isc.core.orm.resource.Function;
import com.sgcc.isc.framework.common.entity.Paging;
import com.sgcc.isc.service.adapter.factory.AdapterFactory;
import com.sgcc.isc.service.adapter.helper.IIdentityService;
import com.sgcc.isc.service.adapter.helper.IOrganizationService;
import com.sgcc.isc.service.adapter.helper.IResourceService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserAuthorServiceImpl implements IUserAuthorService {
    private static final Logger log=Logger.getLogger(LoginServiceImpl.class);

    private static Map<String,String> env=null;

    @Autowired
    private HttpServletRequest request;

    static{
        //初始化配置文件信息
        env = PropertiesUtil.getPropertiesMap();
    }

    private static final String FUNC_CATEGORY="001";

    @Override
    public List<Menu> getAllTreeByUserId(String userId) throws Exception {
        List<Menu> list=null;

        //应用ID
        String appId=env.get("app.id");

        IResourceService service= AdapterFactory.getResourceService();
        FunctionTree funcTree=service.getFuncTree(userId, appId, FUNC_CATEGORY);
        if(funcTree!=null){
            list=new ArrayList<Menu>();
            for(FunctionNode funcNode :funcTree.getFuncNode()){
                Menu menu=new Menu();
                menu.setMenuId(funcNode.getCurrentNode().getId());
                menu.setMenuCode(funcNode.getCurrentNode().getBusiCode());
                menu.setMenuName(funcNode.getCurrentNode().getName());
                menu.setMenuUrl(FunctionNodeUtil.getFuncUrlByNode(funcNode));
                menu.setIconCls(funcNode.getCurrentNode().getFuncOtherInfo());
                list.add(menu);

                getSubMenu(userId,funcNode.getCurrentNode().getId(),list);
            }
        }

        return list;
    }

    /**
     * 获取顶级菜单
     * @param userId 登录用户ID
     * @return
     * @throws Exception
     */
    @Override
    public List<Menu> getTopMenu(String userId) throws Exception{
        List<Menu> list=null;

        //应用ID
        String appId=env.get("app.id");

        IResourceService service=AdapterFactory.getResourceService();
        FunctionTree funcTree=service.getFuncTree(userId, appId, FUNC_CATEGORY);
        if(funcTree!=null){
            list=new ArrayList<Menu>();
            for(FunctionNode funcNode :funcTree.getFuncNode()){
                Menu menu=new Menu();
                menu.setMenuId(funcNode.getCurrentNode().getId());
                menu.setMenuCode(funcNode.getCurrentNode().getBusiCode());
                menu.setMenuName(funcNode.getCurrentNode().getName());
                menu.setMenuUrl(FunctionNodeUtil.getFuncUrlByNode(funcNode));
                list.add(menu);
            }
        }

        return list;
    }

    /**
     * 获取子菜单
     * @param userId
     * @param funcId
     * @return
     * @throws Exception
     */
    @Override
    public List<Menu> getSubMenu(String userId,String funcId) throws Exception{
        List<Menu> list=null;

        IResourceService service=AdapterFactory.getResourceService();
        FunctionNode funcNode=service.getFuncTreeByFuncId(userId,funcId,FUNC_CATEGORY);
        if(funcNode!=null){
            list=new ArrayList<Menu>();
            List<FunctionNode> subFuncNodeList=funcNode.getNextNode();
            if(subFuncNodeList!=null && subFuncNodeList.size()>0){
                for(FunctionNode subFuncNode : subFuncNodeList){
                    Menu menu=new Menu();
                    menu.setMenuId(subFuncNode.getCurrentNode().getId());
                    menu.setMenuCode(subFuncNode.getCurrentNode().getBusiCode());
                    menu.setMenuName(subFuncNode.getCurrentNode().getName());
                    menu.setMenuUrl(FunctionNodeUtil.getFuncUrlByNode(subFuncNode));
                    list.add(menu);

                    //子菜单
                    getFuncSubMenu(userId,subFuncNode.getCurrentNode().getId(),list,service);
                }
            }
        }
        return list;
    }

    /**
     * 获取子菜单
     * @param userId
     * @param funcId
     * @return
     * @throws Exception
     */
    @Override
    public void getSubMenu(String userId,String funcId,List<Menu> list) throws Exception{
        //List<Menu> list=null;

        IResourceService service=AdapterFactory.getResourceService();
        FunctionNode funcNode=service.getFuncTreeByFuncId(userId,funcId,FUNC_CATEGORY);
        if(funcNode!=null){
            //list=new ArrayList<Menu>();
            List<FunctionNode> subFuncNodeList=funcNode.getNextNode();
            if(subFuncNodeList!=null && subFuncNodeList.size()>0){
                for(FunctionNode subFuncNode : subFuncNodeList){
                    Menu menu=new Menu();
                    menu.setMenuId(subFuncNode.getCurrentNode().getId());
                    menu.setMenuCode(subFuncNode.getCurrentNode().getBusiCode());
                    menu.setMenuName(subFuncNode.getCurrentNode().getName());
                    menu.setMenuUrl(FunctionNodeUtil.getFuncUrlByNode(subFuncNode));
                    menu.setMenuParentId(funcId);
                    menu.setIconCls(subFuncNode.getCurrentNode().getFuncOtherInfo());
                    list.add(menu);

                    //子菜单
                    getFuncSubMenu(userId,subFuncNode.getCurrentNode().getId(),list,service);
                }
            }
        }
    }

    /**
     * 递归获取子菜单
     * @param userId
     * @param funcId
     * @param list
     * @param service
     * @throws Exception
     */
    private void getFuncSubMenu(String userId,String funcId,List<Menu> list,IResourceService service) throws Exception{
        FunctionNode funcNode=service.getFuncTreeByFuncId(userId,funcId,FUNC_CATEGORY);
        if(funcNode!=null){
            List<FunctionNode> subFuncNodeList=funcNode.getNextNode();
            if(subFuncNodeList!=null && subFuncNodeList.size()>0){
                for(FunctionNode subFuncNode : subFuncNodeList){
                    Menu menu=new Menu();
                    menu.setMenuId(subFuncNode.getCurrentNode().getId());
                    menu.setMenuCode(subFuncNode.getCurrentNode().getBusiCode());
                    menu.setMenuName(subFuncNode.getCurrentNode().getName());
                    menu.setMenuUrl(FunctionNodeUtil.getFuncUrlByNode(subFuncNode));
                    menu.setMenuParentId(funcId);
                    list.add(menu);

                    //子菜单
                    getFuncSubMenu(userId,subFuncNode.getCurrentNode().getId(),list,service);
                }
            }
        }
    }

    /**
     * 对url的访问权限,通过遍历判断，过时
     * function.getUrl()是否能拿到URL待定
     * @param userId
     * @param menuUrl
     * @return
     * @throws Exception
     */
    @Override
    public boolean isPermitFuncByUser(String userId,String menuUrl) throws Exception{
        boolean result=false;

        //应用ID
        String appId=env.get("app.id");

        //获取此用户所有的有权限访问的功能集合
        IResourceService service=AdapterFactory.getResourceService();
        List<Function> funcList=service.getUserPermitFuncs(userId, appId, FUNC_CATEGORY, null);
        if(funcList!=null && funcList.size()>0){
            //遍历功能集合
            for(Function function : funcList){
                if(function.getUrl().equals(menuUrl)){
                    result=true;
                    break;
                }
            }
        }

        return result;
    }


    /**
     * url是否在白名单中
     * @param url
     * @return
     * @throws
     */
    @Override
    public boolean isIncludeWhiteList(String url) throws Exception{
        boolean result=false;
        String whiteList=null;
        //白名单
        String whiteListStr=env.get("whitelist");
        if(whiteListStr!=null && !whiteListStr.equals("")){
            String[] whiteListArray=whiteListStr.split(";");
            for(int i=0;i<whiteListArray.length;i++){
                whiteList=whiteListArray[i].toLowerCase();
                Pattern pattern = Pattern.compile(whiteList);
                Matcher matcher=pattern.matcher(url.toLowerCase());
                if(matcher.matches()){
                    result=true;
                    break;
                }
            }
        }

        return result;
    }

    /**
     * 用户对URL是否有访问权限
     * @param menuUrl
     * @return
     * @throws Exception
     */
    @Override
    public boolean isPermitFuncByUrl(String userId,String menuUrl) throws Exception{
        boolean result=false;

        //应用ID
        String appId=env.get("app.id");

        //获取此用户所有的有权限访问的功能集合
        IResourceService service=AdapterFactory.getResourceService();
        String pageUrl= CommFunc.getPageUrl(menuUrl);
        result=service.hasPermitURLObj(userId, appId, pageUrl);

        return result;
    }

    /**
     * 从ISC向本地同步用户信息
     * @return
     * @throws Exception
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public List<Staff> syncUserFromIscToLocal() throws Exception{
        List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
        boolean done=false;

        //应用ID
        String appId=env.get("app.id");

        IIdentityService service=AdapterFactory.getIdentityService();
        //分页索引值
        int i=1;
        while(!done){
            //分页获取用户
            Paging page=service.getUsers(appId,null,null,500,i,false);
            if(page!=null){
                List<Map<String,Object>> userList=(List<Map<String,Object>>)page.getData();
                list.addAll(userList);
                if(userList.size()<=500){
                    done=true;
                }else{
                    i++;//下一页
                }
            }
        }
        if(null!=list)
            return SynchroISC.getStaff(list);
        else
            return null;
    }

    /**
     * 根据登录用户获取业务组织
     * @param userId
     * @return
     * @throws Exception
     */
    public List<BusinessOrganization> getBusinessOrganizationByUser(String userId, String orgId) throws Exception{
        List<BusinessOrganization> list=new ArrayList<BusinessOrganization>();

        //应用ID
        String appId=env.get("app.id");
        IIdentityService service=AdapterFactory.getIdentityService();
        IOrganizationService orgService=AdapterFactory.getOrganizationService();
        if(true){
            if(orgId!=null && !orgId.equals("")){
                list = orgService.getChildBusiOrgsById("2c9082e861f5140401621eabf22c0039",null);
                list=orgService.getBusiOrgByUserId(userId, appId);
                //查询子集
                list=orgService.getChildBusiOrgsById("2c9082e861f5140401622220a322007c", null);
            }else{
                //查询第一级
                //list=service.getUserOrgPathByUserId(appId,userId);
                list=orgService.getBusiOrgByUserId(userId, appId);
            }
        }
        return list;
    }

    /**
     * 所有子业务组织集合
     * @return
     * @throws Exception
     */
    @Override
    public List<TreeIsc> getServiceOrg() throws Exception {
        List<TreeIsc> trees = Lists.newArrayList();
        //应用ID
        String appId=env.get("app.id");
        IOrganizationService orgService=AdapterFactory.getOrganizationService();
        //BusiOrgNode busiOrgNode = orgService.getAllBusiOrgsById("2c9082e861f5140401621eabf22c0039");
        BusiOrgTree SS=orgService.getMatchedOrgsByOrgName(appId,"浙江省电力公司");
        /**
         *   name = "获取业务系统业务组织机构树的第一层组织单元集合",
         * params = {"业务系统ID", "业务组织信息参数", "业务组织排序字段 "},
         */
        List<BusinessOrganization>  oneList = orgService.getChildOrgsBySystemId(appId,null,null);
        for(BusinessOrganization org : oneList){
            TreeIsc treeOne = new TreeIsc();
            treeOne.setId(org.getId());
            treeOne.setText(org.getName());
            treeOne.setIconCls("icon-company");
            this.getSubOrg(org.getId(), treeOne);
            trees.add(treeOne);
        }
        return trees;
    }
    /**
     *  name = "根据业务组织单元标识获取下一层业务组织单元集合",
     *         params = {"业务组织单元ID", "业务组织信息参数 "},
     */
    public void getSubOrg(String orgId,TreeIsc treeP) throws Exception {
        IOrganizationService orgService = AdapterFactory.getOrganizationService();
        List<BusinessOrganization> subList = orgService.getChildBusiOrgsById(orgId, null);
        if(null!=subList && subList.size()>0) {
            List<TreeIsc> tree = Lists.newArrayList();
            for (BusinessOrganization org : subList) {
                TreeIsc nodeTree = new TreeIsc();
                nodeTree.setId(org.getId());
                nodeTree.setText(org.getName());
                nodeTree.setIconCls("icon-folder");
                getSubOrg(org.getId(), nodeTree);
                tree.add(nodeTree);
            }
            treeP.setChildren(tree);
        }
    }

    /**
     *   name = "根据基准组织ID、基准组织属性获取下一级基准组织信息",
     *         params = {"基准组织ID", "基准组织单元的性质类别"},
     * @return
     * @throws Exception
     */
    @Override
    public List<TreeIsc> getBaseOrg(String baseOrgId) throws Exception {
        List<TreeIsc> trees = Lists.newArrayList();
        IIdentityService service=AdapterFactory.getIdentityService();
        Department org = service.getDepartmentById(baseOrgId);

        TreeIsc treeOne = new TreeIsc();

        treeOne.setId(org.getId());
        treeOne.setText(org.getName());
        treeOne.setIconCls("icon-company");

        this.getBaseSubOrg(org.getId(), treeOne);
        trees.add(treeOne);
        return trees;
    }

    public void getBaseSubOrg(String baseOrgId,TreeIsc treeP) throws Exception {
        IIdentityService service=AdapterFactory.getIdentityService();
        List<Department> subList = service.getSubDepartment(baseOrgId,null);
        if(null!=subList && subList.size()>0) {
            List<TreeIsc> tree = Lists.newArrayList();
            for (Department org : subList) {
                TreeIsc nodeTree = new TreeIsc();
                nodeTree.setId(org.getId());
                nodeTree.setText(org.getName());
                nodeTree.setIconCls("icon-folder");
                getBaseSubOrg(org.getId(), nodeTree);
                tree.add(nodeTree);
            }
            treeP.setChildren(tree);
        }
    }

    @Override
    public List<FunctionNode> getResourceByUserIdAndAppId(String userId) throws Exception {
        String appId=env.get("app.id");
        FunctionTree ss= AdapterFactory.getResourceService().getFuncTree(userId,appId, null);
        if(null!=ss)
            return ss.getFuncNode();
        else
            return null;
    }

    @Override
    public List<String> getBusinessOrganizationByUserId(String userId) throws Exception {
        List<String> list=new ArrayList<String>();
        //应用ID
        String appId=env.get("app.id");
        IOrganizationService orgService=AdapterFactory.getOrganizationService();
        if(userId!=null && !userId.equals("")) {
            List<BusinessOrganization> listOrg = orgService.getBusiOrgByUserId(userId, appId);
            for(BusinessOrganization org : listOrg){
                list.add(org.getId());
            }
        }
        return list;
    }

    /**
     * 获取用户所在的业务单位
     * @return
     * @throws Exception
     */
    @Override
    public List<TreeIsc> getCurrentUserServiceOrg() throws Exception {
        List<TreeIsc> treeIscs = SessionShare.getBaseOrg(request,"serviceOrgList");
        com.goshine.gscenter.common.model.User user=SessionShare.getUser(request);
        List<String> list=this.getBusinessOrganizationByUserId(user.getId());
        List<TreeIsc> treeIscList=new ArrayList<>();
        for (String oid : list) {
            for (int i=0;i<treeIscs.size();i++) {
                TreeIsc treeIsc=new TreeIsc();
                //PropertyUtils.copyProperties(treeIsc, treeIscs.get(i));
                treeIsc = BeanMapper.map(treeIscs.get(i),TreeIsc.class);
                treeIscList=findChildrens(treeIsc,treeIscs,oid,treeIscList);
            }
        }
        return treeIscList;
    }
    /**
     * 获取用户所在的业务单位
     * @return
     * @throws Exception
     */
    @Override
    public List<TreeIsc> getUserServiceOrg(String userid) throws Exception {
        List<TreeIsc> treeIscs = SessionShare.getBaseOrg(request,"serviceOrgList");
        List<String> list=this.getBusinessOrganizationByUserId(userid);
        List<TreeIsc> treeIscList=new ArrayList<>();
        for (String oid : list) {
            for (int i=0;i<treeIscs.size();i++) {
                TreeIsc treeIsc=new TreeIsc();
                //PropertyUtils.copyProperties(treeIsc, treeIscs.get(i));
                treeIsc = BeanMapper.map(treeIscs.get(i),TreeIsc.class);
                treeIscList=findChildrens(treeIsc,treeIscs,oid,treeIscList);
            }
        }
        return treeIscList;
    }

    /**
     * 递归查找子节点
     * @param
     * @return
     */
    public static List<TreeIsc> findChildrens(TreeIsc treeIsc,List<TreeIsc> treeIscs,String oid,List<TreeIsc>  treeIscList) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if(treeIsc.getId().equals(oid)) {
            for (int i=0;i<treeIscs.size();i++) {
                TreeIsc treeIsc2=new TreeIsc();
                //PropertyUtils.copyProperties(treeIsc2, treeIscs.get(i));
                treeIsc2 = BeanMapper.map(treeIscs.get(i),TreeIsc.class);
                if(treeIsc2.getId().equals(oid))
                    treeIscList.add(treeIsc2);
            }
        }else {
            List<TreeIsc> treeIscs2=treeIsc.getChildren();
            if(treeIscs2!=null) {
                for (int i=0;i<treeIscs2.size();i++) {
                    TreeIsc treeIsc2=new TreeIsc();
                    //PropertyUtils.copyProperties(treeIsc2, treeIscs2.get(i));
                    treeIsc2 = BeanMapper.map(treeIscs.get(i),TreeIsc.class);
                    findChildrens(treeIsc2,treeIscs2,oid,treeIscList);
                }

            }
        }
        return treeIscList;
    }

    /**
     * 根据  业务组织获取 用户列表
     * @return
     * @throws Exception
     */
    @Override
    public List<User> getUsersByOrg() throws Exception {
        //应用ID
        com.goshine.gscenter.common.model.User user= SessionShare.getUser(request);
        List<String> lists=this.getBusinessOrganizationByUserId(user.getId());

        String appId=env.get("app.id");
        IIdentityService service=AdapterFactory.getIdentityService();
        List<User> list = service.getUsersByOrg(appId,lists.get(0),null,null);
        return list;
    }




    /**
     * 同步所有子业务组织集合
     * @return
     * @throws Exception
     */
    @Override
    public List<Unit> syncOrg() throws Exception {
        List<Unit> trees = Lists.newArrayList();
        //应用ID
        String appId=env.get("app.id");
        IOrganizationService orgService=AdapterFactory.getOrganizationService();
        //BusiOrgNode busiOrgNode = orgService.getAllBusiOrgsById("2c9082e861f5140401621eabf22c0039");
        BusiOrgTree SS=orgService.getMatchedOrgsByOrgName(appId,"浙江省电力公司");
        /**
         *   name = "获取业务系统业务组织机构树的第一层组织单元集合",
         * params = {"业务系统ID", "业务组织信息参数", "业务组织排序字段 "},
         */
        List<BusinessOrganization>  oneList = orgService.getChildOrgsBySystemId(appId,null,null);
        for(BusinessOrganization org : oneList){
            Unit treeOne = new Unit();
            treeOne.setOrgId(org.getId());
            treeOne.setOrgNo(org.getId());
            treeOne.setOrgName(org.getName());
            //treeOne.setOrgTypeCode(org.getOrgTypeId());
            treeOne.setpOrgId("");
            this.setrgType(treeOne,org.getNatureId());
            trees.add(treeOne);
            this.syncSubOrg(org.getId(), trees);
        }
        return trees;
    }

    /**
     * #业务单位  01
     * org.id=2c9082e861f5140401621e99de640002
     * #业务部门  02
     * dept.id=2c9082e861f5140401621e99de730003
     * #班组   03
     * group.id=2c9082e86554fc8101656b26c8cb02c4
     * #工区    04
     * job.id=2c9082e86554fc8101656b2e665f02c5
     * @param u
     * @param natureId
     */
    private void setrgType(Unit u,String natureId){
        String orgId=env.get("org.id");
        String deptId=env.get("dept.id");
        String groupId=env.get("group.id");
        String jobId=env.get("job.id");
        if(orgId.equals(natureId)){
            u.setOrgTypeCode("01");
        }else if(deptId.equals(natureId)){
            u.setOrgTypeCode("02");
        }else if(groupId.equals(natureId)){
            u.setOrgTypeCode("03");
        }else if(jobId.equals(natureId)){
            u.setOrgTypeCode("04");
        }
    }

    public void syncSubOrg(String orgId,List<Unit> trees) throws Exception {
        IOrganizationService orgService = AdapterFactory.getOrganizationService();
        List<BusinessOrganization> subList = orgService.getChildBusiOrgsById(orgId, null);
        if(null!=subList && subList.size()>0) {
            for (BusinessOrganization org : subList) {
                Unit treeOne = new Unit();
                treeOne.setOrgId(org.getId());
                treeOne.setOrgNo(org.getId());
                treeOne.setOrgName(org.getName());
                //treeOne.setOrgTypeCode(org.getOrgTypeId());
                treeOne.setpOrgId(orgId);
                this.setrgType(treeOne,org.getNatureId());
                trees.add(treeOne);
                syncSubOrg(org.getId(), trees);
            }
        }
    }

    /**
     * 根据角色获取  用户ID 集合
     * @return
     * @throws Exception
     */
    @Override
    public Set<String> getUserIdsByRole(List<BpmNodeVo> listRole, String nodeId) throws Exception {
        IIdentityService service1=AdapterFactory.getIdentityService();
        Set<String> set=new HashSet();
        for(BpmNodeVo node:listRole){
            if(nodeId.equals(node.getNodeId())){
                for(BpmActorVo role:node.getBpmActorVoList()){
                    //01 ：角色
                    if(role.getType().equals("01")) {//角色
                        List<User> list = service1.getUsersByOrgRole(role.getId(), null, null);
                        for (User u : list) {
                            set.add(u.getId());
                        }
                    }if(role.getType().equals("03")) {
                        set.add(role.getId());//用户
                    }
                }
            }
        }
        return set;
    }

    /**
     *<pre>
     * 描述: 获取所有业务组织
     * @return
     * @throws Exception
     *</pre>
     */
    @Override
    public List<TreeIsc> getAppAllBusinessOrg() throws Exception{
        List<TreeIsc> treeIscs = SessionShare.getBaseOrg(request,"serviceOrgList");
        return treeIscs;
    }

    /**
     *<pre>
     * 描述: 获取指定的组织及下属组织
     * @param orgId
     * @return
     * @throws Exception
     *</pre>
     */
    @Override
    public List<String> getBusinessOrgByOrgId(String orgId) throws Exception{
        List<String> orgIdList=new ArrayList<String>();

        //把树结构转为list
        List<TreeIsc> treeIscs = SessionShare.getBaseOrg(request,"serviceOrgList");
        List<BusinessOrg> orgList=getBusinessOrgList(treeIscs);
        //遍历组织树，获取和传入参数一致的组织
        for(BusinessOrg businessOrg : orgList){
            if(!ObjectUtil.isEmpty(orgId)){
                if(businessOrg.getOrgId().equals(orgId)){
                    getBusinessOrgByOrgId(orgIdList,orgId,orgList);
                    break;
                }
            }else{
                orgIdList.add(businessOrg.getOrgId());
            }
        }

        return orgIdList;
    }

    /**
     *<pre>
     * 描述: 把树结构的业务组织转换为LIST结构
     * @param treeIscList
     * @return
     *</pre>
     */
    private List<BusinessOrg> getBusinessOrgList(List<TreeIsc> treeIscList){
        List<BusinessOrg> list=new ArrayList<BusinessOrg>();

        try{
            if(treeIscList!=null && treeIscList.size()>0){
                JSONArray orgJsonArray=JSONArray.fromObject(treeIscList);
                for(int i=0;i<orgJsonArray.size();i++){
                    JSONObject orgJson=orgJsonArray.getJSONObject(i);
                    BusinessOrg businessOrg=new BusinessOrg();
                    businessOrg.setOrgId(orgJson.getString("id"));
                    businessOrg.setOrgName(orgJson.getString("text"));
                    businessOrg.setParentOrgId(orgJson.getString("pid"));
                    list.add(businessOrg);
                    getBusinessOrgList(list,orgJson.getJSONArray("children"),orgJson.getString("id"));
                }
            }
        }catch(Exception ex){
            log.error(ex.getMessage());
        }

        return list;
    }

    /**
     *<pre>
     * 描述: 把树结构的业务组织转换为LIST结构的递归调用
     * @param list
     * @param childrenList
     *</pre>
     */
    private void getBusinessOrgList(List<BusinessOrg> list, JSONArray childrenList, String pid){

        for(int i=0;i<childrenList.size();i++){
            JSONObject orgJson=childrenList.getJSONObject(i);
            BusinessOrg businessOrg=new BusinessOrg();
            businessOrg.setOrgId(orgJson.getString("id"));
            businessOrg.setOrgName(orgJson.getString("text"));
            businessOrg.setParentOrgId(pid);
            list.add(businessOrg);
            getBusinessOrgList(list,orgJson.getJSONArray("children"),orgJson.getString("id"));
        }
    }

    private void getBusinessOrgByOrgId(List<String> list,String orgId,List<BusinessOrg> orgList){
        //首先找到匹配的组织
        int index=-1;
        for(int i=0;i<orgList.size();i++){
            if(orgList.get(i).getOrgId().equals(orgId)){
                list.add(orgId);
                index=i;
                break;
            }
        }

        if(index>=0){
            getgetBusinessOrgByParentOrg(list,orgId,orgList);
        }
    }

    private void getgetBusinessOrgByParentOrg(List<String> list,String orgId,List<BusinessOrg> orgList){
        for(int i=0;i<orgList.size();i++){
            if(orgList.get(i).getParentOrgId().equals(orgId)){
                list.add(orgList.get(i).getOrgId());
                getgetBusinessOrgByParentOrg(list,orgList.get(i).getOrgId(),orgList);
            }
        }
    }
}
