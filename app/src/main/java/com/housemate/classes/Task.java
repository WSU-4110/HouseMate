package com.housemate.classes;

public abstract class Task {
    private String name;
    private String description;
    private int id;

    public Task(
            String name,
            String description
    ) {
        this.name = name;
        this.description = description;
        id = -1;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public int getId() { return id; }

    void setId(int id) { this.id = id; }
}