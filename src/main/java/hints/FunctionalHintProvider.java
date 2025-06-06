
package hints;

import rooms.Room;

public class FunctionalHintProvider implements HintProvider {
    private String hint;
    private Room room;

    public FunctionalHintProvider(String hint, Room room) {
        this.hint = hint;
        this.room = room;
    }

    @Override
    public String getHint() {
        return this.hint;
    }

    public Room getRoom() {
        return this.room;
    }
}