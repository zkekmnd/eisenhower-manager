package com.zkekmnd.taskmanager;

import java.util.LinkedHashMap;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import java.util.stream.Collectors;

public class TaskManager{
    LinkedHashMap<Integer, Task> tasks = new LinkedHashMap<>();
    static final String TASK_NUMBER = "Task #";

    public void addTask(String description, Priority priority){
        Task task = new Task(description, priority);
        tasks.put(task.getId(), task);

        IO.println("Task added with ID #" + task.getId());
    }

    public void listAllTasks(){
        if(tasks.isEmpty()) IO.println("There's nothing to display here.");
        tasks.values().forEach(Task::printTask);
    }

    public void listByPriority(Priority priority){
        if(tasks.isEmpty()) IO.println("There's nothing to display here.");

        List<Task> sortedTasks = tasks.values().stream()
                .peek(task -> IO.println("Checking: " + task.getPriority()))
                .filter(task -> task.getPriority().equals(priority))
                .toList();

        if(sortedTasks.isEmpty()) IO.println("No tasks with the priority: " + priority);
        sortedTasks.forEach(Task::printTask);
    }

    public void markTaskAsDone(int id){
        Task task = getTask(id);
        if(task == null) return;

        task.toggleDone();
        IO.println(String.format("Task #%d updated to [%s]", id, (task.isDone()) ? "DONE" : "PENDING"));
    }

    public void updatePriority(int id, Priority newPriority){
        Task task = getTask(id);
        if(task == null) return;

        task.setPriority(newPriority);
        IO.println(TASK_NUMBER + id + " has been updated to [" + task.getPriority().getLabel() + "]");
    }

    public void deleteTask(int id){
        Task removedTask = tasks.remove(id);
        if(removedTask == null){
            IO.println(TASK_NUMBER + " not found");
            return;
        }
        IO.println(TASK_NUMBER + id + " has successfully been removed.");
    }

    public void changeDescription(int id, String description){
        Task task = getTask(id);
        if(task == null) return;

        task.setDescription(description);
        IO.println(TASK_NUMBER + id + " changed description to \"" + description + "\"");
    }

    public Task getTask(int id){
        Task task = tasks.get(id);
        if(task == null) {
            IO.println(TASK_NUMBER + id + " not found.");
        }
        return task;
    }

    public void saveToFile(String filename){
        Path filePath = Path.of(filename);
        try{
            String lines = tasks.values().stream()
                    .map(Task::toSaveString)
                    .collect(Collectors.joining("\n"));
            Files.writeString(filePath, lines);
        }
        catch(IOException e){
            IO.println("File Error: " + e.getMessage());
        }
    }

    public void readFromFile(String filename){
        Path filePath = Path.of(filename);
        if(!Files.exists(filePath)){
            IO.println("LOG: No saved tasks found. Starting from scratch...");
            return;
        }

        try{
            List<String> contents = Files.readAllLines(filePath);
            int maxId = 0;

            for(String line : contents){
                if(line.trim().isEmpty()){
                    continue;
                }

                String[] parts = line.split("\\|");

                if(parts.length < 4){
                    IO.println("Data incomplete: " + line);
                    continue;
                }

                try{
                    int id = Integer.parseInt(parts[0].trim());
                    String description = parts[1].trim();
                    Priority priority = Priority.valueOf(parts[2].trim().toUpperCase());
                    boolean isDone = Boolean.parseBoolean(parts[3].trim());
                    Task task = new Task(id, description, priority, isDone);
                    tasks.put(id, task);

                    if(id > maxId) maxId = id;
                }
                catch(Exception e){
                    IO.println("Line [" + line + "] Parse Error: " + e.getMessage());
                }
            }
            if(maxId > 0){
                Task.setIdCounter(maxId + 1);
                IO.println("Successfully loaded " + tasks.size() + " tasks.");
            }
        }
        catch(IOException e){
            IO.println("File Error: " + e.getMessage());
        }
        catch(IllegalArgumentException e){
            IO.println("Parse Error: " + e.getMessage());
        }
    }
}
