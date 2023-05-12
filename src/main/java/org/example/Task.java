package org.example;

public class Task {
    private String name;
    private String date;
    private boolean isImportant;

    public Task(String name, String date, boolean isImportant) {
        this.name = name;
        this.date = date;
        this.isImportant = isImportant;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public boolean isImportant() {
        return isImportant;
    }
}
