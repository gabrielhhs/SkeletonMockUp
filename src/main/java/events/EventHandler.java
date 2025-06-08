package events;

import rooms.SpecialEventRoom;

public class EventHandler {
    private Event event;
    private SpecialEventRoom parent;

    public EventHandler(SpecialEventRoom parent) {
        this.parent = parent;
    }
    public EventHandler(SpecialEventRoom parent, Event event) {
        this(parent);
        this.event = event;
    }

    public void start() {
        if (this.event != null) this.event.start(this);
    }
    public void start(Event event) {
        this.setEvent(event);
        this.start();
    }

    public void consume(String input) {
        if (this.event != null) this.event.consume(input);
    }

    public void setEvent(Event event) {
        this.event = event;
    }
    public Event getEvent() {
        return this.event;
    }

    public SpecialEventRoom getParent() {
        return this.parent;
    }
}