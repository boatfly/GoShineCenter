package com.goshine.gscenter.common.hbase.instance.service;

import com.goshine.gscenter.common.hbase.HBaseDaoUtil;
import com.goshine.gscenter.common.hbase.HBasePageBean;
import com.goshine.gscenter.common.hbase.instance.dao.HMessageDao;
import com.goshine.gscenter.common.hbase.instance.entity.HMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service("hMessageService")
@Transactional(readOnly = true)
public class HMessageService {
    @Autowired
    private HMessageDao hMessageDao;

    @Autowired
    private HBaseDaoUtil hBaseDaoUtil;

    /**
     * @param hMessage
     */
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void save(HMessage hMessage) {
        hMessageDao.save(hMessage);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void batchAdd(List<HMessage> hMessages){
        hMessageDao.batchAdd(hMessages);
    }

    /**
     * @param hMessage
     * @param rowkey
     */
    public List<HMessage> getById(HMessage hMessage, String rowkey) {
        return hMessageDao.getById(hMessage, rowkey);
    }

    public HBasePageBean queryScanGreater(HBasePageBean<HMessage> hBasePageBean,HMessage hMessage,Integer page,Integer pagesize,String startRowKey, Map<String, String> param) throws Exception {
        //hBasePageBean.setList(hMessageDao.queryScanGreater(hMessage,param));
        hBasePageBean = hMessageDao.queryPageScanGreater(hBasePageBean,hMessage,param);
        return hBasePageBean;
    }
}
