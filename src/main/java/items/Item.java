package items;

import core.Game;
import core.Player;
import rooms.Room;

public interface Item {
	void use(Game game);
	String getName();
}