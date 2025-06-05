package core.hints;

import rooms.Room;

public class FunctionalHint implements Hint {
    private String hint;
    private Room room;

    public FunctionalHint(String hint, Room room) {
        this.hint = hint;
        this.room = room;
    }

    @Override
    public String getHint() {
        return this.hint;
    }

    public Room getRoom() {
        return room;
    }
}
