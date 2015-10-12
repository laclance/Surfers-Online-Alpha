package com.coesolutions.surfersonline.model;

public class SurfSpot {
    private long id;
    private BasicInfo basicInfo;

    public SurfSpot() {
    }

    public SurfSpot(String name, String description) {
        this.basicInfo = new BasicInfo(name, description);
    }

    public SurfSpot(long id, String name, String description) {
        this.id = id;
        this.basicInfo = new BasicInfo(name, description);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BasicInfo getBasicInfo() {
        return basicInfo;
    }

    public void setBasicInfo(String name, String description) {
        this.basicInfo = new BasicInfo(name, description);
    }
}

