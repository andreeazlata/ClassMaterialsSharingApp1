package org.example.service;

import org.example.domain.Material;
import org.example.domain.Transaction;
import org.example.domain.TransactionValidator;
import org.example.repository.IRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ServiceTransaction {
    private final IRepository<Transaction> repositoryTransaction;
    private final IRepository<Material> repositoryMaterial;
    private final TransactionValidator transactionValidator;

    public ServiceTransaction(IRepository<Transaction> repositoryTransaction,
                              IRepository<Material> repositoryMaterial,
                              TransactionValidator transactionValidator, UndoRedoManager undoRedoManager) {
        this.repositoryTransaction = repositoryTransaction;
        this.repositoryMaterial = repositoryMaterial;
        this.transactionValidator = transactionValidator;
    }

    /**
     *
     * @param idEntity
     * @param materialId
     * @param dateAndHour
     * @param numberOfItems
     * @param studentId
     * @throws Exception
     */
    public void addTransaction(int idEntity, int materialId, String dateAndHour, int numberOfItems, int studentId) throws Exception {
        Transaction transaction = new Transaction(idEntity, materialId, dateAndHour, numberOfItems, studentId);
        this.transactionValidator.validate(transaction, this.repositoryMaterial);
        this.repositoryTransaction.create(transaction);
    }

    /**
     *
     * @param idEntity
     * @param materialId
     * @param dateAndHour
     * @param numberOfItems
     * @param studentId
     * @throws Exception
     */
    public void updateTransaction(int idEntity, int materialId, String dateAndHour, int numberOfItems, int studentId) throws Exception {
        Transaction transaction = new Transaction(idEntity, materialId, dateAndHour, numberOfItems, studentId);
        this.transactionValidator.validate(transaction, this.repositoryMaterial);
        this.repositoryTransaction.update(transaction);
    }

    /**
     * deletes a transaction by its id
     *
     * @param idEntity
     */
    public void deleteTransaction(int idEntity) {
        this.repositoryTransaction.delete(idEntity);
    }

    /**
     * @return a list of all the transactions
     */
    public List<Transaction> getAll() {
        return this.repositoryTransaction.read();
    }




public List<Transaction> getTransactionsByText(String searchText) {
        List<Transaction> results = new ArrayList<>();
        for (Transaction t : this.getAll()) {
        if (String.valueOf(t.getStudentId()).contains(searchText) ||
        t.getDateAndHour().contains(searchText) ||
        String.valueOf(t.getNumberOfItems()).contains(searchText)) {
        results.add(t);
        }
        }

        return results;
        }

public List<Transaction> getBetweenTwoDateAndTimes(LocalDateTime start, LocalDateTime end) {
        List<Transaction> results = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        for (Transaction t: this.getAll()) {
        String stringDate = t.getDateAndHour();
        LocalDateTime typedDate = LocalDateTime.parse(stringDate, formatter);
        if (start.isBefore(typedDate) && typedDate.isBefore(end)) {
        results.add(t);
        }
        }

        return results;
        }
}