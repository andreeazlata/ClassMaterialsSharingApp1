package org.example.domain;

public class Student extends Entity {
    private String name;
    private String department;
    private int numberOfUploads;

    public Student(int idEntity, String name, String department, int numberOfUploads) {
        super(idEntity);
        this.name = name;
        this.department = department;
        this.numberOfUploads = numberOfUploads;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    public int getNumberOfUploads() {
        return numberOfUploads;
    }

    public void setNumberOfUploads(int numberOfUploads) {
        this.numberOfUploads = numberOfUploads;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", department='" + department + '\'' +
                ", numberOfUploads=" + numberOfUploads +
                '}';
    }
}
