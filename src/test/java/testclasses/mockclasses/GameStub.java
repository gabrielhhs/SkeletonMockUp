package testclasses.mockclasses;

import core.Game;
import core.GameStatus;
import core.Player;
import core.StatusManager;
import rooms.Room;

import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

public class GameStub extends Game {
    public boolean startCalled = false;
    public boolean stopCalled = false;
    public boolean saveCalled = false;
    public boolean loadCalled = false;
    public boolean playerCalled = false;
    public String lastSaveName;
    public String lastLoadName;

    public Room testInitialRoom;
    public Player testPlayer;
    public StatusManager testStatusManager;
    public Set<Room> testRooms = new HashSet<>();
    public boolean runningState = false;

    public GameStub(PlayerSpy player) {
        super(new GameStub.EmptyInputStream(), new DataSaverStub());
        this.testPlayer = player;
    }

    public GameStub() {
        super(new GameStub.EmptyInputStream(), new DataSaverStub());
        this.testPlayer = new Player(testInitialRoom);
    }

    public void setTestStatusManager(StatusManager testStatusManager) {
        this.testStatusManager = testStatusManager;
    }

    @Override
    public void start() {
        this.startCalled = true;
        this.runningState = true;
    }

    @Override
    public void stop() {
        this.stopCalled = true;
        this.runningState = false;
    }

    @Override
    public Room getInitialRoom() {
        return this.testInitialRoom;
    }

    @Override
    public Player getPlayer() {
        this.playerCalled = true;
        return this.testPlayer;
    }

    @Override
    public StatusManager getStatusManager() {
        return this.testStatusManager;
    }

    @Override
    public Set<Room> collectRooms() {
        return this.testRooms;
    }

    @Override
    public void save(String saveName) {
        this.saveCalled = true;
        this.lastSaveName = saveName;
    }

    @Override
    public void load(String saveName) {
        this.loadCalled = true;
        this.lastLoadName = saveName;
    }

    @Override
    public void onDeath(Player player) {
        this.stop();
    }

    public void setCurrentRoom(Room room) {
        this.testPlayer.setCurrentRoom(room);
    }

    public void setGameStatus(GameStatus status) {
        this.testStatusManager.set(status);
    }

    public void addTestRoom(Room room) {
        this.testRooms.add(room);
    }

    private static class EmptyInputStream extends InputStream {
        @Override
        public int read() {
            return -1;
        }
    }
}