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
    private final IRepository<Material> materialIRepository;
    private final TransactionValidator transactionValidator;
    private final UndoRedoManager undoRedoManager;
    private Transaction transaction;

    public ServiceTransaction(IRepository<Transaction> repositoryTransaction,
                              IRepository<Student> repositoryStudent,
                              IRepository<Material> materialIRepository,
                              TransactionValidator transactionValidator, UndoRedoManager undoRedoManager) {
        this.repositoryTransaction = repositoryTransaction;
        this.repositoryStudent = repositoryStudent;
        this.transactionValidator = transactionValidator;
        this.undoRedoManager = undoRedoManager;
        this.materialIRepository = materialIRepository;
    }

    /**
     * @param idEntity    the id of the transaction.
     * @param materialId  the id of the material.
     * @param dateAndHour the date and hour of the transaction.
     * @param studentId   the id of the student.
     * @throws Exception
     */
    public void addTransaction(int idEntity, int materialId, String dateAndHour, int studentId) throws Exception {
        Transaction transaction = new Transaction(idEntity, materialId, dateAndHour, studentId);
        this.transactionValidator.validate(transaction, this.repositoryStudent, this.materialIRepository);
        this.repositoryTransaction.create(transaction);
        this.undoRedoManager.addToUndo(new UndoRedoAddOperation<>(this.repositoryTransaction, transaction));
    }

    /**
     * @param idEntity    the id of the transaction.
     * @param materialId  the id of the material.
     * @param dateAndHour the date and hour of the transaction.
     * @param studentId   the id of the student.
     * @throws Exception
     */
    public void updateTransaction(int idEntity, int materialId, String dateAndHour, int studentId) throws Exception {
        Transaction transaction = new Transaction(idEntity, materialId, dateAndHour, studentId);
        this.transactionValidator.validate(transaction, this.repositoryStudent, this.materialIRepository);
        this.repositoryTransaction.update(transaction);
    }

    /**
     * deletes a transaction by its id
     *
     * @param idEntity the id of the transaction.
     */
    public void deleteTransaction(int idEntity) {
        Transaction transaction = this.repositoryTransaction.readOne(idEntity);
        this.repositoryTransaction.delete(idEntity);
        this.undoRedoManager.addToUndo(new UndoRedoDeleteOperation<>(this.repositoryTransaction, transaction));
    }

    /**
     * @return a list of all the transactions.
     */
    public List<Transaction> getAll() {
        return this.repositoryTransaction.read();
    }


    public List<Transaction> getTransactionsByText(String searchText) {
        List<Transaction> results = new ArrayList<>();
        for (Transaction t : this.getAll()) {
            if (String.valueOf(t.getStudentId()).contains(searchText) ||
                    String.valueOf(t.getMaterialId()).contains(searchText) ||
                    t.getDateAndHour().contains(searchText)) {
                results.add(t);
            }
        }

        return results;
    }

    /**
     * @param start the start date of the search.
     * @param end   the end date of hte search.
     * @return returns a list of all the transaction from the given interval.
     */
    public List<Transaction> getBetweenTwoDateAndTimes(LocalDateTime start, LocalDateTime end) {
        List<Transaction> results = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        for (Transaction t : this.getAll()) {
            String stringDate = t.getDateAndHour();
            LocalDateTime typedDate = LocalDateTime.parse(stringDate, formatter);
            if (start.isBefore(typedDate) && typedDate.isBefore(end)) {
                results.add(t);
            }
        }

        return results;
    }
}