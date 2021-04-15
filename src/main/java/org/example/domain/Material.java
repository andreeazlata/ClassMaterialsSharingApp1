package org.example.domain;

public class Material extends Entity {
    private String name;
    private String author;
    private String description;
    private int numberOfPages;
    private int uploaderId;

    public Material(int idEntity, String name, String author, String description, int numberOfPages, int uploaderId) {
        super(idEntity);
        this.name = name;
        this.author = author;
        this.description = description;
        this.numberOfPages = numberOfPages;
        this.uploaderId = uploaderId;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public String getDescription() {
        return description;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public int getUploaderId() {
        return uploaderId;
    }

    @Override
    public String toString() {
        return "Material{" +
                "name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", description='" + description + '\'' +
                ", numberOfPages=" + numberOfPages +
                ", uploaderId=" + uploaderId +
                '}';
    }
}
