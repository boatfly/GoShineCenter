package com.goshine.gscenter.webbase.controller;

import com.goshine.gscenter.common.isc.model.IscUser;
import com.goshine.gscenter.common.isc.model.Menu;
import com.goshine.gscenter.common.isc.service.ILoginService;
import com.goshine.gscenter.common.isc.service.IUserAuthorService;
import com.goshine.gscenter.gskit.base.ObjectUtil;
import com.goshine.gscenter.gskit.mapper.JsonMapper;
import com.goshine.gscenter.request.Result;
import com.goshine.gscenter.webbase.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class LoginController {

    private static Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private ILoginService loginService;
    @Autowired
    private IUserAuthorService userAuthorServiceImpl;


    private final String userid = "2c9082e861f514040162222252a2007f";
    private final String appid = "2c9082e861f5140401621e9df3a70005";
    private final String username = "mjs";
    private final String passwd = "mjs123456";

    @GetMapping("/isc/getTopMenu")
    public String getTopMenu() throws Exception {
        List<Menu> menuList = userAuthorServiceImpl.getTopMenu(userid);
        return JsonMapper.INSTANCE.toJson(menuList);
    }

    @GetMapping("/isc/getAllTreeByUserId")
    public String getAllTreeByUserId() throws Exception {
        List<Menu> menuList = userAuthorServiceImpl.getAllTreeByUserId(userid);
        return JsonMapper.INSTANCE.toJson(menuList);
    }

    @RequestMapping("/")
    public String index(){
        return "index";
    }

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String login(HttpServletRequest request){
        IscUser iscUser = (IscUser) request.getSession().getAttribute(Constants.ISC_USER_SESSION_NOTATION);
        if(!ObjectUtil.isEmpty(iscUser)){
            return "index";
        }
        return "login";
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String login(String username, String password,HttpServletRequest request){
        Result result = new Result();
        try {
            IscUser iscUser=loginService.login(username,password);
            if(iscUser!=null){
                result.setSuccess(true);
                request.getSession().setAttribute(Constants.ISC_USER_SESSION_NOTATION,iscUser);
            }else{
                return "login";
            }
            result.setObj(iscUser);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //return JsonMapper.INSTANCE.toJson(result);
        return "index";
    }

    @RequestMapping(value = "/leftMenu")
    public String leftMenu(HttpServletRequest request, Model model) {
        IscUser iscUser = (IscUser) request.getSession().getAttribute(Constants.ISC_USER_SESSION_NOTATION);
        try {
            List<Menu> menuList = userAuthorServiceImpl.getTopMenu(userid);
            model.addAttribute("menuList",menuList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("iscuser",iscUser);
        return "leftMenu";
    }

    @RequestMapping(value = "/main")
    public String main() {
        return "main";
    }

    @RequestMapping(value = "/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().removeAttribute(Constants.ISC_USER_SESSION_NOTATION);
        return "login";
    }

}
