package saving.mockclasses;

import core.Game;
import saving.DataSaver;

import java.util.Set;

class DataSaverStub implements DataSaver {
    @Override
    public void save(Game game, String saveName) {}
    @Override
    public void load(Game game, String saveName) {}
    @Override
    public Set<String> getSaves() { return Set.of(); }
    @Override
    public boolean saveExists(String saveName) { return false; }
}