package com.example.princ.homework2;

/*
  Author : Sujanth Babu Guntupalli
*/

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    ImageButton ctButton, etButton, ftButton, ltButton, ptButton, ntButton, dtButton;
    TextView viewTitle, viewDate, viewTime, viewPriority, viewTaskNo;
    Task nt;
    LinkedList<Task> taskList = new LinkedList<Task>();

    int taskId = -1;

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 1) {
            nt = (Task) data.getSerializableExtra("finalval");
            taskList.add(nt);
            //lListsort(taskList);
            //taskId=taskList.indexOf(nt);
            taskId = taskList.size() - 1;
            Log.d("taskId", "" + taskId);
            int currentTask = taskId + 1;
            viewTitle.setText(taskList.get(taskId).title);
            viewDate.setText(taskList.get(taskId).date);
            viewTime.setText(taskList.get(taskId).time);
            viewPriority.setText(taskList.get(taskId).priority);
            viewTaskNo.setText("Task " + currentTask + " of " + currentTask);
        }

        if (resultCode == RESULT_OK && requestCode == 2) {
            nt = (Task) data.getSerializableExtra("finalval");
            taskList.add(taskId, nt);
            taskList.remove(taskId + 1);
            lListsort(taskList);
            taskId = taskList.indexOf(nt);
            int currentTask = taskId + 1;
            viewTitle.setText(taskList.get(taskId).title);
            viewDate.setText(taskList.get(taskId).date);
            viewTime.setText(taskList.get(taskId).time);
            viewPriority.setText(taskList.get(taskId).priority);
            viewTaskNo.setText("Task " + currentTask + " of " + taskList.size());
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("  View Tasks");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.drawable.list);
        actionBar.setDisplayShowHomeEnabled(true);

        ctButton = (ImageButton) findViewById(R.id.createTaskButton);
        etButton = (ImageButton) findViewById(R.id.editTaskButton);
        ftButton = (ImageButton) findViewById(R.id.firstTaskButton);
        ltButton = (ImageButton) findViewById(R.id.lastTaskButton);
        ptButton = (ImageButton) findViewById(R.id.previousTaskButton);
        ntButton = (ImageButton) findViewById(R.id.nextTaskButton);
        dtButton = (ImageButton) findViewById(R.id.deleteCurrTaskButton);

        viewTitle = (TextView) findViewById(R.id.tvTaskTitle);
        viewDate = (TextView) findViewById(R.id.tvTaskDate);
        viewTime = (TextView) findViewById(R.id.tvTaskTime);
        viewPriority = (TextView) findViewById(R.id.tvTaskPriority);
        viewTaskNo = (TextView) findViewById(R.id.tvTaskNo);

        Task t1 = new Task("FirstTask", "12/01/2018", "8:00PM", "High");
        taskList.add(t1);
        taskId += 1;
        Task ft = taskList.get(taskId);
        viewTitle.setText(ft.title);
        viewDate.setText(ft.date);
        viewTime.setText(ft.time);
        viewPriority.setText(ft.priority);
        viewTaskNo.setText("Task " + 1 + " of " + taskList.size());

        ctButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //dtButton.setEnabled(true);
                Intent intent = new Intent(MainActivity.this, CreateTaskActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        etButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (taskList != null && !taskList.isEmpty()) {
                    String etTitle = viewTitle.getText().toString();
                    String etDate = viewDate.getText().toString();
                    String etTime = viewTime.getText().toString();
                    String etPriority = viewPriority.getText().toString();
                    Task et = new Task(etTitle, etDate, etTime, etPriority);
                    Intent intent = new Intent(MainActivity.this, EditTaskActivity.class);
                    intent.putExtra("editTask", et);
                    startActivityForResult(intent, 2);
                } else {
                    Toast.makeText(getApplicationContext(), "Task List is empty",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        ftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    taskId = 0;
                    Task ft = taskList.get(taskId);
                    viewTitle.setText(ft.title);
                    viewDate.setText(ft.date);
                    viewTime.setText(ft.time);
                    viewPriority.setText(ft.priority);
                    viewTaskNo.setText("Task " + 1 + " of " + taskList.size());
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Task List is empty",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        ltButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    taskId = taskList.size() - 1;
                    int last = taskList.size();
                    Task ft = taskList.get(last - 1);
                    viewTitle.setText(ft.title);
                    viewDate.setText(ft.date);
                    viewTime.setText(ft.time);
                    viewPriority.setText(ft.priority);
                    viewTaskNo.setText("Task " + last + " of " + last);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Task List is empty",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        ptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (taskList == null || taskList.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Task List is empty",
                            Toast.LENGTH_SHORT).show();
                    Log.d("taskId", "" + taskId);
                } else if ((taskId - 1) < 0) {
                    Toast.makeText(getApplicationContext(), "This is the first task",
                            Toast.LENGTH_SHORT).show();
                } else {
                    taskId -= 1;
                    int currentTask = taskId + 1;
                    Task ft = taskList.get(taskId);
                    viewTitle.setText(ft.title);
                    viewDate.setText(ft.date);
                    viewTime.setText(ft.time);
                    viewPriority.setText(ft.priority);
                    viewTaskNo.setText("Task " + currentTask + " of " + taskList.size());
                }
            }
        });

        ntButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (taskList == null || taskList.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Task List is empty",
                            Toast.LENGTH_SHORT).show();
                    Log.d("taskId", "" + taskId);
                } else if ((taskId + 1) > (taskList.size() - 1)) {
                    Toast.makeText(getApplicationContext(), "This is the last task",
                            Toast.LENGTH_SHORT).show();
                } else {
                    taskId += 1;
                    int currentTask = taskId + 1;
                    Task ft = taskList.get(taskId);
                    viewTitle.setText(ft.title);
                    viewDate.setText(ft.date);
                    viewTime.setText(ft.time);
                    viewPriority.setText(ft.priority);
                    viewTaskNo.setText("Task " + currentTask + " of " + taskList.size());
                }
            }
        });
        dtButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Log.d("delete", "" + taskId);
                    taskList.remove(taskId);
                    if (taskList != null && !taskList.isEmpty()) {
                        Log.d("delete", "" + taskId);
                        taskId = 0;
                        Task ft = taskList.get(taskId);
                        viewTitle.setText(ft.title);
                        viewDate.setText(ft.date);
                        viewTime.setText(ft.time);
                        viewPriority.setText(ft.priority);
                        viewTaskNo.setText("Task " + 1 + " of " + taskList.size());
                    } else {
                        viewTitle.setText("Task Title");
                        viewDate.setText("Task Date");
                        viewTime.setText("Task Time");
                        viewPriority.setText("Task Priority");
                        viewTaskNo.setText("Task " + 0 + " of " + 0);
                        taskId = -1;
                        Log.d("taskId", "" + taskId);
                        //dtButton.setEnabled(false);
                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Task List is empty",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void lListsort(LinkedList l) {
        Collections.sort(l, new Comparator<Task>() {
            @Override
            public int compare(Task t1, Task t2) {
                Date d1 = null, d2 = null;
                Date tm1 = null, tm2 = null;
                String[] times;
                times = t1.getTime().split(" ");
                String time1 = times[0];
                if (times[1].equals("PM")) {
                    String[] tsTime;
                    tsTime = time1.split(":");
                    int sHour = Integer.parseInt(tsTime[0]);
                    if (sHour != 12) {
                        sHour = sHour + 12;
                    } else {
                        sHour = 0;
                    }
                    time1 = sHour + ":" + tsTime[1];
                }
                times = t2.getTime().split(" ");
                String time2 = times[0];
                if (times[1].equals("PM")) {
                    String[] tsTime;
                    tsTime = time2.split(":");
                    int sHour = Integer.parseInt(tsTime[0]);
                    if (sHour != 12) {
                        sHour = sHour + 12;
                    } else {
                        sHour = 0;
                    }
                    time2 = sHour + ":" + tsTime[1];
                }
                try {
                    d1 = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH).parse(t1.getDate());
                    d2 = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH).parse(t2.getDate());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                int x = d1.compareTo(d2);

                if (x != 0) {
                    return x;
                } else {
                    try {
                        tm1 = new SimpleDateFormat("HH:mm", Locale.ENGLISH).parse(time1);
                        tm1 = new SimpleDateFormat("HH:mm", Locale.ENGLISH).parse(time2);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return tm1.compareTo(tm2);
                }
            }
        });
    }
}
//android:digits="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"