package stratpattern;

public class TaskHandler {
    private Task task;

    public TaskHandler() {
    }

    public TaskHandler(Task task) {
        this.task = task;
    }

    public void startTask(Task task) {
        task.start();
    }

    public void startTask() {
        if (this.task != null) this.task.start();
    }

    public void consume(String input) {
        if (this.task != null) this.task.consume(input);
    }

    public void setTask(Task task) {
        this.task = task;
    }
}