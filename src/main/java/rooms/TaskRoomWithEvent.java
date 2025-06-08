package rooms;

import core.Game;
import events.EventHandler;

public class TaskRoomWithEvent extends TaskRoom {
    private EventHandler eventHandler = new EventHandler(this);

    public TaskRoomWithEvent(Game game, String name) {
        super(game, name);
    }

    @Override
    public void onEnter() {
        System.out.println("Welcome to the " + this.name + " room");
        if (this.eventHandler.getEvent() != null && this.eventHandler.getEvent().canStart()) this.eventHandler.start();
    }

    public EventHandler getEventHandler() {
        return this.eventHandler;
    }
}