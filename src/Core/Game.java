package Core;

import Rooms.*;

import javax.xml.crypto.Data;
import java.util.List;
import java.util.Set;

public class Game {
    Room room = DataSeeder.seed(this);

    Player player = new Player(room);
    //TODO: /\ moet overgezet worden in de DataSeeder.class

    public void start() {
        roomPlanning.enter();
    }

    public void goNext(Room room) {
        room.enter();
        player.setPosition(room);
    }
}
