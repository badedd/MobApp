package com.example.eduard.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Switch;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Phil on 02.07.2017.
 */

public class OverviewItemAdapter extends ArrayAdapter<Todo> {

    Context context;

    public OverviewItemAdapter(Context context, List<Todo> todoArrayList) {
        super(context, 0, todoArrayList);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        final Todo todo = getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listview_item, parent, false);
        }

        //Declare textview and switch-element
        TextView textView = (TextView) convertView.findViewById(R.id.item_textview);
        Switch item_switch = (Switch) convertView.findViewById(R.id.item_switch);

        //Filling textview element with name, expire and favourite from todo
        String germanDate;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

        try {
            Date tempDate = sdf.parse(todo.getExpire());
            germanDate = sdf2.format(tempDate);
        } catch (ParseException e) {
            germanDate = todo.getExpire();
        }

        textView.setText(todo.getName() + "\n" + germanDate + "\n");
        if(todo.isFavourite()) {
            textView.setText(textView.getText()+"favourite");
        }

        //Filling switch with done-attribute from todo
        //TODO: Switch auf Todo.getDone setzen
        item_switch.setChecked(true);

        //Adding onClickListener
        final Intent intentToDetail = new Intent(context, detailitem.class);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentToDetail.putExtra("todo_id", todo.get_dbID());
                context.startActivity(intentToDetail);
            }
        });

        return convertView;
    }
}
