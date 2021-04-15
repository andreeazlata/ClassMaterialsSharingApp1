package org.example.domain;

public class Transaction extends Entity {
        private int materialId;
        private String dateAndHour;
        private int studentId;

    public Transaction(int idEntity, int materialId, String dateAndHour , int studentId) {
        super(idEntity);
        this.materialId = materialId;
        this.dateAndHour = dateAndHour;
        this.studentId = studentId;
    }

    public int getMaterialId() {
        return materialId;
    }

    public String getDateAndHour() {
        return dateAndHour;
    }


    public int getStudentId() {
        return studentId;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "materialId=" + materialId +
                ", dateAndHour='" + dateAndHour + '\'' +
                ", studentId=" + studentId +
                '}';
    }
}
