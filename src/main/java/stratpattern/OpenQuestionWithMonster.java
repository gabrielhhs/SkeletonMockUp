package stratpattern;

import hints.HintProvider;
import rewards.RewardProvider;
import rooms.TaskRoomWithMonster;

public class OpenQuestionWithMonster extends OpenQuestion {
    public OpenQuestionWithMonster(String question, String answer, HintProvider hint, TaskRoomWithMonster parent, RewardProvider reward) {
        super(question, answer, parent, hint, reward);
    }
    public OpenQuestionWithMonster(String question, String answer, TaskRoomWithMonster parent, RewardProvider reward) {
        this(question, answer, null, parent, reward);
    }
    public OpenQuestionWithMonster(String question, String answer, HintProvider hint,TaskRoomWithMonster parent) {
        this(question, answer, hint, parent, null);
    }
    public OpenQuestionWithMonster(String question, String answer, TaskRoomWithMonster parent) {
        this(question, answer, null, parent, null);
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