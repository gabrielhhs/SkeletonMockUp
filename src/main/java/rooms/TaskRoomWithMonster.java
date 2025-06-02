package rooms;

import core.Game;
import entities.QuestionMonster;

public class TaskRoomWithMonster extends TaskRoom {
    private QuestionMonster monster;

    public TaskRoomWithMonster(Game game, String name, QuestionMonster monster) {
        super(game, name);
        this.monster = monster;
        this.monster.setParent(this);
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