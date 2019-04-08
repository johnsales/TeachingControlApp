package com.teachingcontrolapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.teachingcontrolapp.accountActivity.LoginActivity;
import com.teachingcontrolapp.accountActivity.UserActivity;
import com.teachingcontrolapp.activity.ListStudentsActivity;
import com.teachingcontrolapp.activity.ArrayCourseActivity;
import com.teachingcontrolapp.data.StudentDAO;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private TextView txt_fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();

        //CourseDAO courseDAO = CourseDAO.getInstance(getApplicationContext());
        StudentDAO studentDAO = StudentDAO.getInstance(getApplicationContext());

        txt_fragment = findViewById(R.id.txt_fragment);
        txt_fragment.setText("Courses registered: "+getApplicationContext().getResources().getStringArray(R.array.course_arrays).length+
                             "\nStudents registered:"+studentDAO.list().size()
                            );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actions,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.userIcon:
                startActivity(new Intent(this, UserActivity.class));
                return true;
            case R.id.signout_id:
                auth.signOut();
                startActivity(new Intent(this, LoginActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void manageStudents(View view) {
        startActivity(new Intent(this, ListStudentsActivity.class));
    }

    public void courseInformations(View view) {
        startActivity(new Intent(this, ArrayCourseActivity.class));

    }
}
