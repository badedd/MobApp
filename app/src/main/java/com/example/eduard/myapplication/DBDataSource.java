package com.example.eduard.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

/**
 * Created by Eduard on 12.05.2017.
 */

public class DBDataSource {
    private SQLiteDatabase database;
    private DBConnection dbHelper;

    public DBDataSource(Context context) {

        dbHelper = new DBConnection(context);
       // database = dbHelper.getWritableDatabase();
    }

    public List<String> getAllTodos(){

        try {
            return dbHelper.getAllToDos();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }


}
