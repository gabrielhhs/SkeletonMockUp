package saving.mockclasses;

import core.Player;
import rooms.Room;


public class PlayerStub extends Player {
    public boolean setCurrentRoomCalled = false;

    public PlayerStub(Room room) {
        super(room);
    }

    @Override
    public void setCurrentRoom(Room currentRoom) {
        System.out.println("[STUB] setCurrentRoom Called.");
        this.setCurrentRoomCalled = true;
    }
}
