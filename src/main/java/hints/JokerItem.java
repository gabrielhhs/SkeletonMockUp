package hints;

import core.Player;
import items.Item;
import rooms.Room;
import rooms.TaskRoom;
import stratpattern.QuestionTask;

public class JokerItem implements Item {

    @Override
    public void use(Player player, Room room) {
        if (room instanceof TaskRoom taskRoom && !taskRoom.isCleared() && taskRoom.getTaskHandler().getTask() instanceof QuestionTask task) {
            task.askHint("Y");
            player.takeItem(this);
        } else System.out.println("Nothing happened");
    }

    @Override
    public String getName() {
        return "The Joker";
    }
}