package com.goshine.gscenter.common.model;

import java.io.Serializable;

public class Unit implements Serializable {
    private static final long serialVersionUID = 1L;


    private String orgId;

    private String orgNo;
    private String orgName;
    private String pOrgId;
    private String pOrgName;
    private String orgTypeCode;
    private String orgTypeName;
    private int sortNo;


    public String getOrgId() {

        return orgId;
    }


    public void setOrgId(String orgId) {

        this.orgId = orgId;
    }
    public String getOrgNo() {
        return orgNo;
    }
    public void setOrgNo(String orgNo) {
        this.orgNo = orgNo;
    }
    public String getOrgName() {
        return orgName;
    }
    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }
    public String getpOrgId() {
        return pOrgId;
    }
    public void setpOrgId(String pOrgId) {
        this.pOrgId = pOrgId;
    }
    public String getpOrgName() {
        return pOrgName;
    }
    public void setpOrgName(String pOrgName) {
        this.pOrgName = pOrgName;
    }
    public String getOrgTypeCode() {
        return orgTypeCode;
    }
    public void setOrgTypeCode(String orgTypeCode) {
        this.orgTypeCode = orgTypeCode;
    }
    public String getOrgTypeName() {
        return orgTypeName;
    }
    public void setOrgTypeName(String orgTypeName) {
        this.orgTypeName = orgTypeName;
    }
    public int getSortNo() {
        return sortNo;
    }
    public void setSortNo(int sortNo) {
        this.sortNo = sortNo;
    }
}