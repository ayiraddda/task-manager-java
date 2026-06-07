package model;
public class Task {
    private int id;
    private String title;
    private String description;
    private TaskStatus status;
    private Priority priority;
    private int assigneeId;   
    private int projectId;
    public Task(int id, String title, String description,
                TaskStatus status, Priority priority, int projectId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.assigneeId = 0;
        this.projectId = projectId;
    }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public TaskStatus getStatus() { return status; }
    public void setStatus(TaskStatus status) { this.status = status; }
    public Priority getPriority() { return priority; }
    public void setPriority(Priority priority) { this.priority = priority; }
    public int getAssigneeId() { return assigneeId; }
    public void setAssigneeId(int assigneeId) { this.assigneeId = assigneeId; }
    public int getProjectId() { return projectId; }
    public void setProjectId(int projectId) { this.projectId = projectId; }
    @Override
    public String toString() {
        String assignee = (assigneeId == 0) ? "unassigned" : "employee #" + assigneeId;
        return "Task{id=" + id
                + ", title='" + title + "'"
                + ", status=" + status
                + ", priority=" + priority
                + ", assignee=" + assignee
                + ", projectId=" + projectId + "}";
    }
}