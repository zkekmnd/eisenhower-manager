# ZKEK's Eisenhower Task Manager
A console-based Task Manager built with Java, implementing the Eisenhower Matrix priority system.

## Features
- **CRUD Operations**: Add, List, Update, and Delete tasks.
- **Eisenhower Matrix**: Categorize tasks by Urgency and Importance (***DO, SCHEDULE, DELEGATE, ELIMINATE***).
- **Persistence**: Automatically saves and loads tasks from a local file (e.g., `tasks.txt`).
- **Robust Error Handling**: Handles invalid inputs, missing files, and data corruption gracefully.
- **Smart ID Management**: Ensures unique IDs even after restarting the application.

## How to Run
1. Clone this repository.
2. Compile the Java files: `javac -d out src/com/zkekmnd/taskmanager/*.java`
3. Run the main class: `java -cp out com.zkekmnd.taskmanager.Eisenhower`

## Commands
- `add|<description>,<priority>`
- `list` (subcommands: `list_all`, `list_priority`)
- `done|<id>`
- `delete|<id>`
- `update_desc|<id>|<new_description>`
- `quit`

## Tech Stack
- Java (Enums, Collections, File I/O, Streams)
- Object-Oriented Design