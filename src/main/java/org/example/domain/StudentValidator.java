package org.example.domain;

import org.example.repository.IRepository;

public class StudentValidator {

    public void validate(Student student, IRepository<Student> studentIRepository) throws Exception {
        if (student.getName()==null) {
            throw new Exception("The student must have a name");
        }
    }
}
