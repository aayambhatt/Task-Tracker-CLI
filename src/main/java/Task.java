import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

public class Task {
    private final int id;
    private String description;
    private TaskStatus status;
    private final Instant createdAt;
    private Instant updatedAt;

    // new task
    public Task(int id, String description) {
        this.id = id;
        this.description = description;
        this.status = TaskStatus.TODO;
        this.createdAt = Instant.now();
        this.updatedAt = createdAt;
    }

    // restore from JSON
    @JsonCreator
    public Task(@JsonProperty("id") int id,
                @JsonProperty("description") String description,
                @JsonProperty("status") TaskStatus status,
                @JsonProperty("createdAt") Instant createdAt,
                @JsonProperty("updatedAt") Instant updatedAt) {
        this.id = id;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getId() { return id; }
    public String getDescription() { return description; }
    public TaskStatus getStatus() { return status; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }

    public void updateDescription(String description) {
        this.description = description;
        this.updatedAt = Instant.now();
    }

    public void changeStatus(TaskStatus status) {
        this.status = status;
        this.updatedAt = Instant.now();
    }

    @Override
    public String toString() {
        return id + ". [" + status + "] " + description;
    }
}