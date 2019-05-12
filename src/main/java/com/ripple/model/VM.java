package com.ripple.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by intel on 5/10/2019.
 */
@Entity
public class VM {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    private String userId;
    private String os;
    private String ram;
    private String hardDisk;
    private String cpucores;

    public long getId() {
        return id;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public String getHardDisk() {
        return hardDisk;
    }

    public void setHardDisk(String hardDisk) {
        this.hardDisk = hardDisk;
    }

    public String getCpucores() {
        return cpucores;
    }

    public void setCpucores(String cpucores) {
        this.cpucores = cpucores;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String toString(){
        return "OS "+os+"\n RAM "+ ram + "\n hard disk "+ hardDisk + "\n cpuCores "+cpucores+"\n userId "+ userId;
    }

}
