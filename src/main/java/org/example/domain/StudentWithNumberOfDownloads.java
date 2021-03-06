package org.example.domain;

public class StudentWithNumberOfDownloads {

    private int studentId;
    private int numberOfDownloads;
    private String studentName;

    public StudentWithNumberOfDownloads(int studentId, int numberOfDownloads, String studentName) {
        this.studentId = studentId;
        this.numberOfDownloads = numberOfDownloads;
        this.studentName = studentName;
    }

    public int getStudentId() {
        return studentId;
    }

    public int getNumberOfDownloads() {
        return numberOfDownloads;
    }

    public String getStudentName() {
        return studentName;
    }

    @Override
    public String toString() {
        return "StudentWithNumberOfDownloads{" +
                "studentId=" + studentId +
                ", numberOfDownloads=" + numberOfDownloads +
                ", studentName='" + studentName + '\'' +
                '}';
    }
}
