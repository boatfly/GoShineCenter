package com.goshine.gscenter.common.isc.vo;

import java.util.List;

public class TreeIsc implements java.io.Serializable {

    private static final long serialVersionUID = 980682543891282923L;
    private String id;
    private String text;
    private String state = "open";// open,closed
    private boolean checked = false;
    private Object attributes;
    private List<TreeIsc> children;
    private String iconCls;
    private String pid;
    private String value;
    private String type;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text == null ? null : text.trim();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public Object getAttributes() {
        return attributes;
    }

    public void setAttributes(Object attributes) {
        this.attributes = attributes == null ? null : attributes;
    }

    public List<TreeIsc> getChildren() {
        return children;
    }

    public void setChildren(List<TreeIsc> children) {
        this.children = children == null ? null : children;
    }

    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls == null ? null : iconCls.trim();
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid == null ? null : pid.trim();
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    @Override
    public String toString() {
        return "Tree [id=" + id + ", text=" + text + ", state=" + state + ", checked=" + checked + ", attributes="
                + attributes + ", children=" + children + ", iconCls=" + iconCls + ", pid=" + pid + ", value=" + value
                + ", type=" + type + "]";
    }

}
