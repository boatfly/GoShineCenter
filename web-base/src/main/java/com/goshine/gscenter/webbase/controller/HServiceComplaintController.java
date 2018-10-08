package com.goshine.gscenter.webbase.controller;

import com.goshine.gscenter.common.hbase.HBasePageBean;
import com.goshine.gscenter.common.hbase.instance.entity.HMessage;
import com.goshine.gscenter.common.hbase.instance.entity.HServiceComplaint;
import com.goshine.gscenter.common.hbase.instance.service.HServiceComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class HServiceComplaintController {
    @Autowired
    private HServiceComplaintService hServiceComplaintService;

    @GetMapping("/hbase/hServiceComplaints/{rowkey}")
    public String viewHmessage(@PathVariable String rowkey, Model model){
        List<HServiceComplaint> hMessages = hServiceComplaintService.getById(new HServiceComplaint(),rowkey);
        model.addAttribute("hMessage",hMessages.get(0));
        return "hServiceComplaintInfo";
    }

    @GetMapping("/hbase/hServiceComplaints")
    public String allHmessages(HttpServletRequest request, Integer page, Integer pagesize, String startRowKey, Model model) throws Exception {

        if(page==null||page.intValue()==0){
            page=1;
            request.getSession().removeAttribute("hBasePageBean");
        }
        if(pagesize==null||pagesize.intValue()==0){
            pagesize=2;
        }
        HBasePageBean<HServiceComplaint> hBasePageBean = (HBasePageBean)request.getSession().getAttribute("hBasePageBean");
        if(hBasePageBean==null){
            hBasePageBean = new HBasePageBean<HServiceComplaint>();
        }
        hBasePageBean.setPageSize(pagesize);
        hBasePageBean.setCurrPage(page);
        hBasePageBean.setNextPageRow(startRowKey);
        hBasePageBean = hServiceComplaintService.queryScanGreater(hBasePageBean,new HServiceComplaint(),page,pagesize,startRowKey,null);
        request.getSession().setAttribute("hBasePageBean",hBasePageBean);
        model.addAttribute("hBasePageBean",hBasePageBean);
        return "hServiceComplaintList";
    }
}
