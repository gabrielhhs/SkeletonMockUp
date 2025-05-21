package core;

import rooms.*;

import javax.xml.crypto.Data;
import java.util.List;
import java.util.Set;

public class Game {
    Room currentRoom = DataSeeder.seed(this);

    Player player = new Player(currentRoom);
    //TODO: /\ moet overgezet worden in de DataSeeder.class

    public void start() {
        this.currentRoom.enter();
    }

    public void goNext(Room room) {
        room.enter();
        player.setPosition(room);
    }

    public Player getPlayer(){
        return this.player;
    }
}