package org.example.domain;

public class MaterialValidator {

    public void validate(Material material) throws Exception {
        if (material.getName()=="a") {
            throw new Exception("The material must have a name");
        }
        if (material.getDescription().length()<3) {
            throw new Exception("The description must be at least 15 characters long");
        }
    }
}