import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TaskStorage {
    private final ObjectMapper mapper = new ObjectMapper();
    private final File file = new File("tasks.json");

    public List<Task> load() {
        if (!file.exists()) return new ArrayList<>();
        return mapper.readValue(file, new TypeReference<List<Task>>() {});
    }

    public void save(List<Task> tasks) {
        mapper.writerWithDefaultPrettyPrinter().writeValue(file, tasks);
    }
}