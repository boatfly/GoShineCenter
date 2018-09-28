package com.goshine.gscenter.common.isc.util;

import com.goshine.gscenter.common.isc.vo.TreeIsc;
import com.goshine.gscenter.common.model.User;
import net.sf.json.JSONObject;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class SessionShare {
    public static User getUser(HttpServletRequest request){
        //ServletContext ContextA =request.getSession().getServletContext().getContext("/ptm");// 这里面传递的是 WebappA的虚拟路径
        //HttpSession sessionA =(HttpSession)ContextA.getAttribute("session");
        HttpSession sessionA =(HttpSession)request.getSession().getAttribute("session");
        if(null==sessionA) return null;
        String uStr = sessionA.getAttribute("user-share").toString();
        System.out.println("*******************:session get data -->："+uStr);
        JSONObject obj = JSONObject.fromObject(uStr);
        User user=(User)JSONObject.toBean(obj, User.class);
        return user;
    }

    public static List<TreeIsc> getBaseOrg(HttpServletRequest request, String flag){
        //ServletContext ContextA =request.getSession().getServletContext().getContext("/ptm");// 这里面传递的是 WebappA的虚拟路径
        //HttpSession sessionA =(HttpSession)ContextA.getAttribute("session");
        HttpSession sessionA =(HttpSession)request.getSession().getAttribute("session");
        if(null==sessionA) return null;
        List<TreeIsc> trees = (List<TreeIsc>)sessionA.getAttribute(flag);
        System.out.println("*******************:session get org -->："+trees.size());

        return trees;
    }
}
