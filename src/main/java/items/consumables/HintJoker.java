package items.consumables;

import core.Game;
import items.Item;
import rooms.TaskRoom;
import stratpattern.QuestionTask;

public class HintJoker implements Item {
    @Override
    public void use(Game game) {
        if (game.getPlayer().getCurrentRoom() instanceof TaskRoom taskRoom && !taskRoom.isCleared() && taskRoom.getTaskHandler().getTask() instanceof QuestionTask task) {
            task.askHint("Y");
            game.getPlayer().takeItem(this);
        } else System.out.println("Nothing happened");
    }

    @Override
    public String getName() {
        return "Clue Joker";
    }
}