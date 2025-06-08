package events;

import rooms.Room;

public class EventHandler {
    private Event event;
    private Room parent;

    public EventHandler(Room parent) {
        this.parent = parent;
    }
    public EventHandler(Room parent, Event event) {
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

    public Room getParent() {
        return this.parent;
    }
}