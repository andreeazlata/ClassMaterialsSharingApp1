package org.example.domain;

import org.example.repository.IRepository;

public class StudentValidator {
    /**
     *
     * @param student the student entity.
     * @param studentIRepository the student repository.
     * @throws Exception when trying to add a student without a name.
     */
    public void validate(Student student, IRepository<Student> studentIRepository) throws Exception {
        if (student.getName()==null) {
            throw new Exception("The student must have a name");
        }

    }
}
