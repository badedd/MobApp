package com.example.eduard.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.SimpleTimeZone;

/**
 * Created by Eduard on 12.05.2017.
 */

public class DBDataSource {
    private SQLiteDatabase database;
    private DBConnection dbHelper;

    public DBDataSource(Context context) {

        dbHelper = new DBConnection(context);
    }

    public List<Todo> getAllTodos(){
        List<Todo> resultList = new ArrayList<>();
        try {
            List<String[]> allTodosAsString = dbHelper.getAllToDos();

            for(String[] indexArray : allTodosAsString) {
                //String name, String description, boolean favourite, Date expire
                String name = indexArray[0];
                String description = indexArray[1];
                boolean favourite = Boolean.parseBoolean(indexArray[2]);

                String expire = indexArray[3];
                int dbID = Integer.parseInt(indexArray[4]);



                resultList.add(new Todo(name, description, favourite, expire, dbID));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            return resultList;
        }
    }

    public void newTodo(Todo newToDo) {
        dbHelper.newToDo(newToDo);
    }

    public void deleteAllToDos(){
        dbHelper.deleteAllToDos();
    }

    public void deleteToDoByID(int toDoId){
        dbHelper.deleteToDoByID(toDoId);
    }

    public Todo getToDoByID(int toDoID) {
        Todo todo = dbHelper.getToDoByID(toDoID);
        return todo;
    }

    public void editToDo(Todo editTodo) {
        dbHelper.editToDo(editTodo);
    }
}
