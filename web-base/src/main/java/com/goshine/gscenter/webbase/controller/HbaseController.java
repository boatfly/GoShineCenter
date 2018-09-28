package com.goshine.gscenter.webbase.controller;

import com.goshine.gscenter.common.hbase.instance.entity.HMessage;
import com.goshine.gscenter.common.hbase.instance.service.HMessageService;
import com.goshine.gscenter.gskit.mapper.JsonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HbaseController {
    @Autowired
    private HMessageService hMessageService;

    @GetMapping("/hbase/hmessages")
    public String allHmessages() throws Exception {
        List<HMessage> list = hMessageService.queryScanGreater(new HMessage(),null);
        return JsonMapper.INSTANCE.toJson(list);
    }
}
