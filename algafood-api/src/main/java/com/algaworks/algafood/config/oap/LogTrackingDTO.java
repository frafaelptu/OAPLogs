package com.algaworks.algafood.config.oap;

import java.util.Map;

public class LogTrackingDTO {

    private String startTime;
    private String endTime;
    private String duration;
    private String method;
    private String className;
    private Map<String, String> Context;

    public Map<String, String> getContext() {
        return Context;
    }

    public void setContext(Map<String, String> context) {
        Context = context;
    }

    private boolean logCompleted = false;


    public boolean isLogCompleted() {
        return logCompleted;
    }

    public void setLogCompleted(boolean logCompleted) {
        this.logCompleted = logCompleted;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

}
