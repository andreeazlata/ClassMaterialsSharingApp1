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
    private Material material;

    private UndoRedoManager undoRedoManager;

    public ServiceMaterial(IRepository<Material> materialIRepository, IRepository<Transaction> transactionIRepository,IRepository<Student> studentRepository, MaterialValidator materialValidator, UndoRedoManager undoRedoManager) {
        this.materialIRepository = materialIRepository;
        this.transactionsRepository = transactionIRepository;
        this.studentRepository = studentRepository;
        this.materialValidator = materialValidator;
        this.undoRedoManager = undoRedoManager;

    }



    public void addMaterial(int idEntity, String name, String author, String description, int numberOfPages, int uploaderId) throws Exception {
        material = new Material( idEntity, name, author,  description, numberOfPages, uploaderId);
        this.materialValidator.validate(material, this.studentRepository);
        this.materialIRepository.create(material);
        undoRedoManager.addToUndo(new UndoRedoAddOperation<>(this.materialIRepository, material));

    }

    public void updateMaterial(int idEntity, String name, String author, String description, int numberOfPages, int uploaderId) throws Exception {
        Material material = new Material( idEntity, name, author,  description, numberOfPages, uploaderId);
        this.materialValidator.validate(material, this.studentRepository);
        this.materialIRepository.update(material);
    }


    public void delete(int idMaterial) throws RepositoryException {
        this.materialIRepository.delete(idMaterial);
        undoRedoManager.addToUndo(new UndoRedoDeleteOperation<>(this.materialIRepository, material));

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


//    /**
//     * @return list of medicine sorted by number of purchases, decreasing
//     */
//    public List<MaterialWithNumberOfUploaders> getMaterialWithNumberOfUploaders() {
//        Map<Integer, Integer> materialWithNumberOfUploaders = new HashMap<>();
//        for (Transaction t : this.transactionsRepository.read()) {
//            int idMaterial = t.getMaterialId();
//            if (!materialWithNumberOfUploaders.containsKey(idMaterial)) {
//                materialWithNumberOfUploaders.put(idMaterial, 1);
//            } else {
//                materialWithNumberOfUploaders.put(idMaterial, materialWithNumberOfUploaders.get(idMaterial) + 1);
//            }
//        }
//        List<MaterialWithNumberOfUploaders> results = new ArrayList<>();
//        for (Map.Entry<Integer, Integer> entry : materialWithNumberOfUploaders.entrySet()) {
//            int idMaterial = entry.getKey();
//            int numberOfPurchases = entry.getValue();
//
//            Material medicine = this.materialIRepository.readOne(idMaterial);
//            results.add(new MaterialWithNumberOfUploaders(idMaterial, material.getName(), numberOfPurchases));
//        }
//        results.sort(Comparator.comparing(MaterialWithNumberOfUploaders::getNumberOfUploaders).reversed());
//        return results;
//    }
//

    public List<Material> getAll() {
        return this.materialIRepository.read();
    }

}
