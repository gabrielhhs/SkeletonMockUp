package rooms;

import core.Game;
import core.RoomStatus;

import java.util.HashMap;
import java.util.Map;

public abstract class Room {
    protected Game parent;
    protected String name;
    protected boolean cleared;
    protected Map<String, Room> neighboringRooms = new HashMap<>();

    protected Room(Game parent, String name) {
        this.parent = parent;
        this.name = name;
    }

    protected abstract void onEnter();
    protected abstract void handleUncleared();

    public void chooseRoom() {
        RoomStatus.SELECTING_ROOM.setTrue();
        System.out.println("Choose which way you want to go:");
        for (var entry : this.neighboringRooms.entrySet()) {
            if(entry != null) System.out.println(entry.getKey());
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
        return parent;
    }
    public void setParent(Game parent) {
        this.parent = parent;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public boolean isCleared() {
        return cleared;
    }
    public void setCleared() {
        this.cleared = true;
    }

    public Map<String, Room> getNeighboringRooms() {
        return this.neighboringRooms;
    }
    public void setNeighboringRooms(Map<String, Room> neighbors) {
        this.neighboringRooms = neighbors;
    }

    public void addNeighboringRoom(String direction, Room room) {
        this.neighboringRooms.put(direction, room);
    }
}