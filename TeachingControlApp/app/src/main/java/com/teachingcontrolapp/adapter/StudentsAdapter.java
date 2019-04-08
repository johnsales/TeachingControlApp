package com.teachingcontrolapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.teachingcontrolapp.R;
import com.teachingcontrolapp.data.Student;

public class StudentsAdapter extends BaseAdapter {

    private Context context;
    private List<Student> students = new ArrayList<>();

    public StudentsAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return students.size();
    }

    @Override
    public Student getItem(int position) {
        return students.get(position);
    }

    @Override
    public long getItemId(int position) {
        return students.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.adapter_list_students, parent, false);
            holder = new ViewHolder();
            holder.txtName = view.findViewById(R.id.txt_name);
            holder.txtGrade = view.findViewById(R.id.txt_grade);
            holder.txtCourse = view.findViewById(R.id.txt_course);
            view.setTag(holder);

        } else {
            holder = (ViewHolder) view.getTag();
        }

        Student student = students.get(position);

        holder.txtName.setText(student.getName());
        holder.txtGrade.setText(String.valueOf(student.getGrade()));
        holder.txtCourse.setText(String.valueOf(student.getCourse()));

        return view;
    }

    public void setItems(List<Student> students) {
        this.students = students;
        notifyDataSetChanged();
    }

    private static class ViewHolder {
        public TextView txtName;
        public TextView txtGrade;
        public TextView txtCourse;
    }
}
