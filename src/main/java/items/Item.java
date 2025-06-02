package items;

import core.Player;
import rooms.Room;

public interface Item {
	void use(Player player, Room room);
	String getName();
}