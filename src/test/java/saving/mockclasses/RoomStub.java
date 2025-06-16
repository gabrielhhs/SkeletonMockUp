package saving.mockclasses;

import core.Game;
import rooms.Room;

public class RoomStub extends Room {
    public boolean onEnterCalled = false;
    public boolean handleUnclearedCalled = false;
    public boolean enterCalled = false;
    public boolean chooseRoomCalled = false;
    public boolean consumeCalled = false;
    public String lastConsumeInput;

    public RoomStub(Game parent, String name) {
        super(parent, name);
    }

    @Override
    protected void onEnter() {
        onEnterCalled = true;
        System.out.println("[MOCK] RoomStub.onEnter() called");
    }

    @Override
    protected void handleUncleared() {
        System.out.println("[MOCK] RoomStub.handleUncleared() called");
        handleUnclearedCalled = true;
    }

    @Override
    public void chooseRoom() {
        chooseRoomCalled = true;
    }

    @Override
    public void setParent(Game parent) {
        super.setParent(parent);
    }

    @Override
    public void consume(String input) {
        consumeCalled = true;
        lastConsumeInput = input;
    }

    public void addNeighbor(String direction, Room room) {
        this.putNeighboringRoom(direction, room);
    }

    public void simulateUnclearedState() {
        super.setCleared(false);
    }
    public void simulateClearedState() {
        super.setCleared(true);
    }
}

