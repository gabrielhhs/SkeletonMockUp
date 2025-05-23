/*
package rooms;

import stratpattern.TaskHandler;

public class Room2Daily extends Room {
    private TaskHandler taskHandler = new TaskHandler();
    //ToDo: assign task to the taskHandler in DataSeeder

    public Room2Daily(Game game) {
        super(game);
    }

    @Override
    public void introductionText() {
        System.out.println("You have entered the Room of Daily Scrum Suffering.");
    }

    @Override
    void handleUncleared() {
        if (!this.taskHandler.startTask()){
            this.game.getPlayer().damage(1);
            //ToDo: summon monster
        }
    }
}*/
