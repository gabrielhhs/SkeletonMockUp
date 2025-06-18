package rooms;

import core.Game;

public class EndingRoom extends Room {
    public EndingRoom(Game game) {
        super(game, "The end");
    }

    @Override
    protected void onEnter() {
        System.out.println("You won the game! You can keep playing if you want");
    }

    @Override
    protected void handleUncleared() {
        // No op
    }
}