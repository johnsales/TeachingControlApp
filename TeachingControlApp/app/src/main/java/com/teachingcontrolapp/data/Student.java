package com.teachingcontrolapp.data;

import com.google.firebase.auth.FirebaseAuth;

import java.io.Serializable;

public class Student implements Serializable {

    private int id;
    private String name;
    private double grade;
    private String course;
    private String userId;

    public Student(int id, String name, double grade,String course, String userId) {
        this.id = id;
        this.name = name;
        this.grade = grade;
        this.course = course;
        this.userId = userId;
    }

    public Student(String name, double grade, String course, String userId) {
        this.name = name;
        this.grade = grade;
        this.course = course;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public String getCourse() {return course; }

    public void setCourse(String course) { this.course = course; }

    public String getUserId() { return userId; }

    public void setUserId(String userId) { this.userId = userId; }
}
