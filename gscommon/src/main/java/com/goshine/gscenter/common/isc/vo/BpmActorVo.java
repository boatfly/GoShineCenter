package com.goshine.gscenter.common.isc.vo;

import java.io.Serializable;

public class BpmActorVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    private String type;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


}
