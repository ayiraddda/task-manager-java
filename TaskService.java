package service;
import model.Employee;
import model.Priority;
import model.Task;
import model.TaskStatus;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class TaskService {
    private final ArrayList<Task> taskList         = new ArrayList<>();
    private final ArrayList<Employee> employeeList = new ArrayList<>();
    private final HashMap<Integer, Task> taskMap         = new HashMap<>();
    private final HashMap<Integer, Employee> employeeMap = new HashMap<>();
    public boolean addEmployee(Employee employee) {
        if (employeeMap.containsKey(employee.getId())) {
            System.out.println("Error: employee with ID " + employee.getId() + " already exists");
            return false;
        }
        employeeList.add(employee);
        employeeMap.put(employee.getId(), employee);
        return true;
    }
    public boolean addTask(Task task) {
        if (taskMap.containsKey(task.getId())) {
            System.out.println("Error: task with ID " + task.getId() + " already exists");
            return false;
        }
        taskList.add(task);
        taskMap.put(task.getId(), task);
        return true;
    }
    public boolean assignTask(int taskId, int employeeId) {
        Task task = taskMap.get(taskId);
        if (task == null) {
            System.out.println("Error: task with ID " + taskId + " not found");
            return false;
        }
        if (!employeeMap.containsKey(employeeId)) {
            System.out.println("Error: employee with ID " + employeeId + " not found");
            return false;
        }
        task.setAssigneeId(employeeId);
        return true;
    }
    public boolean updateStatus(int taskId, TaskStatus newStatus) {
        Task task = taskMap.get(taskId);
        if (task == null) {
            System.out.println("Error: task with ID " + taskId + " not found");
            return false;
        }
        task.setStatus(newStatus);
        return true;
    }
    public List<Task> getTasksByEmployee(int employeeId) {
        if (!employeeMap.containsKey(employeeId)) {
            System.out.println("Error: employee with ID " + employeeId + " not found");
            return new ArrayList<>();
        }
        List<Task> result = new ArrayList<>();
        for (Task t : taskList) {
            if (t.getAssigneeId() == employeeId) {
                result.add(t);
            }
        }
        return result;
    }
    public List<Task> getTasksByStatus(TaskStatus status) {
        List<Task> result = new ArrayList<>();
        for (Task t : taskList) {
            if (t.getStatus() == status) {
                result.add(t);
            }
        }
        return result;
    }
    public List<Task> getHighPriorityTasks() {
        List<Task> result = new ArrayList<>();
        for (Task t : taskList) {
            if (t.getPriority() == Priority.HIGH) {
                result.add(t);
            }
        }
        return result;
    }
    public Map<TaskStatus, Integer> getTaskCountByStatus() {
        Map<TaskStatus, Integer> counts = new EnumMap<>(TaskStatus.class);
        for (TaskStatus status : TaskStatus.values()) {
            counts.put(status, 0);
        }
        for (Task t : taskList) {
            counts.put(t.getStatus(), counts.get(t.getStatus()) + 1);
        }
        return counts;
    }
    public Employee getMostBusyEmployee() {
        Employee result = null;
        int maxCount = 0;
        for (Employee e : employeeList) {
            int unfinished = 0;
            for (Task t : taskList) {
                if (t.getAssigneeId() == e.getId() && t.getStatus() != TaskStatus.DONE) {
                    unfinished++;
                }
            }
            if (unfinished > maxCount) {
                maxCount = unfinished;
                result = e;
            }
        }
        return result;
    }
    public List<Task> searchByTitle(String keyword) {
        List<Task> result = new ArrayList<>();
        String lowerKeyword = keyword.toLowerCase();
        for (Task t : taskList) {
            if (t.getTitle().toLowerCase().contains(lowerKeyword)) {
                result.add(t);
            }
        }
        return result;
    }
    public Employee findEmployeeById(int id) { return employeeMap.get(id); }
    public Task findTaskById(int id) { return taskMap.get(id); }
    public List<Employee> getAllEmployees() { return employeeList; }
    public List<Task> getAllTasks() { return taskList; }
}