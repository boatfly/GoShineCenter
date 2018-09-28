package com.goshine.gscenter.common.model;

import java.io.Serializable;

public class OstaffPhoto implements Serializable {
    private Long photoId;

    private String empNo;

    private String entPhoto;

    private static final long serialVersionUID = 1L;

    public Long getPhotoId() {
        return photoId;
    }

    public void setPhotoId(Long photoId) {
        this.photoId = photoId;
    }

    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo == null ? null : empNo.trim();
    }

    public String getEntPhoto() {
        return entPhoto;
    }

    public void setEntPhoto(String entPhoto) {
        this.entPhoto = entPhoto == null ? null : entPhoto.trim();
    }
}
