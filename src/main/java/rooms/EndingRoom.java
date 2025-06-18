package rooms;

import core.Game;

public class EndingRoom extends Room {
    public EndingRoom(Game game) {
        super(game, "The end");
    }

    @Override
    protected void onEnter() {
        System.out.printf("You won the game!\nFinal score: %d%n", this.parent.getPlayer().getScore());
        this.parent.stop();
    }

    @Override
    protected void handleUncleared() {
        // No op
    }
}