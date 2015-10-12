package com.coesolutions.surfersonline.repository.database;


import com.coesolutions.surfersonline.model.SurfArea;

import java.util.List;

public interface SurfAreaDAO {
    void createSurfArea(SurfArea SurfArea);
    void updateSurfArea(SurfArea SurfArea);
    SurfArea findSurfAreaById(long id);
    void deleteSurfArea(SurfArea SurfArea);
    List<SurfArea> getSurfAreaList();
}
