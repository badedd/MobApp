package com.example.eduard.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Date;



public class MainActivity extends AppCompatActivity {
    private DBDataSource dbDataSource;
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Todo todo = new Todo("Testname1","Testdesc",false,new Date(System.currentTimeMillis()));

         dbDataSource = new DBDataSource(this);

         TextView textView = (TextView) findViewById(R.id.FirstTextField);
         dbDataSource.getAllTodos();
         //textView.setText(dbDataSource.getAllTodos().get(0));

    }

}
