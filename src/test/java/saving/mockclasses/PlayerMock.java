package saving.mockclasses;

import core.Player;
import rooms.Room;


public class PlayerMock extends Player {
    public boolean setCurrentRoomCalled = false;

    public PlayerMock(Room room) {
        super(room);
    }

    @Override
    public void setCurrentRoom(Room currentRoom) {
        System.out.println("Player.setCurrentRoom Called.");
        this.setCurrentRoomCalled = true;
        super.setCurrentRoom(currentRoom);
    }
}
