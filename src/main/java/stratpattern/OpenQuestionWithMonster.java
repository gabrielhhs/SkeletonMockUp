package stratpattern;

import rooms.TaskRoomWithMonster;

public class OpenQuestionWithMonster extends OpenQuestion {

    public OpenQuestionWithMonster(String question, String answer, TaskRoomWithMonster parent) {
        super(question, answer, parent);
    }

    @Override
    public void consume(String input) {
        TaskRoomWithMonster parent = (TaskRoomWithMonster) this.getParent();
        if (input.equalsIgnoreCase(this.answer)) super.handleCorrectAnswer();
        else if (!parent.getMonster().isActive()) {
            super.handleWrongAnswer();
            parent.activateMonster();
            this.parent.getParent().getPlayer().damage(1);
        } else {
            System.out.println("Imagine failing twice. DIE!!");
            this.parent.getParent().getPlayer().kill();
        }
    }
}