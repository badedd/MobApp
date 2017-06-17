package com.example.eduard.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.SimpleTimeZone;

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

    public List<Todo> getAllTodos(){

        try {
            List<String[]> allTodosAsString = dbHelper.getAllToDos();
            List<Todo> resultList = new ArrayList<>();
            for(String[] indexArray : allTodosAsString) {
                //String name, String description, boolean favourite, Date expire
                String name = indexArray[1];
                String description = indexArray[2];
                boolean favourite = Boolean.parseBoolean(indexArray[3]);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss");
                Date expire = sdf.parse(indexArray[4]);
                resultList.add(new Todo(name, description, favourite, expire));
            }
            return resultList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }


}
