package rooms;

import core.Game;
import stratpattern.Task;
import stratpattern.TaskHandler;

public class Room2Daily extends Room {
    private TaskHandler taskHandler = new TaskHandler();
    //ToDo: assign task to the taskHandler in DataSeeder

    public Room2Daily(Game game) {
        super(game);
    }

    @Override
    public void introductionText() {
        System.out.println("""
                HAHAHA you got through the first room!
                This question is going to be a little bit harder.
                BUT if you dont answer it correctly you will have to suffer for the rest of your live!
                """);
    }

    @Override
    void handleUncleared() {
        if (!this.taskHandler.startTask()){
            this.game.getPlayer().damage(1);
            //ToDo: summon monster
        }
    }
}