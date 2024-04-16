package com.example.travelor.bean;

import java.io.Serializable;

public class Plans implements Serializable {
    private String mainPlan;
    private String details;
    private String createTime;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMainPlan() {
        return mainPlan;
    }

    public void setMainPlan(String main_plan) {
        this.mainPlan = main_plan;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String create_time) {
        this.createTime = create_time;
    }

    @Override
    public String toString() {
        return "plans{" +
                "main_plan='" + mainPlan + '\'' +
                ", details='" + details + '\'' +
                ", create_time='" + createTime + '\'' +
                '}';
    }
}
