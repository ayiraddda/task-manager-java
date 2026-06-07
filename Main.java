package main;
import model.Employee;
import model.Priority;
import model.Task;
import model.TaskStatus;
import service.TaskService;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
public class Main {
    private static final TaskService service = new TaskService();
    private static final Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        System.out.println("Task Manager");
        while (true) {
            printMenu();
            String choice = scanner.nextLine().trim();
            System.out.println();
            switch (choice) {
                case "1": addEmployee();     break;
                case "2": createTask();      break;
                case "3": assignTask();      break;
                case "4": updateStatus();    break;
                case "5": tasksByEmployee(); break;
                case "6": tasksByStatus();   break;
                case "7": analyticsMenu();   break;
                case "0":
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Unknown option. Enter a number from the menu.");
            }
            System.out.println();
        }
    }
    private static void printMenu() {
        System.out.println(" 1. Add employee");
        System.out.println(" 2. Create task");
        System.out.println(" 3. Assign task");
        System.out.println(" 4. Update task status");
        System.out.println(" 5. Tasks by employee");
        System.out.println(" 6. Tasks by status");
        System.out.println(" 7. Analytics");
        System.out.println(" 0. Exit");
        System.out.print("Choose: ");
    }
    private static void addEmployee() {
        int id = readInt("Employee ID: ");
        if (id < 0) return;
        System.out.print("Name: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Error: name cannot be empty.");
            return;
        }
        System.out.print("Role (e.g. Developer, QA, Manager): ");
        String role = scanner.nextLine().trim();
        if (role.isEmpty()) {
            System.out.println("Error: role cannot be empty");
            return;
        }
        Employee employee = new Employee(id, name, role);
        if (service.addEmployee(employee)) {
            System.out.println("Added: " + employee);
        }
    }
    private static void createTask() {
        int id = readInt("Task ID: ");
        if (id < 0) return;
        System.out.print("Title: ");
        String title = scanner.nextLine().trim();
        if (title.isEmpty()) {
            System.out.println("Error: title cannot be empty");
            return;
        }
        System.out.print("Description: ");
        String description = scanner.nextLine().trim();
        TaskStatus status = readTaskStatus();
        if (status == null) return;
        Priority priority = readPriority();
        if (priority == null) return;
        int projectId = readInt("Project ID: ");
        if (projectId < 0) return;
        Task task = new Task(id, title, description, status, priority, projectId);
        if (service.addTask(task)) {
            System.out.println("Created: " + task);
        }
    }
    private static void assignTask() {
        int taskId = readInt("Task ID: ");
        if (taskId < 0) return;

        int employeeId = readInt("Employee ID: ");
        if (employeeId < 0) return;

        if (service.assignTask(taskId, employeeId)) {
            System.out.println("Task #" + taskId + " assigned to employee #" + employeeId);
        }
    }
    private static void updateStatus() {
        int taskId = readInt("Task ID: ");
        if (taskId < 0) return;

        TaskStatus status = readTaskStatus();
        if (status == null) return;

        if (service.updateStatus(taskId, status)) {
            System.out.println("Task #" + taskId + " status updated to " + status);
        }
    }
    private static void tasksByEmployee() {
        int employeeId = readInt("Employee ID: ");
        if (employeeId < 0) return;
        Employee employee = service.findEmployeeById(employeeId);
        if (employee == null) {
            System.out.println("Error: employee with ID " + employeeId + " not found.");
            return;
        }
        List<Task> tasks = service.getTasksByEmployee(employeeId);
        if (tasks.isEmpty()) {
            System.out.println(employee.getName() + " has no assigned tasks.");
        } else {
            System.out.println("Tasks of " + employee.getName() + " [" + employee.getRole() + "] (" + tasks.size() + " total):");
            for (Task t : tasks) {
                System.out.println("  " + t);
            }
        }
    }
    private static void tasksByStatus() {
        TaskStatus status = readTaskStatus();
        if (status == null) return;

        List<Task> tasks = service.getTasksByStatus(status);
        if (tasks.isEmpty()) {
            System.out.println("No tasks with status " + status + ".");
        } else {
            System.out.println("Tasks with status " + status + " (" + tasks.size() + " total):");
            for (Task t : tasks) {
                System.out.println("  " + t);
            }
        }
    }
    private static void analyticsMenu() {
        while (true) {
            System.out.println("Analytics");
            System.out.println(" 1. Count by status");
            System.out.println(" 2. HIGH priority tasks");
            System.out.println(" 3. Most overloaded employee");
            System.out.println(" 4. Search task by title");
            System.out.println(" 0. Back");
            System.out.print("Choose: ");
            String choice = scanner.nextLine().trim();
            System.out.println();
            switch (choice) {
                case "1": showStatusCounts();      break;
                case "2": showHighPriorityTasks(); break;
                case "3": showMostBusyEmployee();  break;
                case "4": searchByTitle();         break;
                case "0": return;
                default: System.out.println("Unknown option");
            }
            System.out.println();
        }
    }
    private static void showStatusCounts() {
        Map<TaskStatus, Integer> counts = service.getTaskCountByStatus();
        System.out.println("Tasks by status:");
        for (Map.Entry<TaskStatus, Integer> entry : counts.entrySet()) {
            System.out.printf("  %-15s → %d%n", entry.getKey(), entry.getValue());
        }
    }
    private static void showHighPriorityTasks() {
        List<Task> tasks = service.getHighPriorityTasks();
        if (tasks.isEmpty()) {
            System.out.println("No HIGH priority tasks");
        } else {
            System.out.println("HIGH priority tasks (" + tasks.size() + "):");
            for (Task t : tasks) {
                System.out.println("  " + t);
            }
        }
    }
    private static void showMostBusyEmployee() {
        Employee employee = service.getMostBusyEmployee();
        if (employee == null) {
            System.out.println("No employees with unfinished tasks found");
        } else {
            System.out.println("Most overloaded employee: " + employee);
        }
    }
    private static void searchByTitle() {
        System.out.print("Keyword: ");
        String keyword = scanner.nextLine().trim();
        if (keyword.isEmpty()) {
            System.out.println("Error: keyword cannot be empty");
            return;
        }
        List<Task> tasks = service.searchByTitle(keyword);
        if (tasks.isEmpty()) {
            System.out.println("No tasks found matching \"" + keyword + "\".");
        } else {
            System.out.println("Found " + tasks.size() + " task(s) matching \"" + keyword + "\":");
            for (Task t : tasks) {
                System.out.println("  " + t);
            }
        }
    }
    private static int readInt(String prompt) {
        System.out.print(prompt);
        String line = scanner.nextLine().trim();
        try {
            return Integer.parseInt(line);
        } catch (NumberFormatException e) {
            System.out.println("Error: please enter a valid integer");
            return -1;
        }
    }
    private static TaskStatus readTaskStatus() {
        System.out.println("Status:   1=TODO  2=IN_PROGRESS  3=DONE");
        System.out.print("Choose: ");
        String input = scanner.nextLine().trim();
        switch (input) {
            case "1": return TaskStatus.TODO;
            case "2": return TaskStatus.IN_PROGRESS;
            case "3": return TaskStatus.DONE;
            default:
                System.out.println("Error: invalid status. Enter 1, 2, or 3");
                return null;
        }
    }
    private static Priority readPriority() {
        System.out.println("Priority: 1=LOW  2=MEDIUM  3=HIGH");
        System.out.print("Choose: ");
        String input = scanner.nextLine().trim();
        switch (input) {
            case "1": return Priority.LOW;
            case "2": return Priority.MEDIUM;
            case "3": return Priority.HIGH;
            default:
                System.out.println("Error: invalid priority. Enter 1, 2, or 3");
                return null;
        }
    }
}