package com.goshine.gscenter.common.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 员工表
 */
public class Staff implements Serializable {
    /**
     * 本实体记录的唯一标识。
     */
    private String empNo;

    private Long deptId;

    private String orgIdIsc;

    private String createTime;

    /**
     * 部门名称
     */
    private String deptName;

    private Long stationNo;
    /**
     * 工号，营销业务人员的服务工号
     */
    private String staffNo;
    /**
     * 业务人员姓名
     */
    private String name;
    /**
     * 性别。01 男、02 女
     */
    private String gender;
    /**
     * 所在职位名称。
     */
    private String posName;
    /**
     * 人员所在岗位代码
     */
    private String position;
    /**
     * 工作分工种类：01 检定人员、02 修校人员、03 装表接电。
     */
    private String workTypeCode;
    /**
     * 人员的技术等级代码
     */
    private String techLevelCode;
    /**
     * 出生年月日
     */
    private String ymd;
    /**
     * 文化程度。01 专科、02 本科、03 研究生、04 博士、05 博士后。
     */
    private String degreeCode;
    /**
     * 人员联系手机号码
     */
    private String mobile;
    /**
     * 办公室电话号码',
     */
    private String officeTelNo;
    /**
     * 服务人员的服务等级设置。01 一级、02 二级、03 三级、04 四级、05 五级。
     */
    private String srvLevelCode;
    /**
     * 是否持有专业学习证明
     */
    private String certFlag;
    /**
     * 是否为定编人员
     */
    private String fixedFlag;
    /**
     * 是否在职标志
     */
    private String onPosFlag;
    /**
     * 技术专业说明
     */
    private String professionCode;
    /**
     * 本人在该业务专业工作的开始日期
     */
    private String professionBgnDate;
    /**
     * 参加工作开始日期
     */
    private String joinDate;
    /**
     * 个人职称说明
     */
    private String titel;
    /**
     * 政治面貌，如
     * 01 群众
     * 02 团员
     * 03 党员',
     */
    private String politicalStatusCode;
    /**
     * '职称级别，如
     * 01 初级
     * 02 中级
     * 03 高级及以上',
     */
    private String titleLevelCode;

    private String statusCode;
    /**
     * 人员的特殊说明
     */
    private String remark;
    /**
     * 门禁卡号
     */
    private String entGuaNum;

    /**
     * 门禁系统人员编号
     */
    private String entGuaId;
    /**
     * 门禁组号
     */
    private String entGroup;
    /**
     * 是否使用时间段 1：是 默认 0：否
     */
    private String isGroupTz;

    /**
     * 门禁密码
     */
    private String entGuaPwd;

    /**
     * 人员的相片
     */
    private String photo;


    private Long stationId;

    private String orgId;

    private String staffType;

    /**
     * 门禁机编号
     */
    private String sensorNo;

    /**
     * 指纹信息
     */
    private List<OStaffFingerprint> OStaffFingerprintList = new ArrayList<>();


    /**
     * 人脸信息
     */
    private List<OstaffPhoto> photoList = new ArrayList<>();


    /**
     * 个人时间段封装
     */
    private String staffAtzString;

    /**
     * 个人验证方式封装
     */
    private String verifyMethod;

    /**
     * 门禁授权用户标识封装
     */
    private Long authId;

    private static final long serialVersionUID = 1L;


    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getOrgIdIsc() {
        return orgIdIsc;
    }

