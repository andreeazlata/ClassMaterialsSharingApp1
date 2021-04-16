package org.example.domain;

public class Student extends Entity {
    private final String name;
    private final String department;


    public Student(int idEntity, String name, String department) {
        super(idEntity);
        this.name = name;
        this.department = department;

    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }


    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", department='" + department + '\'' +
                '}';
    }
}
