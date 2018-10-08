package com.goshine.gscenter.common.hbase.instance.entity;

import com.goshine.gscenter.common.hbase.annotations.HbaseColumn;
import com.goshine.gscenter.common.hbase.annotations.HbaseTable;

/**
 * 客户服务
 */
@HbaseTable(tableName="t_hmessage")
public class HMessage {
    @HbaseColumn(family="rowkey", qualifier="rowkey")
    private String id;

    @HbaseColumn(family="demo", qualifier="msg")
    private String msg;

    @HbaseColumn(family="demo", qualifier="sendTime")
    private String sendTime;

    private String ssdw;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }
}
