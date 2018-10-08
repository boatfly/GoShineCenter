package com.goshine.gscenter.common.hbase.instance.service;

import com.goshine.gscenter.common.hbase.HBasePageBean;
import com.goshine.gscenter.common.hbase.instance.dao.HServiceComplaintDao;
import com.goshine.gscenter.common.hbase.instance.entity.HMessage;
import com.goshine.gscenter.common.hbase.instance.entity.HServiceComplaint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service("hServiceComplaintService")
public class HServiceComplaintService {

    @Autowired
    private HServiceComplaintDao hServiceComplaintDao;

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void batchAdd(List<HServiceComplaint> hServiceComplaints){
        hServiceComplaintDao.batchAdd(hServiceComplaints);
    }

    public List<HServiceComplaint> getById(HServiceComplaint hMessage, String rowkey) {
        return hServiceComplaintDao.getById(hMessage, rowkey);
    }

    public HBasePageBean queryScanGreater(HBasePageBean<HServiceComplaint> hBasePageBean, HServiceComplaint hMessage, Integer page, Integer pagesize, String startRowKey, Map<String, String> param) throws Exception {
        hBasePageBean = hServiceComplaintDao.queryPageScanGreater(hBasePageBean,hMessage,param);
        return hBasePageBean;
    }
}
