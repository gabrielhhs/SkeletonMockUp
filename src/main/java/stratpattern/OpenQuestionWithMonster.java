package stratpattern;

import hints.HintProvider;
import rooms.TaskRoomWithMonster;

public class OpenQuestionWithMonster extends OpenQuestion {
    public OpenQuestionWithMonster(String question, String answer, HintProvider hint, TaskRoomWithMonster parent) {
        super(question, answer, parent, hint);
    }
    public OpenQuestionWithMonster(String question, String answer, TaskRoomWithMonster parent) {
        this(question, answer, null, parent);
    }

    @Override
    public void handleWrongAnswer() {
        TaskRoomWithMonster parent = (TaskRoomWithMonster) this.getParent();

        if (!parent.getMonster().isActive()) {
            super.handleWrongAnswer();
            parent.activateMonster();
            parent.getParent().getPlayer().damage(1);
        } else {
            System.out.println("Imagine failing twice. DIE!!");
            parent.getParent().getPlayer().kill();
        }
    }
}