package rooms;

import core.Game;

public class Outside extends Room {

    public Outside(Game parent, String name) {
        super(parent, name);
    }

    @Override
    protected void onEnter() {
        System.out.println("wow it looks like you are outside :O");
        hasBeenCleared();
    }

    @Override
    protected void handleUncleared() {
        //NoUnclearedTasksToHandle
    }
}