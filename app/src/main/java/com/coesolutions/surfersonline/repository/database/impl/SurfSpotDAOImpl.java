package com.coesolutions.surfersonline.repository.database.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.coesolutions.surfersonline.model.SurfSpot;
import com.coesolutions.surfersonline.repository.database.SurfSpotDAO;
import com.coesolutions.surfersonline.repository.database.DBAdapter;

import java.util.ArrayList;
import java.util.List;

public class SurfSpotDAOImpl implements SurfSpotDAO {
    private SQLiteDatabase database;
    private final DBAdapter dbHelper;

    public SurfSpotDAOImpl(Context context) {
        dbHelper = new DBAdapter(context);
    }

    private void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    private void close() {
        dbHelper.close();
    }
    @Override
    public void createSurfSpot(SurfSpot surfSpot) {
        open();
        ContentValues values = new ContentValues();
        values.put(DBAdapter.COLUMN_NAME, surfSpot.getBasicInfo().getName());
        values.put(DBAdapter.COLUMN_DESCRIPTION, surfSpot.getBasicInfo().getDescription());

        database.insert(DBAdapter.TABLE_SURFSPOT, null, values);
        close();
    }

    @Override
    public void updateSurfSpot(SurfSpot surfSpot) {
        open();
        ContentValues values = new ContentValues();

        values.put(DBAdapter.COLUMN_NAME, surfSpot.getBasicInfo().getDescription());
        values.put(DBAdapter.COLUMN_DESCRIPTION, surfSpot.getBasicInfo().getDescription());

        database.update(DBAdapter.TABLE_SURFSPOT, values, DBAdapter.COLUMN_ID + " = ? ",
                new String[]{String.valueOf(surfSpot.getId())});
        close();
    }

    @Override
    public SurfSpot findSurfSpotById(long id) {
        open();
        Cursor cursor = database.query(DBAdapter.TABLE_SURFSPOT, new String[]{
                        DBAdapter.COLUMN_ID, DBAdapter.COLUMN_NAME, DBAdapter.COLUMN_DESCRIPTION},
                DBAdapter.COLUMN_ID + " = ? ", new String[]{String.valueOf(id)}, null, null, null, null);

        SurfSpot surfSpot = new SurfSpot();
        if (cursor != null) {
            cursor.moveToFirst();

            if (!cursor.isNull(0)) {
                surfSpot.setId(cursor.getInt(0));
                surfSpot.setBasicInfo(cursor.getString(1), cursor.getString(2));
            }
            cursor.close();
        }
        close();

        return surfSpot;
    }

    @Override
    public void deleteSurfSpot(SurfSpot surfSpot) {
        open();
        database.delete(DBAdapter.TABLE_SURFSPOT, DBAdapter.COLUMN_ID + " = ? ",
                new String[]{String.valueOf(surfSpot.getId())});
        close();
    }

    @Override
    public List<SurfSpot> getSurfSpotList() {
        String selectQuery = "SELECT " + DBAdapter.COLUMN_ID + ", " + DBAdapter.COLUMN_NAME + ", " +
                DBAdapter.COLUMN_DESCRIPTION + " FROM " + DBAdapter.TABLE_SURFSPOT;
        List<SurfSpot> surfSpots = new ArrayList<>();
        open();
        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor.moveToFirst () ) {
            do {
                final SurfSpot surfSpot = new SurfSpot();
                surfSpot.setId(cursor.getInt(0));
                surfSpot.setBasicInfo(cursor.getString(1), cursor.getString(2));
                surfSpots.add(surfSpot);
            } while (cursor.moveToNext());
        }
        cursor.close();
        close();
        return surfSpots;
    }
}
