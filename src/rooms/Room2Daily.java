package rooms;

import core.Game;

public class Room2Daily extends Room {

    public Room2Daily(Game game) {
        super(game);
    }

    @Override
    public void introductionText() {
        System.out.println("You have entered the Room of Daily Scrum Suffering.");
    }

    @Override
    void handleUncleared() {
        if (!this.taskHandler.startTask(this.task)){
            this.game.getPlayer().damage(1);
            //ToDo: summon monster
        }
    }
}