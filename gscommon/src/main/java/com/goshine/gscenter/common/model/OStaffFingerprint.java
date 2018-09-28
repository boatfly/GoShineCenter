package com.goshine.gscenter.common.model;

import java.io.Serializable;

public class OStaffFingerprint implements Serializable {
    private Long fingerprintId;

    private String empNo;

    private String entFingerprint;

    private static final long serialVersionUID = 1L;

    public Long getFingerprintId() {
        return fingerprintId;
    }

    public void setFingerprintId(Long fingerprintId) {
        this.fingerprintId = fingerprintId;
    }



    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    public String getEntFingerprint() {
        return entFingerprint;
    }

    public void setEntFingerprint(String entFingerprint) {
        this.entFingerprint = entFingerprint == null ? null : entFingerprint.trim();
    }
}
