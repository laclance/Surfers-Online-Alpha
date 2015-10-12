package com.coesolutions.surfersonline.repository.database.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.coesolutions.surfersonline.model.SurfArea;
import com.coesolutions.surfersonline.repository.database.SurfAreaDAO;
import com.coesolutions.surfersonline.repository.database.DBAdapter;

import java.util.ArrayList;
import java.util.List;

public class SurfAreaDAOImpl implements SurfAreaDAO{
    private SQLiteDatabase database;
    private final DBAdapter dbHelper;

    public SurfAreaDAOImpl(Context context) {
        dbHelper = new DBAdapter(context);
    }

    private void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    private void close() {
        dbHelper.close();
    }

    @Override
    public void createSurfArea(SurfArea surfArea) {
        open();
        ContentValues values = new ContentValues();
        values.put(DBAdapter.COLUMN_NAME, surfArea.getBasicInfo().getName());
        values.put(DBAdapter.COLUMN_DESCRIPTION, surfArea.getBasicInfo().getDescription());

        database.insert(DBAdapter.TABLE_SURFAREA, null, values);
        close();
    }

    @Override
    public void updateSurfArea(SurfArea surfArea) {
        open();
        ContentValues values = new ContentValues();

        values.put(DBAdapter.COLUMN_NAME, surfArea.getBasicInfo().getName());
        values.put(DBAdapter.COLUMN_DESCRIPTION, surfArea.getBasicInfo().getDescription());

        int x = database.update(DBAdapter.TABLE_SURFAREA, values, DBAdapter.COLUMN_ID + " = ? ",
                new String[]{String.valueOf(surfArea.getId())});
        close();
    }

    @Override
    public SurfArea findSurfAreaById(long id) {
        open();
        Cursor cursor = database.query(DBAdapter.TABLE_SURFAREA, new String[]{
                DBAdapter.COLUMN_ID, DBAdapter.COLUMN_NAME, DBAdapter.COLUMN_DESCRIPTION},
                DBAdapter.COLUMN_ID + " = ? ", new String[]{String.valueOf(id)}, null, null, null, null);

        SurfArea surfArea = new SurfArea();
        if (cursor != null) {
            cursor.moveToFirst();

            surfArea.setId(cursor.getInt(0));
            surfArea.setBasicInfo(cursor.getString(1), cursor.getString(2));
            cursor.close();
        }
        close();

        return surfArea;
    }

    @Override
    public void deleteSurfArea(SurfArea surfArea) {
        open();
        database.delete(DBAdapter.TABLE_SURFAREA, DBAdapter.COLUMN_ID + " = ? ",
                new String[]{String.valueOf(surfArea.getId())});
        close();
    }

    @Override
    public List<SurfArea> getSurfAreaList() {
        String selectQuery = "SELECT " + DBAdapter.COLUMN_ID + ", " + DBAdapter.COLUMN_NAME + ", " +
                DBAdapter.COLUMN_DESCRIPTION + " FROM " + DBAdapter.TABLE_SURFAREA;
        List<SurfArea> surfAreas = new ArrayList<>();
        open();
        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor.moveToFirst () ) {
            do {
                final SurfArea surfArea = new SurfArea();
                surfArea.setId(cursor.getInt(0));
                surfArea.setBasicInfo(cursor.getString(1), cursor.getString(2));
                surfAreas.add(surfArea);
            } while (cursor.moveToNext());
        }
        cursor.close();
        close();
        return surfAreas;
    }
}
