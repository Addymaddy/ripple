package com.ripple.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by intel on 5/10/2019.
 */
public class ApplicationResponse {

    public ApplicationResponse(String app_status_code, String msg) {
        this.app_status_code = app_status_code;
        this.msg = msg;
        this.params = new HashMap<>();
    }

    String app_status_code;
    String msg;
    Map<String, String> params;

    public String getApp_status_code() {
        return app_status_code;
    }

    public void setApp_status_code(String app_status_code) {
        this.app_status_code = app_status_code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public void addParams(String key, String value){
        params.put(key, value);
    }


}
