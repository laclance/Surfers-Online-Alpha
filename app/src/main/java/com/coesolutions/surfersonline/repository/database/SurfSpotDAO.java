package com.coesolutions.surfersonline.repository.database;

import com.coesolutions.surfersonline.model.SurfSpot;

import java.util.List;

public interface SurfSpotDAO {
    void createSurfSpot(SurfSpot SurfSpot);
    void updateSurfSpot(SurfSpot SurfSpot);
    SurfSpot findSurfSpotById(long id);
    void deleteSurfSpot(SurfSpot SurfSpot);
    List<SurfSpot> getSurfSpotList();
}
