package rooms;

import core.Game;

import java.util.HashMap;

public class SideRoom extends Room {
    public SideRoom(Game game) {
        super(game);
        super.neighboringRooms = new HashMap<>();
    }

    @Override
    void introductionText() {
        System.out.println("you have gotten to the sideRoom");
    }

    @Override
    void question() {

    }

    @Override
    void answerCheck() {

    }

    @Override
    void result() {

    }
}