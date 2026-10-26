# Task Tracker CLI

A simple command-line task manager written in Java. Add, update, delete and track tasks from the console. Tasks are saved to a `tasks.json` file in the directory you run the program from.

## Requirements

- Java 21
- Maven
- Jackson 3 (`tools.jackson`), managed through `pom.xml`

## Running

Open the project in an IDE (e.g. IntelliJ IDEA) and run `Main`. The program starts an interactive prompt:

```
task-cli>
```

Type a command and press Enter. Type `exit` to quit.

## Commands

| Command | What it does |
|---|---|
| `add "<description>"` | Add a new task (starts as `todo`) |
| `update <id> "<description>"` | Change a task's description |
| `delete <id>` | Delete a task |
| `mark-in-progress <id>` | Mark a task as in progress |
| `mark-done <id>` | Mark a task as done |
| `list` | List all tasks |
| `list todo` | List tasks that are not started |
| `list in-progress` | List tasks in progress |
| `list done` | List completed tasks |
| `exit` | Quit the program |

Use double quotes around descriptions that contain spaces.

## Example session

```
task-cli> add "Buy groceries"
Task added successfully (ID: 1)
task-cli> update 1 "Buy groceries and cook dinner"
Done (ID: 1)
task-cli> mark-in-progress 1
Done (ID: 1)
task-cli> list
1. [IN_PROGRESS] Buy groceries and cook dinner
task-cli> mark-done 1
Done (ID: 1)
task-cli> list done
1. [DONE] Buy groceries and cook dinner
task-cli> exit
Goodbye!
```

## Data file

Tasks are stored in `tasks.json`. It is created on the first `add`.

```json
[ {
  "id" : 1,
  "description" : "Buy groceries and cook dinner",
  "status" : "done",
  "createdAt" : "2026-10-26T18:43:14.454813Z",
  "updatedAt" : "2026-10-26T18:44:22.048602Z"
} ]
```

Each task has an `id`, a `description`, a `status` (`todo`, `in-progress` or `done`), and `createdAt` / `updatedAt` timestamps.

## Project structure

```
src/main/java
├── Main.java         # console loop, input parsing, command handling
├── Task.java         # the task data (JSON-serializable)
├── TaskStatus.java   # todo / in-progress / done
├── TaskService.java  # add, update, delete, mark, list logic
└── TaskStorage.java  # loads and saves tasks.json with Jackson
```

