package events.eventtypes;

import core.GameStatus;
import events.Event;

public class ReverseWeepingAngelEvent extends Event {
    private int startingBoundary;
    private int totalEntries = 0;

    public ReverseWeepingAngelEvent(int startingBoundary) {
        super(false);
        this.startingBoundary = startingBoundary;
    }

    @Override
    public void start() {
        System.out.println("In the corner of your eye you notice a shadowy figure standing behind you\nDo you turn around to see whats standing behind you? (Y/N)");
    }

    @Override
    public void end() {
        System.out.println("You decide its better to pray than to see what is behind you");
        this.parent.getParent().chooseRoom();
    }

    @Override
    public void consume(String input) {
        if (input.equalsIgnoreCase("Y")) {
            System.out.println("You quickly turn around, but before you can lay your eyes upon the figure the world goes black");
            this.parent.getParent().getParent().getPlayer().kill();
        } else this.end();
    }

    private void trackEnters() {
        this.totalEntries++;
        if (this.totalEntries == this.startingBoundary) {
            this.parent.getParent().getParent().getStatusManager().set(GameStatus.IN_EVENT);
            this.start();
        }
    }

    @Override
    public boolean canStart() {
        this.trackEnters();
        return super.canStart();
    }
}