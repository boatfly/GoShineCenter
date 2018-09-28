package com.goshine.gscenter.common.mybatis.service;

import com.github.pagehelper.PageHelper;
import com.goshine.gscenter.common.model.User;
import com.goshine.gscenter.common.mybatis.mapper.UserMapper;
import com.goshine.gscenter.common.mybatis.pagehelper.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public PageBean<User> getPageUsers(int currentPage, int pageSize){
        PageHelper.startPage(currentPage, pageSize);
        List<User> users = userMapper.getAll();
        int totalcount = userMapper.getTotalUserCount();
        PageBean<User> pageData = new PageBean<>(currentPage, pageSize, totalcount);
        pageData.setItems(users);
        return pageData;
    }

    public PageBean<User> getPageUsersByConditional(User user,int currentPage, int pageSize){
        PageHelper.startPage(currentPage, pageSize);
        List<User> users = userMapper.getUserByConditional(user);
        int totalcount = userMapper.getTotalConditionalUserCount(user);
        PageBean<User> pageData = new PageBean<>(currentPage, pageSize, totalcount);
        pageData.setItems(users);
        return pageData;
    }
}
