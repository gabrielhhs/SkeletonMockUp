package items.consumables;

import core.Player;
import items.Item;
import rooms.Room;
import rooms.TaskRoomWithMonster;

public class SwordItem extends ConsumableItem {
    @Override
    public void use(Player player, Room room) {
        if (canUse(room)) { room.setCleared(); this.consumeItem(player); }
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