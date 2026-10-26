import java.util.List;
import java.util.function.Consumer;

public class TaskService {
    private final TaskStorage storage = new TaskStorage();

    public Task add(String description) {
        List<Task> tasks = storage.load();
        int nextId = tasks.stream().mapToInt(Task::getId).max().orElse(0) + 1;
        Task task = new Task(nextId, description);
        tasks.add(task);
        storage.save(tasks);
        return task;
    }

    public boolean update(int id, String description) {
        return modify(id, t -> t.updateDescription(description));
    }

    public boolean mark(int id, TaskStatus status) {
        return modify(id, t -> t.changeStatus(status));
    }

    public boolean delete(int id) {
        List<Task> tasks = storage.load();
        boolean removed = tasks.removeIf(t -> t.getId() == id);
        if (removed) storage.save(tasks);
        return removed;
    }

    public List<Task> list(TaskStatus filter) {   // null = all
        List<Task> tasks = storage.load();
        if (filter == null) return tasks;
        return tasks.stream().filter(t -> t.getStatus() == filter).toList();
    }

    // find the task, apply the change, save; false if no such id
    private boolean modify(int id, Consumer<Task> change) {
        List<Task> tasks = storage.load();
        for (Task t : tasks) {
            if (t.getId() == id) {
                change.accept(t);
                storage.save(tasks);
                return true;
            }
        }
        return false;
    }
}