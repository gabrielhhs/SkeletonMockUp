package mobs;

import rooms.TaskRoomWithMonster;
import stratpattern.Task;

public class QuestionMonster{
    private String name;
    private Task task;
    private TaskRoomWithMonster parent;

    public QuestionMonster(String name, Task task, TaskRoomWithMonster parent) {
        this.name = name;
        this.task = task;
        this.parent = parent;
    }

    public void activate() {
        System.out.printf("[%s] YOU HAVE ACTIVATED MY TRAP CARD%nPrepare to face the consequences", this.name);
        this.parent.getTaskHandler().setTask(this.task);
        this.parent.handleUncleared();
    }
}