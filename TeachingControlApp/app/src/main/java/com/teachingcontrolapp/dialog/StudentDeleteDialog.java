package com.teachingcontrolapp.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import com.teachingcontrolapp.data.Student;

public class StudentDeleteDialog extends DialogFragment implements DialogInterface.OnClickListener {

    private OnDeleteListener listener;
    private Student student;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (!(activity instanceof OnDeleteListener)) {
            throw new RuntimeException("The activity should implement DialogFragment.OnDeleteListener");
        }
        this.listener = (OnDeleteListener) activity;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Do you want delete the student " + student.getName() + "?");
        builder.setPositiveButton("Yes", this);
        builder.setNegativeButton("No", this);
        return builder.create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (which == DialogInterface.BUTTON_POSITIVE && listener != null) {
            listener.onDelete(student);
        }
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public interface OnDeleteListener {
        public void onDelete(Student student);
    }
}