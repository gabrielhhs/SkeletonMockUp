package events;

public abstract class Event {
    protected boolean startingConditionMet;
    protected EventHandler parent;

    protected Event(boolean startingConditionMet) {
        this.startingConditionMet = startingConditionMet;
    }

    public abstract void start();
    public abstract void consume(String input);

    public boolean canStart() {
        return this.startingConditionMet;
    }

    public void setParent(EventHandler parent) {
        this.parent = parent;
    }
}