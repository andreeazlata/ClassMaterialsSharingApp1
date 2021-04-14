package org.example.domain;


import org.example.repository.IRepository;

public class TransactionValidator {
    /**
     *
     * @param transaction
     * @param materialIRepository
     * @throws Exception
     */
    public void validate(Transaction transaction, IRepository<Material> materialIRepository) throws Exception {
        Material givenMaterial = materialIRepository.readOne((transaction.getMaterialId()));
       if (givenMaterial == null) {
            throw new Exception("There is no material with the given id!");
       }
        if(transaction.getStudentId()!=Integer.parseInt(String.valueOf(transaction.getStudentId()))){
            throw new Exception("Client card number is not an integer");
        }
        }
    }

