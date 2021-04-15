package org.example.domain;

import org.example.repository.IRepository;

public class MaterialValidator {

    public void validate(Material material, IRepository<Student> studentIRepository) throws Exception {
        if (material.getDescription().length()<3) {
            throw new Exception("The description must be at least 3 characters long");
        }
        Student givenStudent = studentIRepository.readOne((material.getUploaderId()));
        if (givenStudent == null) {
            throw new Exception("There is no student with the given id!");
        }
    }
}