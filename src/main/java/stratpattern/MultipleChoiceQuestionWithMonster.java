package stratpattern;

import hints.HintProvider;
import rooms.TaskRoomWithMonster;

public class MultipleChoiceQuestionWithMonster extends MultipleChoiceQuestion {
    public MultipleChoiceQuestionWithMonster(String question, String[] options, int answer, HintProvider hint, TaskRoomWithMonster room) {
        super(question, options, answer, hint, room);
    }
    public MultipleChoiceQuestionWithMonster(String question, String[] options, int answer, TaskRoomWithMonster room) {
        this(question, options, answer, null, room);
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