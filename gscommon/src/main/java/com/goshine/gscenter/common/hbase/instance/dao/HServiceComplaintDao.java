package com.goshine.gscenter.common.hbase.instance.dao;

import com.goshine.gscenter.common.hbase.HBaseDaoUtil;
import com.goshine.gscenter.common.hbase.HBasePageBean;
import com.goshine.gscenter.common.hbase.instance.entity.HMessage;
import com.goshine.gscenter.common.hbase.instance.entity.HServiceComplaint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component("hServiceComplaintDao")
public class HServiceComplaintDao {

    @Autowired
    private HBaseDaoUtil hBaseDaoUtil;

    public void batchAdd(List<HServiceComplaint> hServiceComplaints){
        hBaseDaoUtil.batchSave(hServiceComplaints);
    }

    public List<HServiceComplaint> getById(HServiceComplaint hServiceComplaint, String rowkey) {
        return hBaseDaoUtil.get(hServiceComplaint, rowkey);
    }

    public HBasePageBean queryPageScanGreater(HBasePageBean<HServiceComplaint> hBasePageBean, HServiceComplaint hServiceComplaint, Map<String, String> param) throws Exception {
        return hBaseDaoUtil.queryPageScanGreater(hBasePageBean,hServiceComplaint,param);
    }
}
