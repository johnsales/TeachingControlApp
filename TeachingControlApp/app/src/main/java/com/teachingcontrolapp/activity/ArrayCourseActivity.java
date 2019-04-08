package com.teachingcontrolapp.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.teachingcontrolapp.R;
import com.teachingcontrolapp.data.Student;
import com.teachingcontrolapp.data.StudentDAO;

import java.util.List;

public class ArrayCourseActivity extends Activity {
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_array_course);
        listView = findViewById(R.id.lstView);

        // Create an ArrayAdapter from List For Set Color
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.course_arrays)){
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                // Get the Item from ListView
                View view = super.getView(position, convertView, parent);
                // Initialize a TextView for ListView each Item
                TextView tv = (TextView) view.findViewById(android.R.id.text1);
                // Set the text color of TextView (ListView Item)
                tv.setTextColor(Color.BLACK);
                // Generate ListView Item using TextView
                return view;
            }
        };
        // DataBind ListView with items from ArrayAdapter
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(getApplicationContext(), "Press long click to see informations",
                        Toast.LENGTH_SHORT).show();
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {

                int counterStudentCourse = 0;
                double avarageStudentCourse = 0.0;
                int approvedStudents = 0;
                int disapprovedStudents = 0;
                List<Student> students = StudentDAO.getInstance(getApplicationContext()).list();
                for (Student t: students) {
                    if(t.getCourse().equals(listView.getItemAtPosition(position).toString())){
                        counterStudentCourse++;
                        avarageStudentCourse += t.getGrade();

                        if(t.getGrade() >= 7.0)
                            approvedStudents++;
                        else
                            disapprovedStudents++;
                    }
                }

                if(counterStudentCourse != 0)
                avarageStudentCourse /= counterStudentCourse;

                AlertDialog alertDialog = new AlertDialog.Builder(view.getContext()).create();
                alertDialog.setTitle(listView.getItemAtPosition(position).toString()+" course situation");
                alertDialog.setMessage("Students registred: "+ counterStudentCourse+
                                       "\nAverage grade: "+avarageStudentCourse+
                                        "\nNumber of approved: "+approvedStudents+
                                        "\nNumber of disapproved: "+disapprovedStudents
                );
                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // here you can add functions
                    }
                });
                alertDialog.setIcon(R.drawable.goal);
                alertDialog.show();


                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary))); // set your desired color
        return super.onCreateOptionsMenu(menu);
    }

}

