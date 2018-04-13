package com.example.princ.homework2;
/*
  Author : Sujanth Babu Guntupalli
*/

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class EditTaskActivity extends AppCompatActivity {

    Button editSaveButton;
    EditText editTitle,editDate,editTime;
    RadioGroup editRadioGroup;
    RadioButton editRadioButton;
    Calendar myCalendar;

    String edTitle,edDate,edTime,edPriority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        setTitle("  Edit Task");
        ActionBar actionBar=getSupportActionBar();
        actionBar.setIcon(R.drawable.list);
        actionBar.setDisplayShowHomeEnabled(true);

        editTitle=(EditText) findViewById(R.id.etTaskTitle);
        editDate=(EditText) findViewById(R.id.etTaskDate);
        editTime=(EditText) findViewById(R.id.etTaskTime);

        editSaveButton=(Button) findViewById(R.id.btSave);
        editRadioGroup=(RadioGroup) findViewById(R.id.radioGroup);

        if(getIntent()!=null && getIntent().getExtras()!=null){
            if(getIntent().getExtras().containsKey("editTask")){
                Task eTask=(Task) getIntent().getExtras().getSerializable("editTask");
                editTitle.setText(eTask.title);
                editDate.setText(eTask.date);
                editTime.setText(eTask.time);
                if(eTask.priority.equals("High")){
                    editRadioGroup.check(R.id.rbHigh);
                }else if(eTask.priority.equals("Medium")){
                    editRadioGroup.check(R.id.rbMedium);
                }else{
                    editRadioGroup.check(R.id.rbLow);
                }

            }
        }

        editSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editTitle.getText().toString().isEmpty()||editDate.getText().toString().isEmpty()||editTime.getText().toString().isEmpty()||(editRadioGroup.getCheckedRadioButtonId() == -1)){
                    Toast.makeText(getApplicationContext(), "Some fields are empty",
                            Toast.LENGTH_SHORT).show();
                }else{
                    edTitle = editTitle.getText().toString();
                    edDate = editDate.getText().toString();
                    edTime = editTime.getText().toString();
                    //ctPriority = ((RadioButton) findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString();
                    editRadioButton = (RadioButton) findViewById(editRadioGroup.getCheckedRadioButtonId());
                    edPriority = editRadioButton.getText().toString();
                    Task t = new Task(edTitle, edDate, edTime, edPriority);

                    if (getIntent() != null) {
                        Intent gotoVTfromET = new Intent(EditTaskActivity.this, MainActivity.class);
                        gotoVTfromET.putExtra("finalval", t);
                        setResult(RESULT_OK, gotoVTfromET);
                        finish();
                    }
                }
            }
        });

        editTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar timePick = Calendar.getInstance();
                int hour = timePick.get(Calendar.HOUR_OF_DAY);
                int minute = timePick.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(EditTaskActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        boolean isPM = (selectedHour >= 12);
                        editTime.setText(String.format("%02d:%02d %s", (selectedHour == 12 || selectedHour == 0) ? 12 : selectedHour % 12, selectedMinute, isPM ? "PM" : "AM"));;
                    }
                }, hour, minute,false);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

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
                editDate.setText(sdf.format(myCalendar.getTime()));
            }

        };
        editDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(EditTaskActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }
}
