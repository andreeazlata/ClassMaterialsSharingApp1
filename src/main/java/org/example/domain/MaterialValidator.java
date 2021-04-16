package org.example.domain;

import org.example.repository.IRepository;

public class MaterialValidator {
    /**
     *
     * @param material the document/material/
     * @param studentIRepository the student repository.
     * @throws Exception when the description is shorter than 3 crt and when trying to add a student id that doesn't exist.
     */
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