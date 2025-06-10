package rooms;

import core.Game;
import events.Event;
import events.EventHandler;

public class SpecialEventRoom extends Room {
    private final EventHandler eventHandler = new EventHandler(this);

    public SpecialEventRoom(Game parent, String name, Event event) {
        super(parent, name);
        this.eventHandler.setEvent(event);
    }

    @Override
    protected void onEnter() {
        System.out.println("Welcome to the " + this.name + " room");
    }

    @Override
    protected void handleUncleared() {
        if (this.eventHandler.getEvent() != null && this.eventHandler.getEvent().canStart()) this.eventHandler.start();
    }

    public EventHandler getEventHandler() {
        return this.eventHandler;
    }
}