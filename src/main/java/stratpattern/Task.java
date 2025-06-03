package stratpattern;

import rooms.TaskRoom;

public abstract class Task {
    public abstract void start();
    public abstract void consume(String input);

    private final TaskRoom parent;

    public Task(TaskRoom parent) {
        this.parent = parent;
    }

    public final void setCleared() {
        this.parent.hasBeenCleared();
        this.parent.chooseRoom();
    }

    public final TaskRoom getParent() {
        return this.parent;
    }
}