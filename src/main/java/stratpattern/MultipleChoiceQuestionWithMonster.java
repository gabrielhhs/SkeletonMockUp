package stratpattern;

import hints.HintProvider;
import rewards.RewardProvider;
import rooms.TaskRoomWithMonster;

public class MultipleChoiceQuestionWithMonster extends MultipleChoiceQuestion {
    public MultipleChoiceQuestionWithMonster(String question, String[] options, int answer, HintProvider hint, TaskRoomWithMonster room, RewardProvider reward) {
        super(question, options, answer, hint, room, reward);
    }
    public MultipleChoiceQuestionWithMonster(String question, String[] options, int answer, TaskRoomWithMonster room, RewardProvider reward) {
        this(question, options, answer, null, room, reward);
    }
    public MultipleChoiceQuestionWithMonster(String question, String[] options, int answer, HintProvider hint, TaskRoomWithMonster room) {
        this(question, options, answer, hint, room, null);
    }
    public MultipleChoiceQuestionWithMonster(String question, String[] options, int answer, TaskRoomWithMonster room) {
        this(question, options, answer, null, room, null);
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