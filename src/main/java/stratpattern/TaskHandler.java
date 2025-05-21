package stratpattern;

public class TaskHandler {
    private Task task;

    
    public TaskHandler() {};
    public TaskHandler(Task task) {
        this.task = task;
    }

    public boolean startTask(Task task) {
        return task.start();
    }

    public boolean startTask() {
        if (this.task != null) return this.task.start();
        return false;
    }
}