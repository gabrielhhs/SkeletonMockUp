package testclasses.mockclasses;

import core.Game;
import rooms.Room;

public class RoomSpy extends Room {
    public boolean onEnterCalled = false;
    public boolean handleUnclearedCalled = false;
    public boolean enterCalled = false;
    public boolean chooseRoomCalled = false;
    public boolean consumeCalled = false;
    public String lastConsumeInput;

    public RoomSpy(Game parent, String name) {
        super(parent, name);
    }

    @Override
    protected void onEnter() {
        this.onEnterCalled = true;
        System.out.println("RoomStub.onEnter() called");
    }

    @Override
    protected void handleUncleared() {
        System.out.println("RoomStub.handleUncleared() called");
        this.handleUnclearedCalled = true;
    }

    @Override
    public void chooseRoom() {
        this.chooseRoomCalled = true;
    }

    @Override
    public void setParent(Game parent) {
        super.setParent(parent);
    }

    @Override
    public void consume(String input) {
        this.consumeCalled = true;
        this.lastConsumeInput = input;
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

