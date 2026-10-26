import com.fasterxml.jackson.annotation.JsonProperty;

public enum TaskStatus {
    @JsonProperty("todo")
    TODO,

    @JsonProperty("in-progress")
    IN_PROGRESS,

    @JsonProperty("done")
    DONE


}
