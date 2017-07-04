package com.example.eduard.myapplication;

import android.app.DatePickerDialog;
import android.content.Context;
import android.text.InputType;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

/**
 * Created by Phil on 02.07.2017.
 */

public class SetDate implements View.OnFocusChangeListener, DatePickerDialog.OnDateSetListener {

    private EditText editText;
    private Calendar myCalendar;
    private Context context;

    public SetDate(EditText editText, Context context) {
        this.editText = editText;
        this.editText.setOnFocusChangeListener(this);
        this.myCalendar = Calendar.getInstance();
        this.context = context;
        this.editText.setInputType(InputType.TYPE_NULL);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String editTextText = "";

        if(dayOfMonth < 10) {
            editTextText = "0" + dayOfMonth;
        } else {
            editTextText = "" + dayOfMonth;
        }

        if(month < 10) {
            editTextText += ".0" + month;
        } else {
            editTextText += "." + month;
        }
        editTextText += "." + year;
        this.editText.setText(editTextText);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(hasFocus) {
            int day = myCalendar.get(Calendar.DAY_OF_MONTH);
            int month = myCalendar.get(Calendar.MONTH);
            int year = myCalendar.get(Calendar.YEAR);
            new DatePickerDialog(context, this, year, month, day).show();
        }
    }
}
