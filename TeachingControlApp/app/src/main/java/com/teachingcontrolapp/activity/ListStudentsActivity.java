package com.teachingcontrolapp.activity;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

import java.util.List;

import com.teachingcontrolapp.R;
import com.teachingcontrolapp.adapter.StudentsAdapter;
import com.teachingcontrolapp.data.Student;
import com.teachingcontrolapp.data.StudentDAO;
import com.teachingcontrolapp.dialog.StudentDeleteDialog;

public class ListStudentsActivity extends ListActivity implements OnItemLongClickListener, StudentDeleteDialog.OnDeleteListener {

    private static final int REQ_EDIT = 100;

    private StudentDAO studentDAO;
    private StudentsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_list_students);

        adapter = new StudentsAdapter(this);
        setListAdapter(adapter);

        getListView().setOnItemLongClickListener(this);


        studentDAO = StudentDAO.getInstance(this);

        updateList();
    }

    private void updateList() {
        List<Student> students = studentDAO.list();
        adapter.setItems(students);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_list_students, menu);
        getActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary))); // set your desired color
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add) {
            Intent intent = new Intent(getApplicationContext(), EditStudentActivity.class);
            startActivityForResult(intent, REQ_EDIT);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQ_EDIT && resultCode == RESULT_OK) {
            updateList();
        }
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent = new Intent(getApplicationContext(), EditStudentActivity.class);
        intent.putExtra("student", adapter.getItem(position));
        startActivityForResult(intent, REQ_EDIT);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
        Student  student = adapter.getItem(position);

        StudentDeleteDialog dialog = new StudentDeleteDialog();
        dialog.setStudent(student);
        dialog.show(getFragmentManager(), "deleteDialog");
        return true;
    }

    @Override
    public void onDelete(Student student) {
        studentDAO.delete(student);
        updateList();
    }
}
