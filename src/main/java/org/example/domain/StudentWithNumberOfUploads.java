package org.example.domain;

public class StudentWithNumberOfUploads {

    private final int studentId;
    private final int numberOfUploads;
    private String studentName;

    public StudentWithNumberOfUploads(int studentId, int numberOfUploads, String studentName) {
        this.studentId = studentId;
        this.numberOfUploads = numberOfUploads;
        this.studentName = studentName;
    }

    public int getStudentId() {
        return studentId;
    }

    public int getNumberOfUploads() {
        return numberOfUploads;
    }

    public String getStudentName() {
        return studentName;
    }

    @Override
    public String toString() {
        return "StudentWithNumberOfUploads{" +
                "studentId=" + studentId +
                ", numberOfUploads=" + numberOfUploads +
                ", studentName='" + studentName + '\'' +
                '}';
    }
}
