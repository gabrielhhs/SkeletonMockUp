package items;

import core.Game;
import core.Registerable;

public interface Item extends Registerable {
	void use(Game game);
	String getName();
}