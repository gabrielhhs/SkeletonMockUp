package rooms;

import core.Game;
import mobs.QuestionMonster;

public class TaskRoomWithMonster extends TaskRoom{
    private QuestionMonster monster;
    //ToDo: Assign task to taskhandler
    //ToDo: Assign new Question to Monster and assign Monster to 'this'

    public TaskRoomWithMonster(Game game, String name) {
        super(game, name);
    }

    public void setMonster(QuestionMonster monster) {
        this.monster = monster;
    }
    public QuestionMonster getMonster() {
        return this.monster;
    }

    public void activateMonster() {
        this.monster.activate();
    }
}