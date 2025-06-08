package events;

public abstract class Event {
    protected boolean startingConditionMet;

    protected Event(boolean startingConditionMet) {
        this.startingConditionMet = startingConditionMet;
    }

    public abstract void start(EventHandler parent);
    public abstract void consume(String input);

    public boolean canStart() {
        return this.startingConditionMet;
    }
}