package org.example.service;

import org.example.domain.*;
import org.example.repository.IRepository;
import org.example.repository.RepositoryException;


import java.util.*;

public class ServiceMaterial {

    private final IRepository<Material> materialIRepository;
    private final IRepository<Transaction> transactionsRepository;
    private final IRepository<Student> studentRepository;
    private final MaterialValidator materialValidator;
    private final UndoRedoManager undoRedoManager;
    private Material material;


    public ServiceMaterial(IRepository<Material> materialIRepository, IRepository<Transaction> transactionIRepository, IRepository<Student> studentRepository, MaterialValidator materialValidator, UndoRedoManager undoRedoManager) {
        this.materialIRepository = materialIRepository;
        this.transactionsRepository = transactionIRepository;
        this.studentRepository = studentRepository;
        this.materialValidator = materialValidator;
        this.undoRedoManager = undoRedoManager;

    }


    /**
     * @param idEntity      the id of the material.
     * @param name          the name of the material.
     * @param author        the author of the material.
     * @param description   the description of the material.
     * @param numberOfPages the number of pages of the material.
     * @param uploaderId    the id for the person who uploaded the material.
     * @throws Exception
     */
    public void addMaterial(int idEntity, String name, String author, String description, int numberOfPages, int uploaderId) throws Exception {
        Material material = new Material(idEntity, name, author, description, numberOfPages, uploaderId);
        this.materialValidator.validate(material, this.studentRepository);
        this.materialIRepository.create(material);
        undoRedoManager.addToUndo(new UndoRedoAddOperation<>(this.materialIRepository, material));

    }

    /**
     * @param idEntity      the id of the material.
     * @param name          the name of the material.
     * @param author        the author of the material.
     * @param description   the description of the material.
     * @param numberOfPages the number of pages of the material.
     * @param uploaderId    the id for the person who uploaded the material.
     * @throws Exception
     */
    public void updateMaterial(int idEntity, String name, String author, String description, int numberOfPages, int uploaderId) throws Exception {
        Material material = new Material(idEntity, name, author, description, numberOfPages, uploaderId);
        this.materialValidator.validate(material, this.studentRepository);
        this.materialIRepository.update(material);
//        undoRedoManager.addToUndo(new UndoRedoUpdateOperation<>(this.materialIRepository,material));
    }

    /**
     * @param idMaterial the id of the material.
     * @throws RepositoryException
     */
    public void delete(int idMaterial) throws RepositoryException {
        Material material = this.materialIRepository.readOne(idMaterial);
        this.materialIRepository.delete(idMaterial);
        undoRedoManager.addToUndo(new UndoRedoDeleteOperation<>(this.materialIRepository, material));

    }

    /**
     * @param searchText the text by which the search is made.
     * @return the materials that contain the searched text.
     */
    public List<Material> getMaterialsByText(String searchText) {
        List<Material> results = new ArrayList<>();
        for (Material p : this.getAll()) {
            if (p.getName().contains(searchText) ||
                    p.getAuthor().contains(searchText) ||
                    String.valueOf(p.getDescription()).contains(searchText) ||
                    String.valueOf(p.getUploaderId()).contains(searchText) ||
                    String.valueOf(p.getIdEntity()).contains(searchText)) {
                results.add(p);
            }
        }

        return results;
    }

    /**
     * @return returns a list of all the materials.
     */
    public List<Material> getAll() {
        return this.materialIRepository.read();
    }

}
