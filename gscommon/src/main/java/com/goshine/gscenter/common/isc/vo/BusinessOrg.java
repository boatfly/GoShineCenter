package com.goshine.gscenter.common.isc.vo;

import java.io.Serializable;

/**
 * 概述:  业务组织，用于记录简单业务组织信息
 */
public class BusinessOrg implements Serializable {
    private static final long serialVersionUID = 1L;

    private String orgId;
    private String orgName;
    private String parentOrgId;

    public String getOrgId() {
        return orgId;
    }
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }
    public String getOrgName() {
        return orgName;
    }
    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }
    public String getParentOrgId() {
        return parentOrgId;
    }
    public void setParentOrgId(String parentOrgId) {
        this.parentOrgId = parentOrgId;
    }

}
