package com.goshine.gscenter.webbase.controller;

import com.goshine.gscenter.common.hbase.HBasePageBean;
import com.goshine.gscenter.common.hbase.instance.entity.HMessage;
import com.goshine.gscenter.common.hbase.instance.service.HMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class HbaseController {
    @Autowired
    private HMessageService hMessageService;

    @GetMapping("/hbase/hmessages/{rowkey}")
    public String viewHmessage(@PathVariable String rowkey,Model model){
        List<HMessage> hMessages = hMessageService.getById(new HMessage(),rowkey);
        model.addAttribute("hMessage",hMessages.get(0));
        return "hMessageInfo";
    }

    @GetMapping("/hbase/hmessages")
    public String allHmessages(HttpServletRequest request,Integer page,Integer pagesize,String startRowKey,Model model) throws Exception {

        if(page==null||page.intValue()==0){
            page=1;
            request.getSession().removeAttribute("hBasePageBean");
        }
        if(pagesize==null||pagesize.intValue()==0){
            pagesize=2;
        }
        HBasePageBean<HMessage> hBasePageBean = (HBasePageBean)request.getSession().getAttribute("hBasePageBean");
        if(hBasePageBean==null){
            hBasePageBean = new HBasePageBean<HMessage>();
            hBasePageBean.setNextPageRow(startRowKey);
            hBasePageBean.setPageSize(pagesize);
        }else{
            hBasePageBean.setPageSize(pagesize);
            hBasePageBean.setCurrPage(page+1);
            hBasePageBean.setCurPageRow(startRowKey);
        }
        hBasePageBean = hMessageService.queryScanGreater(hBasePageBean,new HMessage(),page,pagesize,startRowKey,null);
        request.getSession().setAttribute("hBasePageBean",hBasePageBean);
        model.addAttribute("hBasePageBean",hBasePageBean);
        return "hMessageList";
    }
}
