package com.zkekmnd.taskmanager;

public class Eisenhower{
    void main(){
        TaskManager taskMan = new TaskManager();
        boolean isRunning = true;
        String filename = IO.readln("Enter a file name: ");
        taskMan.readFromFile(filename);

        while(isRunning){
            IO.println("\nZeke's Dummy Eisenhower Task Manager");
            IO.print("""
            Components: task_id, task_description, & PRIORITY
            
            Command Instructions:
            [add] - add|task_description|PRIORITY
            [list] - list
            [update] - update
            [done] - done|task_id
            [delete] - delete|task_id
            [quit] - quit
            """);
            String choice = IO.readln("Enter a command: ");
            String[] command = choice.split("\\|");

            switch(command[0].toLowerCase()){
                case "add" ->{
                    try{
                        if(command.length < 2){
                            IO.println("You're missing a task description.");
                            IO.println("Usage: add|description|PRIORITY");
                        }
                        else if(command.length < 3){
                            IO.println("You're missing the task's priority.");
                            IO.println("Usage: add|description|PRIORITY");
                        }
                        else{
                            taskMan.addTask(command[1].trim(), Priority.valueOf(command[2].trim().toUpperCase()));
                        }
                    }
                    catch(IllegalArgumentException e){
                        IO.println("Priority Usage: DO, SCHEDULE, DELEGATE, ELIMINATE " + e.getMessage());
                    }
                }
                case "list" -> {
                    try{
                        IO.print("""
                        
                        [list_all] - Lists all tasks
                        [list_priority] - Lists all tasks by specified priority
                        """);
                        String listChoice = IO.readln("Enter a command: ");
                        String[] commandList = listChoice.split("\\|");

                        switch(commandList[0].trim().toLowerCase()){
                            case "list_all" -> {
                                if(commandList.length > 1){
                                    IO.println("Other fields are not required.");
                                    IO.println("Usage: list_all");
                                    break;
                                }
                                taskMan.listAllTasks();
                            }
                            case "list_priority" -> {
                                if(commandList.length < 2){
                                    IO.println("You're missing the tasks' priority.");
                                    IO.println("Usage: list_priority|PRIORITY");
                                    break;
                                }
                                taskMan.listByPriority(Priority.valueOf(commandList[1].trim().toUpperCase()));
                            }
                        }
                    }
                    catch(IllegalArgumentException e){
                        IO.println("Priority Usage: DO, SCHEDULE, DELEGATE, ELIMINATE " + e.getMessage());
                    }
                }
                case "update" -> {
                    IO.print("""
                        
                        [update_desc] - Updates task's description
                        [update_priority] - Updates task's priority
                        """);
                    String updateChoice = IO.readln("Enter a command: ");
                    String[] commandUpdate = updateChoice.split("\\|");

                    switch(commandUpdate[0].trim().toLowerCase()){
                        case "update_desc" -> {
                            if(commandUpdate.length < 3){
                                IO.println("You are missing something.");
                                IO.println("Usage: update_desc|[task_id]|new description");
                                break;
                            }
                            try{
                                taskMan.changeDescription(Integer.parseInt(commandUpdate[1]), commandUpdate[2].trim());
                            }
                            catch(NumberFormatException e){
                                IO.println("Parse Error. Enter a real number for the task id.: " + e.getMessage());
                            }

                        }
                        case "update_priority" -> {
                            if(commandUpdate.length < 3){
                                IO.println("You are missing something.");
                                IO.println("Usage: update_priority|[task_id]|PRIORITY");
                            }

                            try {
                                taskMan.updatePriority(Integer.parseInt(commandUpdate[1]),
                                        Priority.valueOf(commandUpdate[2].trim().toUpperCase()));
                            }
                            catch(NumberFormatException e){
                                IO.println("Parse Error. Enter a real number for the task id.: " + e.getMessage());
                            }
                            catch(IllegalArgumentException e){
                                IO.println("Priority Usage: DO, SCHEDULE, DELEGATE, ELIMINATE " + e.getMessage());
                            }
                        }
                    }
                }
                case "done" -> {
                    if(command.length < 2){
                        IO.println("Usage: done|[task id]");
                        break;
                    }
                    try{
                        int id = Integer.parseInt(command[1]);
                        taskMan.markTaskAsDone(id);
                    }
                    catch(NumberFormatException e){
                        IO.println("Invalid ID. Please enter a valid number.");
                    }
                }
                case "delete" -> {
                    if(command.length < 2){
                        IO.println("Usage: delete|[task id]");
                        break;
                    }
                    try{
                        int id = Integer.parseInt(command[1]);
                        taskMan.deleteTask(id);
                    }
                    catch(NumberFormatException e){
                        IO.println("Invalid ID. Please enter a valid number.");
                    }
                }
                case "quit" -> {
                    filename = IO.readln("Enter a file name: ");
                    IO.println("Saving to file...");
                    taskMan.saveToFile(filename);
                    IO.println("File saved successfully.");
                    isRunning = false;
                }
                default -> IO.println("Invalid command. Try again.");
            }
        }
    }
}