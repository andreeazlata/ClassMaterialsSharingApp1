package org.example.domain;

public class MaterialWithNumberOfUploaders {
    private int idMaterial;
    private String nameMaterial;
    private int numberOfUploaders;

    public MaterialWithNumberOfUploaders(int idMaterial, String nameMaterial, int numberOfUploaders) {
        this.idMaterial = idMaterial;
        this.nameMaterial = nameMaterial;
        this.numberOfUploaders = numberOfUploaders;
    }

    public int getIdMaterial() {
        return idMaterial;
    }

    public String getNameMaterial() {
        return nameMaterial;
    }

    public int getNumberOfUploaders() {
        return numberOfUploaders;
    }

    @Override
    public String toString() {
        return "MaterialWithNumberOfUploaders{" +
                "idMaterial=" + idMaterial +
                ", nameMaterial='" + nameMaterial + '\'' +
                ", numberOfUploaders=" + numberOfUploaders +
                '}';
    }
}
