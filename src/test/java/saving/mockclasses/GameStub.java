package saving.mockclasses;

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
    public boolean processInputCalled = false;
    public boolean saveCalled = false;
    public boolean loadCalled = false;
    public boolean playerCalled = false;
    public String lastProcessedInput;
    public String lastSaveName;
    public String lastLoadName;

    // Configurable test data
    public Room testInitialRoom;
    public Player testPlayer;
    public StatusManager testStatusManager;
    public Set<Room> testRooms = new HashSet<>();
    public boolean runningState = false;

    public GameStub(PlayerStub player) {
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
        startCalled = true;
        runningState = true;
    }

    @Override
    public void stop() {
        stopCalled = true;
        runningState = false;
    }

    @Override
    public Room getInitialRoom() {
        return testInitialRoom;
    }

    @Override
    public Player getPlayer() {
        playerCalled = true;
        return testPlayer;
    }

    @Override
    public StatusManager getStatusManager() {
        return testStatusManager;
    }

    @Override
    public Set<Room> collectRooms() {
        return testRooms;
    }

    @Override
    public void save(String saveName) {
        saveCalled = true;
        lastSaveName = saveName;
    }

    @Override
    public void load(String saveName) {
        loadCalled = true;
        lastLoadName = saveName;
    }

    @Override
    public void onDeath(Player player) {
        stop();
    }

    // Test helper methods
    public void setCurrentRoom(Room room) {
        this.testPlayer.setCurrentRoom(room);
    }

    public void setGameStatus(GameStatus status) {
        this.testStatusManager.set(status);
    }

    public void addTestRoom(Room room) {
        this.testRooms.add(room);
    }

    // Stub implementations of dependencies
    private static class EmptyInputStream extends InputStream {
        @Override
        public int read() {
            return -1; // EOF
        }
    }
}