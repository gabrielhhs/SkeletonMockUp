package items;

import core.Game;

public interface Item {
	void use(Game game);
	String getName();
}