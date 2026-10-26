import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        TaskService service = new TaskService();
        Scanner scanner = new Scanner(System.in);

        printUsage();
        while (true) {
            System.out.print("task-cli> ");
            if (!scanner.hasNextLine()) break;

            String line = scanner.nextLine().trim();
            if (line.isEmpty()) continue;
            if (line.equals("exit")) {
                System.out.println("Goodbye!");
                break;
            }
            handle(tokenize(line), service);
        }
    }

    // "add \"Buy groceries\"" -> ["add", "Buy groceries"]
    private static String[] tokenize(String line) {
        List<String> tokens = new ArrayList<>();
        Matcher m = Pattern.compile("\"([^\"]*)\"|(\\S+)").matcher(line);
        while (m.find()) {
            tokens.add(m.group(1) != null ? m.group(1) : m.group(2));
        }
        return tokens.toArray(new String[0]);
    }

    private static void handle(String[] args, TaskService service) {
        try {
            switch (args[0]) {
                case "add" -> {
                    requireArgs(args, 2);
                    Task task = service.add(args[1]);
                    System.out.println("Task added successfully (ID: " + task.getId() + ")");
                }
                case "update" -> {
                    requireArgs(args, 3);
                    report(service.update(Integer.parseInt(args[1]), args[2]), args[1]);
                }
                case "delete" -> {
                    requireArgs(args, 2);
                    report(service.delete(Integer.parseInt(args[1])), args[1]);
                }
                case "mark-in-progress" -> {
                    requireArgs(args, 2);
                    report(service.mark(Integer.parseInt(args[1]), TaskStatus.IN_PROGRESS), args[1]);
                }
                case "mark-done" -> {
                    requireArgs(args, 2);
                    report(service.mark(Integer.parseInt(args[1]), TaskStatus.DONE), args[1]);
                }
                case "list" -> {
                    TaskStatus filter = args.length > 1 ? parseStatus(args[1]) : null;
                    service.list(filter).forEach(System.out::println);
                }
                default -> printUsage();
            }
        } catch (NumberFormatException e) {
            System.out.println("ID must be a number.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void requireArgs(String[] args, int count) {
        if (args.length < count) {
            throw new IllegalArgumentException("Missing arguments for '" + args[0] + "'.");
        }
    }

    private static TaskStatus parseStatus(String s) {
        return switch (s) {
            case "todo" -> TaskStatus.TODO;
            case "in-progress" -> TaskStatus.IN_PROGRESS;
            case "done" -> TaskStatus.DONE;
            default -> throw new IllegalArgumentException("Unknown status: " + s);
        };
    }

    private static void report(boolean success, String id) {
        System.out.println(success ? "Done (ID: " + id + ")" : "No task with ID " + id);
    }

    private static void printUsage() {
        System.out.println("Commands: add <desc> | update <id> <desc> | delete <id> | "
                + "mark-in-progress <id> | mark-done <id> | list [todo|in-progress|done] | exit");
    }
}