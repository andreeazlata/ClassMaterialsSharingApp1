package org.example.domain;


import org.example.repository.IRepository;

public class TransactionValidator {
    /**
     *
     * @param transaction the download transaction.
     * @param studentIRepository the student repository.
     * @throws Exception adding a transaction for a student id that doesn't exist or for a material that wasn't uploaded.
     */
    public void validate(Transaction transaction, IRepository<Student> studentIRepository, IRepository<Material> materialIRepository) throws Exception {
        Student givenStudent = studentIRepository.readOne((transaction.getStudentId()));
       if (givenStudent == null) {
            throw new Exception("There is no student with the given id!");
       }
        Material givenMaterial = materialIRepository.readOne((transaction.getMaterialId()));

        if (givenMaterial == null) {
            throw new Exception("There is no material with the given id!");
        }
        }
    }

