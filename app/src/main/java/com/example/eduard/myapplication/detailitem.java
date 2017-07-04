package com.example.eduard.myapplication;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class detailitem extends Activity {

    DBDataSource dbDataSource;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.context = this.getApplicationContext();
        super.onCreate(savedInstanceState);
        dbDataSource = new DBDataSource(this);
        setContentView(R.layout.activity_detailitem);

        Intent intent = getIntent();

        Todo todo = dbDataSource.getToDoByID(intent.getIntExtra("todo_id", -1));





        EditText editName = (EditText) findViewById(R.id.detail_name_edit);
        EditText editDescription = (EditText) findViewById(R.id.detail_description_edit);
        EditText editDate = (EditText) findViewById(R.id.detail_expiredate_edit);
        EditText editTime = (EditText) findViewById(R.id.detail_expiretime_edit);
        Switch editDone = (Switch) findViewById(R.id.detail_done_edit);

        String todoDate = "";
        String todoTime = "";
        SimpleDateFormat expireStringToDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat expireDate = new SimpleDateFormat("dd.MM.yyyy");
        SimpleDateFormat expireTime = new SimpleDateFormat("HH:mm");

        try {
            Date tempDate = expireStringToDate.parse(todo.getExpire());
            todoDate = expireDate.format(tempDate);
            todoTime = expireTime.format(tempDate);
        } catch (ParseException e) { }



        editName.setText(todo.getName());
        editDescription.setText(todo.getDescription());
        editDate.setText(todoDate.toString());
        editTime.setText(todoTime.toString());
        // TODO: EditDone muss auf todo.getdone() gesetzt werden.
        editDone.setChecked(true);

        SetDate fromDate = new SetDate(editDate, this);
        SetTime fromTime = new SetTime(editTime, this);




        Button delete = (Button) findViewById(R.id.detail_delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Confirm");
                builder.setMessage("Do you really want to delete?");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

}
