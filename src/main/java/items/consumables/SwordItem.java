package items.consumables;

import core.Game;
import core.Player;
import items.Item;
import rooms.Room;
import rooms.TaskRoomWithMonster;

public class SwordItem implements Item {
    @Override
    public void use(Game game) {
        Player player = game.getPlayer();
        Room room = player.getCurrentRoom();

        if (canUse(room)) { room.setCleared(); player.takeItem(this); }
        else System.out.println("Nothing happened...");
    }

    private boolean canUse(Room room) {
        return (room instanceof TaskRoomWithMonster roomWithMonster && roomWithMonster.getMonster().isActive());
    }

    @Override
    public String getName() {
        return "Sword of Monster Smashing";
    }
}