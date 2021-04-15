package org.example.service;

import org.example.domain.*;
import org.example.repository.IRepository;
import org.example.repository.RepositoryException;

import java.util.*;

public class ServiceStudent {
    private final IRepository<Student> studentIRepository;
    private final IRepository<Transaction> transactionsRepository;
    private final StudentValidator studentValidator;
    private Student student;
    private UndoRedoManager undoRedoManager;

    public ServiceStudent(IRepository<Student> studentIRepository, IRepository<Transaction> transactionsRepository, StudentValidator studentValidator, UndoRedoManager undoRedoManager) {
        this.studentIRepository = studentIRepository;
        this.transactionsRepository = transactionsRepository;
        this.studentValidator = studentValidator;
        this.undoRedoManager = undoRedoManager;
    }

    public void addStudent(int idEntity, String name, String department, int numberOfUploads) throws Exception {
        student = new Student(idEntity,  name,  department,numberOfUploads);
        this.studentValidator.validate(student, this.studentIRepository);
        this.studentIRepository.create(student);
        undoRedoManager.addToUndo(new UndoRedoAddOperation<>(this.studentIRepository, student));
    }

    public void updateStudent(int idEntity, String name, String department,int numberOfUploads) throws Exception {
        Student student = new Student(idEntity,  name,  department, numberOfUploads);
        this.studentValidator.validate(student,this.studentIRepository);
        this.studentIRepository.update(student);
    }


    public void deleteStudent(int idMaterial) throws RepositoryException {
        this.studentIRepository.delete(idMaterial);
        undoRedoManager.addToUndo(new UndoRedoDeleteOperation<>(this.studentIRepository, student));

    }

    public List<Student> getStudentsByText(String searchText) {
        List<Student> results = new ArrayList<>();
        for (Student p : this.getAll()) {
            if (p.getName().contains(searchText) ||
                    p.getDepartment().contains(searchText) ||
                    String.valueOf(p.getIdEntity()).contains(searchText));


            {
                results.add(p);
            }
        }

        return results;
    }
    public List<StudentWithNumberOfUploads> getStudentWithNumberOfUploads() {
        Map<Integer, Integer> studentWithNumberOfUploads = new HashMap<>();
        for (Transaction t : this.transactionsRepository.read()) {
            int studentId = t.getStudentId();
            if (!studentWithNumberOfUploads.containsKey(studentId)) {
                studentWithNumberOfUploads.put(studentId, 1);
            } else {
                studentWithNumberOfUploads.put(studentId, studentWithNumberOfUploads.get(studentId) + 1);
            }
        }
        List<StudentWithNumberOfUploads> results = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : studentWithNumberOfUploads.entrySet()) {
            int studentId = entry.getKey();
            int numberOfUploads = entry.getValue();

            Student student = this.studentIRepository.readOne(studentId);
            results.add(new StudentWithNumberOfUploads(studentId, numberOfUploads));
        }
        results.sort(Comparator.comparing(StudentWithNumberOfUploads::getNumberOfUploads).reversed());
        return results;
    }

    public List<Student> getAll() {
        return this.studentIRepository.read();
    }
}
