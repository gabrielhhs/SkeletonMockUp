package stratpattern;

import hints.HintProvider;
import rewards.RewardProvider;
import rooms.TaskRoomWithMonster;

public class OpenQuestionWithMonster extends OpenQuestion {
    public OpenQuestionWithMonster(HintProvider hint, TaskRoomWithMonster parent, RewardProvider reward, String question, String... answers) {
        super(parent, hint, reward, question, answers);
    }
    public OpenQuestionWithMonster(TaskRoomWithMonster parent, RewardProvider reward, String question, String... answers) {
        this(null, parent, reward, question, answers);
    }
    public OpenQuestionWithMonster(HintProvider hint,TaskRoomWithMonster parent, String question, String... answers) {
        this(hint, parent, null, question, answers);
    }
    public OpenQuestionWithMonster(TaskRoomWithMonster parent, String question, String... answers) {
        this(null, parent, null, question, answers);
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