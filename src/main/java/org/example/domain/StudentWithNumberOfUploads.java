package org.example.domain;

public class StudentWithNumberOfUploads {

    private int studentId;
    private int numberOfUploads;

    public StudentWithNumberOfUploads(int studentId, int numberOfUploads) {
        this.studentId = studentId;
        this.numberOfUploads = numberOfUploads;
    }

    public int getStudentId() {
        return studentId;
    }

    public int getNumberOfUploads() {
        return numberOfUploads;
    }

    @Override
    public String toString() {
        return "StudentWithNumberOfUploads{" +
                "studentId=" + studentId +
                ", numberOfUploads=" + numberOfUploads +
                '}';
    }
}
