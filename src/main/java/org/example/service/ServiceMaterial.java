package org.example.service;

import org.example.domain.Material;
import org.example.domain.MaterialValidator;
import org.example.domain.MaterialWithNumberOfUploaders;
import org.example.domain.Transaction;
import org.example.repository.IRepository;
import org.example.repository.RepositoryException;


import java.util.*;

public class ServiceMaterial {

    private final IRepository<Material> materialIRepository;
    private final IRepository<Transaction> transactionsRepository;
    private final MaterialValidator materialValidator;
    private Material material;
    private UndoRedoManager undoRedoManager;

    public ServiceMaterial(IRepository<Material> materialIRepository, IRepository<Transaction> transactionIRepository, MaterialValidator materialValidator, UndoRedoManager undoRedoManager) {
        this.materialIRepository = materialIRepository;
        this.transactionsRepository = transactionIRepository;
        this.materialValidator = materialValidator;
        this.undoRedoManager = undoRedoManager;
    }



    public void addMaterial(int idEntity, String name, String author, String description, int numberOfPages) throws Exception {
        material = new Material( idEntity, name, author,  description, numberOfPages);
        this.materialValidator.validate(material);
        this.materialIRepository.create(material);
    }

    public void updateMaterial(int idEntity, String name, String author, String description, int numberOfPages) throws Exception {
        Material material = new Material( idEntity, name, author,  description, numberOfPages);
        this.materialValidator.validate(material);
        this.materialIRepository.update(material);
    }


    public void delete(int idMaterial) throws RepositoryException {
        this.materialIRepository.delete(idMaterial);

    }

    public List<Material> getMaterialsByText(String searchText) {
        List<Material> results = new ArrayList<>();
        for (Material p : this.getAll()) {
            if (p.getName().contains(searchText) ||
                    p.getAuthor().contains(searchText) ||
                    p.getDescription().contains(searchText) ||
                    String.valueOf(p.getNumberOfPages()).contains(searchText));
 {
                results.add(p);
            }
        }

        return results;
    }
//    public List<Material> getMedicinesCheaperThan(float maxPrice) {
//        List<Material> medicines = new ArrayList<>();
//        for (Material medicine : this.materialIRepository.read()) {
//            if (medicine.getPrice() < maxPrice) {
//                medicines.add(medicine);
//            }
//        }
//        return medicines;
//    }
//


    /**
     * @return list of medicine sorted by number of purchases, decreasing
     */
    public List<MaterialWithNumberOfUploaders> getMedicineOrderedByNumberOfPurchases() {
        Map<Integer, Integer> materialWithNumberOfUploaders = new HashMap<>();
        for (Transaction t : this.transactionsRepository.read()) {
            int idMaterial = t.getMaterialId();
            if (!materialWithNumberOfUploaders.containsKey(idMaterial)) {
                materialWithNumberOfUploaders.put(idMaterial, 1);
            } else {
                materialWithNumberOfUploaders.put(idMaterial, materialWithNumberOfUploaders.get(idMaterial) + 1);
            }
        }
        List<MaterialWithNumberOfUploaders> results = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : materialWithNumberOfUploaders.entrySet()) {
            int idMaterial = entry.getKey();
            int numberOfPurchases = entry.getValue();

            Material medicine = this.materialIRepository.readOne(idMaterial);
            results.add(new MaterialWithNumberOfUploaders(idMaterial, material.getName(), numberOfPurchases));
        }
        results.sort(Comparator.comparing(MaterialWithNumberOfUploaders::getNumberOfUploaders).reversed());
        return results;
    }

    /**
     * @return list of client cards in decreasing order based on the number of purchases
     */
//    public List<StudentWithNumberOfUploads> getClientCardsOrderedByNumberOfPurchases() {
//        Map<Integer, Integer> studentWithNumberOfUploads = new HashMap<>();
//        for (Transaction t : this.transactionsRepository.read()) {
//            int studentId = t.get();
//            if (!studentWithNumberOfUploads.containsKey(studentId)) {
//                studentWithNumberOfUploads.put(studentId, 1);
//            } else {
//                studentWithNumberOfUploads.put(studentId, studentWithNumberOfUploads.get(studentId) + 1);
//            }
//        }
//        List<StudentWithNumberOfUploads> results = new ArrayList<>();
//        for (Map.Entry<Integer, Integer> entry : studentWithNumberOfUploads.entrySet()) {
//            int studentId = entry.getKey();
//            int uploads = entry.getValue();
//
//            Material material = materialIRepository.readOne(studentId);
//            results.add(new StudentWithNumberOfUploads(studentId, uploads));
//        }
//        results.sort(Comparator.comparing(StudentWithNumberOfUploads::getNumberOfPurchases).reversed());
//        return results;
//    }

    public List<Material> getAll() {
        return this.materialIRepository.read();
    }

}
