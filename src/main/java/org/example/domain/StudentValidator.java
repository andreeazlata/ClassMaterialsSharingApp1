package org.example.domain;

public class StudentValidator {

    public void validate(Student student, IRepository<Student> medicineIRepository) throws Exception {
        if (student.getName()==null) {
            throw new Exception("The student must have a name");
        }
        Student givenStudent = medicineIRepository.readOne((transaction.getMedicineId()));
        if (givenStudent == null) {
            throw new Exception("There is no student with the given id!");
        }
    }
}
