import java.time.LocalDate;
import java.time.LocalTime;

public class Task {
    private String id;
    private String description;
    private String status;
    private LocalTime createdAt;
    private LocalTime updatedAt;

    public Task() {}

    public Task(String id, String description, String status, LocalTime createdAt, LocalTime updatedAt) {
        this.id = id;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public LocalTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalTime createdAt) {
        this.createdAt = createdAt;
    }
    public LocalTime getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(LocalTime updatedAt) {
        this.updatedAt = updatedAt;
    }

}

