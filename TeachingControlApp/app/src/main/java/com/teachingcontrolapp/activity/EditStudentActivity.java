package com.teachingcontrolapp.activity;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.text.InputFilter;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.teachingcontrolapp.R;
import com.teachingcontrolapp.data.Student;
import com.teachingcontrolapp.data.StudentDAO;
import com.teachingcontrolapp.utils.InputFilterMinMax;

import java.util.List;

public class EditStudentActivity extends Activity {

    private StudentDAO studentDAO;
    private EditText edtName;
    private EditText edtGrade;
    private Spinner spinnerCourse;
    private Student student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_student);

        edtName = findViewById(R.id.edt_name);
        edtGrade = findViewById(R.id.edt_grade);
        InputFilter limitFilter = new InputFilterMinMax(0.0, 10.0);
        edtGrade.setFilters(new InputFilter[] { limitFilter });
        spinnerCourse = findViewById(R.id.course_spinner);

        studentDAO = StudentDAO.getInstance(this);
        student = (Student) getIntent().getSerializableExtra("student");

        if (student != null) {
            edtName.setText(student.getName());
            edtGrade.setText(String.valueOf(student.getGrade()));
            //Set Value of Spinner
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.course_arrays, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerCourse.setAdapter(adapter);
            if (student.getCourse() != null) {
                int spinnerPosition = adapter.getPosition(student.getCourse());
                spinnerCourse.setSelection(spinnerPosition);
            }

        }
    }

    public void cancel(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }

    public void process(View view) {
        String name = edtName.getText().toString();
        double grade = Double.parseDouble(edtGrade.getText().toString());
        String course = spinnerCourse.getAdapter().getItem(spinnerCourse.getSelectedItemPosition()).toString();
        String user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String msg;

        if (student == null) {
            Student student = new Student(name, grade,course, user_id);

            int sizeClass = 0;
            List<Student> students = StudentDAO.getInstance(getApplicationContext()).list();
            for (Student t: students) {
                if(t.getCourse().equals(course)){
                    sizeClass++;
                }
            }
            if(sizeClass<10){
                studentDAO.save(student);
                msg = "Student "+student.getName()+" saved with ID = " + student.getId();
            }else{
                msg = "The class "+course+ " is already full";
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            }

        } else {
            student.setName(name);
            student.setGrade(grade);
            student.setCourse(course);
            studentDAO.update(student);
            msg = "Student updated with ID = " + student.getId();
        }

        notify(msg);
        setResult(RESULT_OK);
        finish();
    }


    public void notify(String message){
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("default",
                    "YOUR_CHANNEL_NAME",
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("YOUR_NOTIFICATION_CHANNEL_DISCRIPTION");
            mNotificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), "default")
                .setSmallIcon(R.mipmap.confirmation) // notification icon
                .setContentTitle("TeachingControlApp") // title for notification
                .setContentText(message)// message for notification
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI) // set alarm sound for notification
                .setAutoCancel(true); // clear notification after click
        Intent intent = new Intent(getApplicationContext(), ListStudentsActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pi);
        mNotificationManager.notify(0, mBuilder.build());
    }

}
