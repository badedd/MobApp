package com.example.eduard.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Overview extends AppCompatActivity {

    DBDataSource dbDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbDataSource = new DBDataSource(this);

        setContentView(R.layout.activity_overview);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        final List<Todo> todoList = dbDataSource.getAllTodos();

        List<String> todoAsString = new ArrayList<>();

        for(Todo todo : todoList) {
            todoAsString.add(todo.toString());
        }

        OverviewItemAdapter overviewItemAdapter = new OverviewItemAdapter(this, todoList);

//         ArrayAdapter<String> itemsAdapter =
//                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, todoAsString);

        ListView overviewListview = (ListView) findViewById(R.id.overview_listview);
        overviewListview.setAdapter(overviewItemAdapter);

        final Intent intentToDetail = new Intent(this, detailitem.class);
        overviewListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intentToDetail.putExtra("todo_id", todoList.get(position).get_dbID());
                startActivity(intentToDetail);
            }
        });
    }

}
