package org.example.domain;

public class MaterialWithNumberOfUploaders {
    private int idMedicine;
    private String name;
    private int numberOfPurchases;

    public MaterialWithNumberOfUploaders(int idMedicine, String name, int numberOfPurchases) {
        this.idMedicine = idMedicine;
        this.name = name;
        this.numberOfPurchases = numberOfPurchases;
    }

    public int getIdMedicine() {
        return idMedicine;
    }

    public String getName() {
        return name;
    }

    public int getNumberOfPurchases() {
        return numberOfPurchases;
    }

    @Override
    public String toString() {
        return "MaterialWithNumberOfUploaders{" +
                "idMedicine=" + idMedicine +
                ", name='" + name + '\'' +
                ", numberOfPurchases=" + numberOfPurchases +
                '}';
    }
}
