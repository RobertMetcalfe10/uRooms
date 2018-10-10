package com.halaltokens.halaltokens;

import org.jsoup.select.Elements;

import java.util.Iterator;

public class RoomInfo {

    private String startTime;
    private String endTime;
    private String moduleCode;
    private String organization;

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

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public void setRoomInfo(Elements roomInfo) {
        Iterator iterator = roomInfo.eachText().iterator();
        setStartTime(iterator.next().toString());
        setEndTime(iterator.next().toString());
        setModuleCode(iterator.next().toString());
        setOrganization(iterator.next().toString());
    }

}