public class Task {
    private String taskName;
    private String status;

    public Task(String taskName, String status) {
        this.taskName = taskName;
        this.status = status;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
    public String getTaskName() {
        return taskName;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getStatus() {
        return status;
    }
}
