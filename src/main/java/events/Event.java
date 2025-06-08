package events;

public abstract class Event {
    private boolean activeOnEnter;

    protected Event(boolean activateOnEnter) {
        this.activeOnEnter = activateOnEnter;
    }

    public abstract void start(EventHandler parent);
    public abstract void consume(String input);

    public boolean isActiveOnEnter() {
        return this.activeOnEnter;
    }
}