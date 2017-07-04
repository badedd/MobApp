package com.example.eduard.myapplication;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by Phil on 03.07.2017.
 */

public class SetTime implements View.OnFocusChangeListener, TimePickerDialog.OnTimeSetListener {

    private EditText editText;
    private Calendar myCalendar;
    private Context context;

    public SetTime (EditText editText, Context context) {
        this.editText = editText;
        this.editText.setOnFocusChangeListener(this);
        this.myCalendar = Calendar.getInstance();
        this.context = context;
        this.editText.setInputType(InputType.TYPE_NULL);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String editTextText = "";
        if(hourOfDay < 10) {
            editTextText = "0" + hourOfDay;
        } else {
            editTextText = "" + hourOfDay;
        }
        editTextText += ":";
        if(minute < 10) {
            editTextText += "0" + minute;
        } else {
            editTextText += minute;
        }
        this.editText.setText(editTextText);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(hasFocus) {
            int hour = myCalendar.get(Calendar.HOUR_OF_DAY);
            int minute = myCalendar.get(Calendar.MINUTE);
            new TimePickerDialog(context, this, hour, minute, true).show();
        }
    }
}
