package org.example.koltsegkoveto.frontend.model;

public class Category {
    private Long id;
    private String name;

    // getterek és setterek
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name; // ComboBox-ban a kategória nevét fogja mutatni
    }
}
