package org.example.service;

import org.example.domain.*;
import org.example.repository.IRepository;
import org.example.repository.RepositoryException;

import java.util.*;

public class ServiceStudent {
    private final IRepository<Student> studentIRepository;
    private final IRepository<Transaction> transactionsRepository;
    private final IRepository<Material> materialIRepository;
    private final StudentValidator studentValidator;
    private Student student;
    private UndoRedoManager undoRedoManager;

    public ServiceStudent(IRepository<Student> studentIRepository,
                          IRepository<Transaction> transactionsRepository,
                          IRepository<Material> materialIRepository,
                          StudentValidator studentValidator,
                          UndoRedoManager undoRedoManager) {

        this.studentIRepository = studentIRepository;
        this.transactionsRepository = transactionsRepository;
        this.materialIRepository = materialIRepository;
        this.studentValidator = studentValidator;
        this.undoRedoManager = undoRedoManager;
    }

    /**
     *
     * @param idEntity the id of the student.
     * @param name the name of the student.
     * @param department the department the student is a part of.
     * @throws Exception
     */
    public void addStudent(int idEntity, String name, String department) throws Exception {
        Student student = new Student(idEntity,  name,  department);
        this.studentValidator.validate(student, this.studentIRepository);
        this.studentIRepository.create(student);
        undoRedoManager.addToUndo(new UndoRedoAddOperation<>(this.studentIRepository, student));
    }

    /**
     *
     * @param idEntity the id of the student.
     * @param name the name of the student.
     * @param department the department the student is a part of.
     * @throws Exception
     */
    public void updateStudent(int idEntity, String name, String department) throws Exception {
        Student student = new Student(idEntity,  name,  department);
        this.studentValidator.validate(student,this.studentIRepository);
        this.studentIRepository.update(student);
    }

    /**
     *
     * @param idStudent the id of the student.
     * @throws RepositoryException
     */
    public void deleteStudent(int idStudent) throws RepositoryException {
        Student student = this.studentIRepository.readOne(idStudent);
        this.studentIRepository.delete(idStudent);
        undoRedoManager.addToUndo(new UndoRedoDeleteOperation<>(this.studentIRepository, student));

    }

    /**
     *
     * @param searchText
     * @return returns the students that contain the searched text.
     */
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

    /**
     *
     * @return returns a list of all the student ids and the number of files they downloaded.
     */
    public List<StudentWithNumberOfDownloads> getStudentWithNumberOfDownloads() {
        Map<Integer, Integer> studentWithNumberOfDownloads = new HashMap<>();
        for (Transaction t : this.transactionsRepository.read()) {
            int studentId = t.getStudentId();
            if (!studentWithNumberOfDownloads.containsKey(studentId)) {
                studentWithNumberOfDownloads.put(studentId, 1);
            } else {
                studentWithNumberOfDownloads.put(studentId, studentWithNumberOfDownloads.get(studentId) + 1);
            }
        }
        List<StudentWithNumberOfDownloads> results = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : studentWithNumberOfDownloads.entrySet()) {
            int studentId = entry.getKey();
            int numberOfDownloads = entry.getValue();

            Student student = this.studentIRepository.readOne(studentId);
            if(student==null){
                results.add(new StudentWithNumberOfDownloads(studentId, numberOfDownloads, "UNKNOWN"));
            }else{
            results.add(new StudentWithNumberOfDownloads(studentId, numberOfDownloads, student.getName()));}
        }
        results.sort(Comparator.comparing(StudentWithNumberOfDownloads::getNumberOfDownloads).reversed());
        return results;
    }

    public List<StudentWithNumberOfUploads> getStudentWithNumberOfUploads() {
        Map<Integer, Integer> studentWithNumberOfUploads = new HashMap<>();
        for (Material t : this.materialIRepository.read()) {
            int studentId = t.getUploaderId();
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
            if(student==null){
                results.add(new StudentWithNumberOfUploads(studentId, numberOfUploads, "UNKNOWN"));
            }else{
            results.add(new StudentWithNumberOfUploads(studentId, numberOfUploads, student.getName()));}
        }
        results.sort(Comparator.comparing(StudentWithNumberOfUploads::getNumberOfUploads).reversed());
        return results;
    }

    /**
     *
     * @return returns all the students.
     */
    public List<Student> getAll() {
        return this.studentIRepository.read();
    }
}
