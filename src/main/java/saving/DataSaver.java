package saving;

import core.Game;

import java.util.Set;

public interface DataSaver {
	void save(Game game, String saveName) throws SaverException;
	void load(Game game, String saveName) throws SaverException;
	Set<String> getSaves();
	boolean saveExists(String saveName);
}