package com.goshine.gscenter.webbase.controller;

import com.goshine.gscenter.common.model.User;
import com.goshine.gscenter.common.mybatis.mapper.UserMapper;
import com.goshine.gscenter.common.mybatis.pagehelper.PageBean;
import com.goshine.gscenter.common.mybatis.service.UserService;
import com.goshine.gscenter.gskit.mapper.JsonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    private static Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserService userService;

    @GetMapping("/isc/allusers")
    public String getAllUsers(){
        List<User> users = userMapper.getAll();
        return JsonMapper.INSTANCE.toJson(users);
    }
    @GetMapping("/isc/allusers/{page}")
    public String getPageUsers(@PathVariable int page){
        PageBean<User> pageUsers = userService.getPageUsers(page,2);
        return JsonMapper.INSTANCE.toJson(pageUsers);
    }
    @GetMapping("/isc/findusers/")
    public String getPageUsersByConditional(int page,String empNo,String loginname){
        User user = new User();
        user.setLoginname(loginname);
        user.setEmpNo(empNo);
        PageBean<User> pageUsers = userService.getPageUsersByConditional(user,page,2);
        return JsonMapper.INSTANCE.toJson(pageUsers);
    }
}