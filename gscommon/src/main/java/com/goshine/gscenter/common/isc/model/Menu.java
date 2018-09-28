package com.goshine.gscenter.common.isc.model;

import java.io.Serializable;

public class Menu implements Serializable {
    private static final long serialVersionUID = 1L;

    private String menuId;
    private String menuName;
    private String menuCode;
    private String menuUrl;
    private String menuParentId;
    private String iconCls;

    public String getMenuId() {
        return menuId;
    }
    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }
    public String getMenuName() {
        return menuName;
    }
    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }
    public String getMenuCode() {
        return menuCode;
    }
    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }
    public String getMenuUrl() {
        return menuUrl;
    }
    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }
    public String getMenuParentId(){
        return menuParentId;
    }
    public void setMenuParentId(String menuParentId){
        this.menuParentId = menuParentId;
    }
    public String getIconCls(){
        return iconCls;
    }
    public void setIconCls(String iconCls){
        this.iconCls = iconCls;
    }

}
