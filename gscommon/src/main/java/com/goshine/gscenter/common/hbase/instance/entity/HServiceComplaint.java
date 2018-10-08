package com.goshine.gscenter.common.hbase.instance.entity;

import com.goshine.gscenter.common.hbase.annotations.HbaseColumn;
import com.goshine.gscenter.common.hbase.annotations.HbaseTable;

/**
 * hbase 表实体：服务投诉
 * 客户对服务行为、服务渠道等不满进行投诉。
 */
@HbaseTable(tableName="t_hservicecomplaint")
public class HServiceComplaint {
    @HbaseColumn(family="rowkey", qualifier="rowkey")
    private String id;

    //受理内容
    @HbaseColumn(family="demo", qualifier="content")
    private String content;

    //受理时间
    @HbaseColumn(family="demo", qualifier="sendTime")
    private String sendTime;

    //工单号
    @HbaseColumn(family="demo", qualifier="gdh")
    private String gdh;

    //国网工单号
    @HbaseColumn(family="demo", qualifier="gwgdh")
    private String gwgdh;

    //受理人员
    @HbaseColumn(family="demo", qualifier="slry")
    private String slry;

    //处理人工号
    @HbaseColumn(family="demo", qualifier="slrgh")
    private String slrgh;

    //处理部门
    @HbaseColumn(family="demo", qualifier="clbm")
    private String clbm;

    //处理时间
    @HbaseColumn(family="demo", qualifier="clsj")
    private String clsj;

    //用户编号
    @HbaseColumn(family="demo", qualifier="yhbh")
    private String yhbh;

    //用户名称
    @HbaseColumn(family="demo", qualifier="yhmc")
    private String yhmc;

    //联系地址
    @HbaseColumn(family="demo", qualifier="lxdz")
    private String lxdz;

    //诉求类别
    @HbaseColumn(family="demo", qualifier="sqlb")
    private String sqlb;

    //所属单位
    @HbaseColumn(family="demo", qualifier="ssdw")
    private String ssdw;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getGdh() {
        return gdh;
    }

    public void setGdh(String gdh) {
        this.gdh = gdh;
    }

    public String getGwgdh() {
        return gwgdh;
    }

    public void setGwgdh(String gwgdh) {
        this.gwgdh = gwgdh;
    }

    public String getSlry() {
        return slry;
    }

    public void setSlry(String slry) {
        this.slry = slry;
    }

    public String getSlrgh() {
        return slrgh;
    }

    public void setSlrgh(String slrgh) {
        this.slrgh = slrgh;
    }

    public String getClbm() {
        return clbm;
    }

    public void setClbm(String clbm) {
        this.clbm = clbm;
    }

    public String getClsj() {
        return clsj;
    }

    public void setClsj(String clsj) {
        this.clsj = clsj;
    }

    public String getYhbh() {
        return yhbh;
    }

    public void setYhbh(String yhbh) {
        this.yhbh = yhbh;
    }

    public String getYhmc() {
        return yhmc;
    }

    public void setYhmc(String yhmc) {
        this.yhmc = yhmc;
    }

    public String getLxdz() {
        return lxdz;
    }

    public void setLxdz(String lxdz) {
        this.lxdz = lxdz;
    }

    public String getSqlb() {
        return sqlb;
    }

    public void setSqlb(String sqlb) {
        this.sqlb = sqlb;
    }

    public String getSsdw() {
        return ssdw;
    }

    public void setSsdw(String ssdw) {
        this.ssdw = ssdw;
    }
}
