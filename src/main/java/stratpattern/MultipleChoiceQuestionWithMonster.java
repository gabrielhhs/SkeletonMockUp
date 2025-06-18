package stratpattern;

import hints.HintProvider;
import rewards.RewardProvider;
import rooms.TaskRoomWithMonster;

public class MultipleChoiceQuestionWithMonster extends MultipleChoiceQuestion {
    public MultipleChoiceQuestionWithMonster(String question, String[] options, HintProvider hint, TaskRoomWithMonster room, RewardProvider reward) {
        super(question, options, hint, room, reward);
    }
    public MultipleChoiceQuestionWithMonster(String question, String[] options, TaskRoomWithMonster room, RewardProvider reward) {
        this(question, options, null, room, reward);
    }
    public MultipleChoiceQuestionWithMonster(String question, String[] options, HintProvider hint, TaskRoomWithMonster room) {
        this(question, options, hint, room, null);
    }
    public MultipleChoiceQuestionWithMonster(String question, String[] options, TaskRoomWithMonster room) {
        this(question, options, null, room, null);
    }

    @Override
    public void handleWrongAnswer() {
        TaskRoomWithMonster parent = (TaskRoomWithMonster) this.getParent();

        if (!parent.getMonster().isActive()) {
            super.handleWrongAnswer();
            parent.activateMonster();
            this.getParent().getParent().getPlayer().damage(1);
        } else {
            System.out.println("Imagine failing twice. DIE!!");
            this.getParent().getParent().getPlayer().kill();
        }
    }
}