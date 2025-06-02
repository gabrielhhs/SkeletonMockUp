package entities;

import rooms.TaskRoomWithMonster;
import stratpattern.Task;

public class QuestionMonster {
    private String name;
    private Task task;
    private TaskRoomWithMonster parent;
    private boolean active = false;

    public QuestionMonster(String name, Task task) {
        this.name = name;
        this.task = task;
    }
    public QuestionMonster(String name) {
        this.name = name;
    }

    public void activate() {
        System.out.printf("[%s] YOU HAVE ACTIVATED MY TRAP CARD%nPrepare to face the consequences%n", this.name);
        this.parent.getTaskHandler().setTask(this.task);
        this.active = true;
        this.parent.handleUncleared();
    }

    public void setParent(TaskRoomWithMonster parent) {
        this.parent = parent;
    }
    public void setTask(Task task) {
        this.task = task;
    }

    public boolean isActive() {
        return this.active;
    }
}