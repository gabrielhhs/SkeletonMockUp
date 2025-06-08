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
        if (this.eventHandler.getEvent() != null && this.eventHandler.getEvent().canStart()) this.eventHandler.start();
    }

    @Override
    protected void handleUncleared() {
        //No existing uncleared tasks (possible)child classes should implement this
    }

    public EventHandler getEventHandler() {
        return this.eventHandler;
    }
}