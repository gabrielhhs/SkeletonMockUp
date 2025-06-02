package rooms;

import core.Game;
import core.RoomStatus;
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
            RoomStatus.IN_TASK.activate();
            this.taskHandler.startTask();
    }

    public TaskHandler getTaskHandler() {
        return this.taskHandler;
    }
}