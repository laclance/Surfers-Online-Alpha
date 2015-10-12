package com.coesolutions.surfersonline.model;

import java.util.ArrayList;
import java.util.List;

public class SurfArea {
    private long id;
    private BasicInfo basicInfo;
    private List<SurfSpot> surfSpots;

    public SurfArea() {
    }

    public SurfArea(long id, String name, String description) {
        this.id = id;
        this.basicInfo = new BasicInfo(name, description);
        surfSpots = new ArrayList<>();
    }

    public SurfArea(String name, String description) {
        this.basicInfo = new BasicInfo(name, description);
        surfSpots = new ArrayList<>();
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

    public List<SurfSpot> getSurfSpots() {
        return surfSpots;
    }

    public void setSurfSpots(List<SurfSpot> surfSpots) {
        this.surfSpots = surfSpots;
    }

    public int getSurfSpotCount(){
        return surfSpots.size();
    }

    @Override
    public String toString() {
        return "Area: " + id + basicInfo.toString();
    }
}
