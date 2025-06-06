package rooms;

import core.Game;
import core.GameStatus;
import stratpattern.TaskHandler;

public class TaskRoom extends Room {
    private final TaskHandler taskHandler = new TaskHandler();

    public TaskRoom(Game game, String name) {
        super(game, name);
    }

    @Override
    public void onEnter() {
        System.out.println("Welcome to the " + this.name + " room");
    }

    @Override
    public void handleUncleared() {
        this.parent.getStatusManager().set(GameStatus.IN_TASK);
        this.taskHandler.startTask();
    }

    public TaskHandler getTaskHandler() {
        return this.taskHandler;
    }
}