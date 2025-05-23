package rooms;

import core.Game;
import core.RoomStatus;
import stratpattern.TaskHandler;

public class TaskRoom extends Room{
    private TaskHandler taskHandler = new TaskHandler();
    //ToDo: Assign task to taskhandler

    public TaskRoom(Game game, String name) {
        super(game, name);
    }

    @Override
    protected void onEnter() {
        System.out.println("Welcome to the " + this.name + " room");
    }

    @Override
    protected void handleUncleared() {
        if (!this.cleared) {
            RoomStatus.IN_TASK.setTrue();
            this.taskHandler.startTask();
        }
    }

    public TaskHandler getTaskHandler() {
        return this.taskHandler;
    }
}