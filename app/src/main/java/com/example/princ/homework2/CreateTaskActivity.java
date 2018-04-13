package com.example.princ.homework2;

/*
  Author : Sujanth Babu Guntupalli
*/
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.KeyListener;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class CreateTaskActivity extends AppCompatActivity {

    EditText etTitle,etDate,etTime;
    KeyListener dateListener,timeListener;
    Button saveButton;
    Calendar myCalendar;
    RadioGroup radioGroup;
    RadioButton radioButton;

    String ctTitle,ctDate,ctTime,ctPriority;
    //int sHour,sMins;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);
        setTitle("  Create Task");
        ActionBar actionBar=getSupportActionBar();
        actionBar.setIcon(R.drawable.list);
        actionBar.setDisplayShowHomeEnabled(true);

        etTitle=(EditText) findViewById(R.id.etTaskTitle);
        etDate=(EditText) findViewById(R.id.etTaskDate);
        etTime=(EditText) findViewById(R.id.etTaskTime);
        saveButton=(Button) findViewById(R.id.btSave);
        radioGroup=(RadioGroup) findViewById(R.id.radioGroup);

        dateListener = etDate.getKeyListener();
        timeListener = etTime.getKeyListener();
        etDate.setKeyListener(null);
        etTime.setKeyListener(null);

        myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener(){

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "MM/dd/yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                etDate.setText(sdf.format(myCalendar.getTime()));
            }

        };
        etTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar timePick = Calendar.getInstance();
                int hour = timePick.get(Calendar.HOUR_OF_DAY);
                int minute = timePick.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(CreateTaskActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        //sHour=selectedHour;
                        //sMins=selectedMinute;
                        boolean isPM = (selectedHour >= 12);
                        etTime.setText(String.format("%02d:%02d %s", (selectedHour == 12 || selectedHour == 0) ? 12 : selectedHour % 12, selectedMinute, isPM ? "PM" : "AM"));;
                    }
                }, hour, minute,false);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
        etDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(CreateTaskActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etTitle.getText().toString().isEmpty()||etDate.getText().toString().isEmpty()||etTime.getText().toString().isEmpty()||(radioGroup.getCheckedRadioButtonId() == -1)){
                    Toast.makeText(getApplicationContext(), "Some fields are empty",
                            Toast.LENGTH_SHORT).show();
                }else{
                    ctTitle = etTitle.getText().toString();
                    ctDate = etDate.getText().toString();
                    ctTime = etTime.getText().toString();
                    //ctPriority = ((RadioButton) findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString();
                    radioButton = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
                    ctPriority = radioButton.getText().toString();
                    Task t = new Task(ctTitle, ctDate, ctTime, ctPriority);

                    if (getIntent() != null) {
                        Intent gotoVTfromCT = new Intent(CreateTaskActivity.this, MainActivity.class);
                        //Bundle b=new Bundle();
                        //b.putInt("hour",sHour);
                        //b.putInt("mins",sMins);
                        gotoVTfromCT.putExtra("finalval", t);
                        //gotoVTfromCT.putExtras(b);
                        setResult(RESULT_OK, gotoVTfromCT);
                        finish();
                    }
                }
            }
        });

    }
}
