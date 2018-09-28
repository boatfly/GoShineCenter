package com.goshine.gscenter.common.hbase.instance.service;

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
    @Transactional
    public List<HMessage> getById(HMessage hMessage, String rowkey) {
        return hMessageDao.getById(hMessage, rowkey);
    }

    public List<HMessage> queryScanGreater(HMessage hMessage,Map<String, String> param) throws Exception {
        return hMessageDao.queryScanGreater(hMessage,param);
    }
}
