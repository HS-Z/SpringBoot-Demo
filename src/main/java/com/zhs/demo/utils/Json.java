package com.zhs.demo.utils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Json implements Serializable {

    private Boolean success=false;

    private String msg = "";

    private Object obj = null;

    private Map<String, Object> others;


    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
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

    public Map<String, Object> getOthers() {
        return others;
    }

    public void setOthers(Map<String, Object> others) {
        this.others = others;
    }



    public Json addOthers(String key, Object other) {
        if (this.others == null)
            this.others = new HashMap<String, Object>();
        this.others.put(key, other);
        return this;
    }


    public Json addObj(Object obj) {
        this.obj = obj;
        return this;
    }


    public static Json ok() {
        Json j = new Json();
        j.setSuccess(true);
        return j;
    }

    public static Json ok(String msg) {
        Json j = new Json();
        j.setSuccess(true);
        j.setMsg(msg);
        return j;
    }

    public static Json ok(String msg, Object obj) {
        Json j = new Json();
        j.setSuccess(true);
        j.setMsg(msg);
        j.setObj(obj);
        return j;
    }

    public static Json fail() {
        Json j = new Json();
        j.setSuccess(false);
        return j;
    }

    public static Json fail(String msg) {
        Json j = new Json();
        j.setSuccess(false);
        j.setMsg(msg);
        return j;
    }

    public static Map<String, Object> failForMobile(String msg) {
        Map<String, Object> ret = new HashMap<>();
        ret.put("success", "fail");
        ret.put("msg", msg);
        return ret;
    }

    public static Map<String, Object> successForMobile(String msg) {
        Map<String, Object> ret = new HashMap<>();
        ret.put("success", "success");
        ret.put("msg", msg);
        return ret;
    }

    public static Map<String, Object> successJson(String msg) {
        Map<String, Object> ret = new HashMap<>();
        ret.put("success", true);
        ret.put("msg", msg);
        return ret;
    }


    public static Map<String, Object> failJson(String msg) {
        Map<String, Object> ret = new HashMap<>();
        ret.put("success", false);
        ret.put("msg", msg);
        return ret;
    }


}
