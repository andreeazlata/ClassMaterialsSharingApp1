package org.example.service;

import org.example.domain.*;
import org.example.repository.IRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ServiceTransaction {
    private final IRepository<Transaction> repositoryTransaction;
    private final IRepository<Student> repositoryStudent;
    private final TransactionValidator transactionValidator;
    private UndoRedoManager undoRedoManager;
    private Transaction transaction;

    public ServiceTransaction(IRepository<Transaction> repositoryTransaction,
                              IRepository<Student> repositoryStudent,
                              TransactionValidator transactionValidator, UndoRedoManager undoRedoManager) {
        this.repositoryTransaction = repositoryTransaction;
        this.repositoryStudent = repositoryStudent;
        this.transactionValidator = transactionValidator;
        this.undoRedoManager = undoRedoManager;
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
        this.transactionValidator.validate(transaction, this.repositoryStudent);
        this.repositoryTransaction.create(transaction);
        this.undoRedoManager.addToUndo(new UndoRedoAddOperation<>(this.repositoryTransaction, transaction));
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
        this.transactionValidator.validate(transaction, this.repositoryStudent);
        this.repositoryTransaction.update(transaction);
    }

    /**
     * deletes a transaction by its id
     *
     * @param idEntity
     */
    public void deleteTransaction(int idEntity) {
        this.repositoryTransaction.delete(idEntity);
        this.undoRedoManager.addToUndo(new UndoRedoDeleteOperation<>(this.repositoryTransaction, transaction));
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