package rooms;

import core.Game;
import core.RoomStatus;

import java.util.HashMap;
import java.util.Map;

public abstract class Room {
    protected Game parent;
    protected String name;
    protected boolean cleared = false;
    protected final Map<String, Room> neighboringRooms = new HashMap<>();

    protected Room(Game parent, String name) {
        this.parent = parent;
        this.name = name;
    }

    protected abstract void onEnter();
    protected abstract void handleUncleared();

    public void chooseRoom() {
        RoomStatus.SELECTING_ROOM.activate();
        System.out.println("Choose which way you want to go:");
        for (Map.Entry<String, Room> entry : this.neighboringRooms.entrySet()) {
            System.out.println(entry.getKey());
        }
    }

    public final void enter() {
        this.onEnter();
        if (this.cleared) {
            this.chooseRoom();
        } else {
            this.handleUncleared();
        }
    }

    public Game getParent() {
        return this.parent;
    }
    public void setParent(Game parent) {
        this.parent = parent;
    }

    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public boolean isCleared() {
        return this.cleared;
    }
    public void setCleared() {
        this.cleared = true;
    }

    public Map<String, Room> getNeighboringRooms() {
        return this.neighboringRooms;
    }

    public void putNeighboringRoom(String direction, Room room) {
        this.neighboringRooms.put(direction, room);
    }
}