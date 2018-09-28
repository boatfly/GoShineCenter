package com.goshine.gscenter.request;

import org.springframework.ui.Model;

import java.io.Serializable;

public class Result implements Serializable {

    public static final int SUCCESS = 1;
    public static final int FAILURE = -1;

    private static final long serialVersionUID = 5576237395711742682L;

    private boolean success = false;

    private String msg = "";

    private Object obj = null;

    private Model model=null;

    public Result(){}

    public Result(boolean success, String msg) {
        super();
        this.success = success;
        this.msg = msg;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

}