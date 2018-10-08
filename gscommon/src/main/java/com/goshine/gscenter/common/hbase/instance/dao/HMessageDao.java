package com.goshine.gscenter.common.hbase.instance.dao;

import com.goshine.gscenter.common.hbase.HBaseDaoUtil;
import com.goshine.gscenter.common.hbase.HBasePageBean;
import com.goshine.gscenter.common.hbase.instance.entity.HMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component("hMessageDao")
public class HMessageDao {
    @Autowired
    private HBaseDaoUtil hBaseDaoUtil;

    /**
     * @param hMessage
     */
    public void save(HMessage hMessage) {
        hBaseDaoUtil.save(hMessage);
    }

    /**
     *
     * @param hMessages
     */
    public void batchAdd(List<HMessage> hMessages){
        hBaseDaoUtil.batchSave(hMessages);
    }

    /**
     * @param hMessage
     * @param rowkey
     */
    public List<HMessage> getById(HMessage hMessage, String rowkey) {
        return hBaseDaoUtil.get(hMessage, rowkey);
    }

    public List<HMessage> queryScanGreater(HMessage hMessage,Map<String, String> param) throws Exception {
        return hBaseDaoUtil.queryScanGreater(hMessage,param);
    }

    public HBasePageBean queryPageScanGreater(HBasePageBean<HMessage> hBasePageBean,HMessage hMessage, Map<String, String> param) throws Exception {
        return hBaseDaoUtil.queryPageScanGreater(hBasePageBean,hMessage,param);
    }
}
