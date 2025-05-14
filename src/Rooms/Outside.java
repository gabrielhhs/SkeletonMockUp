package Rooms;

import Core.Game;

import java.util.HashMap;

public class Outside extends Room{
    public Outside(Game game) {
        super(game);
        super.neighboringRooms = new HashMap<>();
    }

    void introductionText() {
    }

    void question() {

    }

    void answerCheck() {
        correct = true;
    }

    void result() {
    }
}
