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
        System.out.println("[Mysterious voice] Are you having doubts about the glory of the Scrum methodology?\n(Y/N)");
    }

    @Override
    public void end() {
        System.out.println("[Mysterious voice] Good.");
        this.parent.getParent().chooseRoom();
    }

    @Override
    public void consume(String input) {
        if (input.equalsIgnoreCase("Y")) {
            System.out.println("[Mysterious voice] Well then I have no use for you");
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