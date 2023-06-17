package com.example.menuexample;

import java.io.Serializable;


class Student implements Serializable {
    private String id;
    private String name;
    private String surname;
    private String department;

    public Student(String id, String name, String surname, String program) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.department = program;
    }

    @Override
    public String toString() {
        return id + " | " + name + " " + surname + " | " + department;
    }

    // getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
