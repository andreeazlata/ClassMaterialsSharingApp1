package org.example.domain;

public class Transaction extends Entity {
        private int materialId;
        private String dateAndHour;
        private int numberOfItems;
        private int studentId;

    public Transaction(int idEntity, int materialId, String dateAndHour, int numberOfItems, int studentId) {
        super(idEntity);
        this.materialId = materialId;
        this.dateAndHour = dateAndHour;
        this.numberOfItems = numberOfItems;
        this.studentId = studentId;
    }

    public int getMaterialId() {
        return materialId;
    }

    public String getDateAndHour() {
        return dateAndHour;
    }

    public int getNumberOfItems() {
        return numberOfItems;
    }

    public int getStudentId() {
        return studentId;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "materialId=" + materialId +
                ", dateAndHour='" + dateAndHour + '\'' +
                ", numberOfItems=" + numberOfItems +
                ", studentId=" + studentId +
                '}';
    }
}
