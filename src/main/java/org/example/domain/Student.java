package org.example.domain;

public class Student extends Entity {
    private String name;
    private long studentId;

    public Student(int idEntity, String name, long studentId) {
        super(idEntity);
        this.name = name;
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public long getStudentId() {
        return studentId;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", studentId=" + studentId +
                '}';
    }
}
