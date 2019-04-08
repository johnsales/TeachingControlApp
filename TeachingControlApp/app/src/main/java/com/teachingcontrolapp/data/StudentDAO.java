package com.teachingcontrolapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.teachingcontrolapp.activity.EditStudentActivity;

import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    private static StudentDAO instance;
    private SQLiteDatabase db;

    private StudentDAO(Context context){
        DBHelperStudent dbHelperStudent = DBHelperStudent.getInstance(context);
        db = dbHelperStudent.getWritableDatabase();
    }

    public static StudentDAO getInstance(Context context){
        if(instance == null){
            instance = new StudentDAO(context.getApplicationContext());
        }
        return instance;
    }

    public List<Student> list(){

        String[] columns = {
                StudentsContract.Columns._ID,
                StudentsContract.Columns.NAME,
                StudentsContract.Columns.GRADE,
                StudentsContract.Columns.COURSE,
                StudentsContract.Columns.USER_ID,
        };

        List<Student> students = new ArrayList<>();

        try(Cursor c = db.query(
                StudentsContract.TABLE_NAME,
                columns,
                "user_id='"+ FirebaseAuth.getInstance().getCurrentUser().getUid()+"'",
                null,
                null,
                null,
                StudentsContract.Columns.NAME)
        ){
            if(c.moveToFirst()){
                do{
                    Student s = StudentDAO.fromCursor(c);
                    students.add(s);
                }while (c.moveToNext());
            }
        }

        return students;
    }

    private static Student fromCursor(Cursor cursor){
        int id = cursor.getInt(cursor.getColumnIndex(StudentsContract.Columns._ID));
        String name = cursor.getString(cursor.getColumnIndex(StudentsContract.Columns.NAME));
        double grade = cursor.getDouble(cursor.getColumnIndex(StudentsContract.Columns.GRADE));
        String course = cursor.getString(cursor.getColumnIndex(StudentsContract.Columns.COURSE));
        String userId = cursor.getString(cursor.getColumnIndex(StudentsContract.Columns.USER_ID));

        return new Student(id,name,grade,course, userId);
    }

    public void save(Student student){
        ContentValues values = new ContentValues();
        values.put(StudentsContract.Columns.NAME, student.getName());
        values.put(StudentsContract.Columns.GRADE, student.getGrade());
        values.put(StudentsContract.Columns.COURSE, student.getCourse());
        values.put(StudentsContract.Columns.USER_ID, student.getUserId());
        long id = db.insert(StudentsContract.TABLE_NAME, null,values);
        student.setId((int)id);
    }

    public void update(Student student){
        ContentValues values = new ContentValues();
        values.put(StudentsContract.Columns.NAME, student.getName());
        values.put(StudentsContract.Columns.GRADE, student.getGrade());
        values.put(StudentsContract.Columns.COURSE, student.getCourse());
        db.update(
                StudentsContract.TABLE_NAME,
                values,
                StudentsContract.Columns._ID+"=?",
                new String[]{String.valueOf(student.getId())}
        );

    }

    public void delete(Student student){
        db.delete(
                StudentsContract.TABLE_NAME,
                StudentsContract.Columns._ID+"=?",
                new String[]{String.valueOf(student.getId())}
        );

    }


}
