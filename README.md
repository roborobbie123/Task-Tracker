https://roadmap.sh/projects/task-tracker Welcome to my Task Tracker CLI.

This command line application can help you track what you need to do, what you have completed, and what you are currently working on.

These are the following commands:

add "task" --> adds a task to the list with description (adding a task will generate an id for other commands). update [id] "task" --> changes the task name / description based on the id. delete [id] -- > deletes task from list based on id.

mark-in-progress [id] --> marks given task as in progress. mark-done [id] --> marks given task as done

list --> lists all tasks. list done --> lists all tasks by status "done". list todo --> lists all tasks by status "todo". list in-progress --> lists all tasks by status "in-progress".

q --> saves data and exits application.
