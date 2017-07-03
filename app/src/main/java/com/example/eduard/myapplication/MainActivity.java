package com.example.eduard.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private DBDataSource dbDataSource;

    IDataItemCRUDOperations iDat;


     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // Todo todo = new Todo("Testname1","Testdesc",false,new Date(System.currentTimeMillis()));

         dbDataSource = new DBDataSource(this);
         //dbDataSource.deleteToDoByID(1);
         //dbDataSource.deleteAllToDos();

         //Todo newToDo = new Todo("NeuesTodo","NeueDesc3",false,false,"2017-06-17 20:40:40",0);
         //dbDataSource.newTodo(newToDo);

         TextView textView = (TextView) findViewById(R.id.FirstTextField);
         String sammlung = "";
         List<Todo> resultList = new ArrayList<>();
         resultList = dbDataSource.getAllTodos();

         for (Todo todo:resultList){
             sammlung+=todo.getName()+ " " +todo.getDescription()+ " " + " " +todo.isDone()+ " "+ todo.get_dbID()+"\n";
         }

        // Todo todo = dbDataSource.getToDoByID(1);
         // sammlung += "per ID: "+todo.getName()+ " " +todo.getDescription()+ " " + todo.get_dbID()+"\n";

         //int size = dbDataSource.getAllTodos().size();

         textView.setText(sammlung);
         HttpClientDataItemCRUDOperationsImpl bla = new HttpClientDataItemCRUDOperationsImpl();

         List<DataItem> resultList2 = bla.readAllDataItems();
         for (DataItem item:resultList2){
             System.out.println(item.getName());

         }


         //int size2 = dbDataSource.getAllTodos().size();
         //textView.setText("size1: "+size+", size2: "+size2+", CurrentTime: "+newToDo.getExpire());


/*         dbDataSource.getAllTodos();
         textView.setText(dbDataSource.getAllTodos().get(0).toString());
         Intent intentToLogIn = new Intent(this, LoginScreen.class);
         startActivity(intentToLogIn);

         */
    }

}
