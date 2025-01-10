import java.util.List;

public class TaskTracker {
    private List<Task> tasks;

    public TaskTracker() {}

    public TaskTracker(List<Task> tasks) {
        this.tasks = tasks;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }
    public void removeTask(Task task) {
        tasks.remove(task);
    }
    public void updateTask(Task task) {
        tasks.set(tasks.indexOf(task), task);
    }
    public void printTasks() {
        for (Task task : tasks) {
            System.out.println(task);
        }
    }

    public List<Task> getTasks() {
        return tasks;
    }
    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}

