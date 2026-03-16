package com.zkekmnd.taskmanager;

public class Task {
    private String description;
    private final int id;
    private static int idCounter = 1;
    private boolean isDone;
    private Priority priority;

    public Task(String description, Priority priority) {
        this.description = description;
        this.priority = priority;
        this.id = idCounter++;
        this.isDone = false;
    }

    public Task(int id, String description, Priority priority, boolean isDone) {
        this.id = id;
        this.description = description;
        this.priority = priority;
        this.isDone = isDone;
    }

    public static void setIdCounter(int value) {
        idCounter = value;
    }
    public void setPriority(Priority priority){
        this.priority = priority;
    }
    public Priority getPriority() {
        return priority;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public int getId() {
        return id;
    }
    public boolean isDone() {
        return isDone;
    }
    public void toggleDone(){
        this.isDone = !isDone;
    }
    public void printTask(){
        IO.println(String.format("Task #%d - %s | [%s] | [%s]",
                this.id, this.description, this.priority.getLabel(), (this.isDone) ? "DONE" : "PENDING"));
    }
    public String toSaveString(){
        return String.format("%d|%s|%s|%b", this.id, this.description, this.priority.name(), this.isDone);
    }
}
