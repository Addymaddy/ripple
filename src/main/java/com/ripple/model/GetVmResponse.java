package com.ripple.model;

import java.util.List;

public class GetVmResponse extends ApplicationResponse {
    public GetVmResponse(String app_status_code, String msg) {
        super(app_status_code, msg);
    }

    private List<VM> vmList;


    public List<VM> getVmList() {
        return vmList;
    }

    public void setVmList(List<VM> vmList) {
        this.vmList = vmList;
    }
}