    public void setOrgIdIsc(String orgIdIsc) {
        this.orgIdIsc = orgIdIsc;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Long getStationNo() {
        return stationNo;
    }

    public void setStationNo(Long stationNo) {
        this.stationNo = stationNo;
    }

    public String getStaffNo() {
        return staffNo;
    }

    public void setStaffNo(String staffNo) {
        this.staffNo = staffNo == null ? null : staffNo.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender == null ? null : gender.trim();
    }

    public String getPosName() {
        return posName;
    }

    public void setPosName(String posName) {
        this.posName = posName == null ? null : posName.trim();
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position == null ? null : position.trim();
    }

    public String getWorkTypeCode() {
        return workTypeCode;
    }

    public void setWorkTypeCode(String workTypeCode) {
        this.workTypeCode = workTypeCode == null ? null : workTypeCode.trim();
    }

    public String getTechLevelCode() {
        return techLevelCode;
    }

    public void setTechLevelCode(String techLevelCode) {
        this.techLevelCode = techLevelCode == null ? null : techLevelCode.trim();
    }


    public String getDegreeCode() {
        return degreeCode;
    }

    public void setDegreeCode(String degreeCode) {
        this.degreeCode = degreeCode == null ? null : degreeCode.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getOfficeTelNo() {
        return officeTelNo;
    }

    public void setOfficeTelNo(String officeTelNo) {
        this.officeTelNo = officeTelNo == null ? null : officeTelNo.trim();
    }

    public String getSrvLevelCode() {
        return srvLevelCode;
    }

    public void setSrvLevelCode(String srvLevelCode) {
        this.srvLevelCode = srvLevelCode == null ? null : srvLevelCode.trim();
    }

    public String getCertFlag() {
        return certFlag;
    }

    public void setCertFlag(String certFlag) {
        this.certFlag = certFlag == null ? null : certFlag.trim();
    }

    public String getFixedFlag() {
        return fixedFlag;
    }

    public void setFixedFlag(String fixedFlag) {
        this.fixedFlag = fixedFlag == null ? null : fixedFlag.trim();
    }

    public String getOnPosFlag() {
        return onPosFlag;
    }

    public void setOnPosFlag(String onPosFlag) {
        this.onPosFlag = onPosFlag == null ? null : onPosFlag.trim();
    }

    public String getProfessionCode() {
        return professionCode;
    }

    public void setProfessionCode(String professionCode) {
        this.professionCode = professionCode == null ? null : professionCode.trim();
    }


    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel == null ? null : titel.trim();
    }

    public String getPoliticalStatusCode() {
        return politicalStatusCode;
    }

    public void setPoliticalStatusCode(String politicalStatusCode) {
        this.politicalStatusCode = politicalStatusCode == null ? null : politicalStatusCode.trim();
    }

    public String getTitleLevelCode() {
        return titleLevelCode;
    }

    public void setTitleLevelCode(String titleLevelCode) {
        this.titleLevelCode = titleLevelCode == null ? null : titleLevelCode.trim();
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode == null ? null : statusCode.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getEntGuaNum() {
        return entGuaNum;
    }

    public void setEntGuaNum(String entGuaNum) {
        this.entGuaNum = entGuaNum == null ? null : entGuaNum.trim();
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Long getStationId() {
        return stationId;
    }

    public void setStationId(Long stationId) {
        this.stationId = stationId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getYmd() {
        return ymd;
    }

    public void setYmd(String ymd) {
        this.ymd = ymd;
    }

    public String getProfessionBgnDate() {
        return professionBgnDate;
    }

    public void setProfessionBgnDate(String professionBgnDate) {
        this.professionBgnDate = professionBgnDate;
    }

    public String getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
    }

    public String getStaffType() {
        return staffType;
    }

    public void setStaffType(String staffType) {
        this.staffType = staffType;
    }

    public String getEntGuaId() {
        return entGuaId;
    }

    public void setEntGuaId(String entGuaId) {
        this.entGuaId = entGuaId;
    }

    public String getEntGroup() {
        return entGroup;
    }

    public void setEntGroup(String entGroup) {
        this.entGroup = entGroup;
    }

    public String getIsGroupTz() {
        return isGroupTz;
    }

    public void setIsGroupTz(String isGroupTz) {
        this.isGroupTz = isGroupTz;
    }


    public String getEntGuaPwd() {
        return entGuaPwd;
    }

    public void setEntGuaPwd(String entGuaPwd) {
        this.entGuaPwd = entGuaPwd;
    }


    public String getSensorNo() {
        return sensorNo;
    }

    public void setSensorNo(String sensorNo) {
        this.sensorNo = sensorNo;
    }


    public String getStaffAtzString() {
        return staffAtzString;
    }

    public void setStaffAtzString(String staffAtzString) {
        this.staffAtzString = staffAtzString;
    }

    public List<OStaffFingerprint> getOStaffFingerprintList() {
        return OStaffFingerprintList;
    }

    public void setOStaffFingerprintList(List<OStaffFingerprint> oStaffFingerprintList) {
        OStaffFingerprintList = oStaffFingerprintList;
    }


    public Long getAuthId() {
        return authId;
    }

    public void setAuthId(Long authId) {
        this.authId = authId;
    }


    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }


    public String getVerifyMethod() {
        return verifyMethod;
    }

    public void setVerifyMethod(String verifyMethod) {
        this.verifyMethod = verifyMethod;
    }

    @Override
    public String toString() {
        return "Staff [empNo=" + empNo + ", deptId=" + deptId + ", stationNo=" + stationNo + ", staffNo=" + staffNo
                + ", name=" + name + ", gender=" + gender + ", posName=" + posName + ", position=" + position
                + ", workTypeCode=" + workTypeCode + ", techLevelCode=" + techLevelCode + ", ymd=" + ymd
                + ", degreeCode=" + degreeCode + ", mobile=" + mobile + ", officeTelNo=" + officeTelNo
                + ", srvLevelCode=" + srvLevelCode + ", certFlag=" + certFlag + ", fixedFlag=" + fixedFlag
                + ", onPosFlag=" + onPosFlag + ", professionCode=" + professionCode + ", professionBgnDate="
                + professionBgnDate + ", joinDate=" + joinDate + ", titel=" + titel + ", politicalStatusCode="
                + politicalStatusCode + ", titleLevelCode=" + titleLevelCode + ", statusCode=" + statusCode
                + ", remark=" + remark + ", entGuaNum=" + entGuaNum + ", entGuaId=" + entGuaId + ", entGroup="
                + entGroup + ", isGroupTz=" + isGroupTz + ", entGuaPwd=" + entGuaPwd + ", photo=" + photo
                + ", stationId=" + stationId + ", orgId=" + orgId + ", staffType=" + staffType + "]";
    }

    public List<OstaffPhoto> getPhotoList() {
        return photoList;
    }

    public void setPhotoList(List<OstaffPhoto> photoList) {
        this.photoList = photoList;
    }


}
