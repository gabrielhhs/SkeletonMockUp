package saving.mockclasses;

import core.Player;
import rooms.Room;


public class PlayerSpy extends Player {
    public boolean setCurrentRoomCalled = false;

    public PlayerSpy(Room room) {
        super(room);
    }

    @Override
    public void setCurrentRoom(Room currentRoom) {
        System.out.println("Player.setCurrentRoom Called.");
        this.setCurrentRoomCalled = true;
        super.setCurrentRoom(currentRoom);
    }
}
