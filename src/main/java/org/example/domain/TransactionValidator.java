package org.example.domain;


import org.example.repository.IRepository;

public class TransactionValidator {
    /**
     *
     * @param transaction
     * @param studentIRepository
     * @throws Exception
     */
    public void validate(Transaction transaction, IRepository<Student> studentIRepository, IRepository<Material> materialIRepository) throws Exception {
        Student givenStudent = studentIRepository.readOne((transaction.getStudentId()));
       if (givenStudent == null) {
            throw new Exception("There is no student with the given id!");
       }
        Material givenMaterial = materialIRepository.readOne((transaction.getStudentId()));

        if (givenMaterial == null) {
            throw new Exception("There is no material with the given id!");
        }
        }
    }

