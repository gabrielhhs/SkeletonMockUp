package Core;

import Rooms.*;

public class Game {
    Room roomPlanning = new Room1Planning();
    Room roomDaily = new Room2Daily();

    Player player = new Player(roomPlanning, 10);

    public Game() {
        roomPlanning.setGame(this);
        roomDaily.setGame(this);
    }

    public void start() {
        roomPlanning.enter();
    }

    public void goNext(Room room) {
        room.enter();
        player.setPosition(room);
    }
}
