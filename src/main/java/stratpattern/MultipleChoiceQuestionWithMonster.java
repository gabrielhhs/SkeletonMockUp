package stratpattern;

import rooms.TaskRoomWithMonster;

public class MultipleChoiceQuestionWithMonster extends MultipleChoiceQuestion {
    public MultipleChoiceQuestionWithMonster(String question, String[] options, int answer, TaskRoomWithMonster room) {
        super(question, options, answer, room);
    }

    @Override
    public void consume(String input) {
        TaskRoomWithMonster parent = (TaskRoomWithMonster) this.getParent();
        if (input.matches("\\d+")) {
            if (Integer.parseInt(input) == this.answer) super.handleCorrectAnswer();
            else if (!parent.getMonster().isActive()) {
                super.handleWrongAnswer();
                parent.activateMonster();
                this.parent.getParent().getPlayer().damage(1);
            } else {
                System.out.println("Imagine failing twice. DIE!!");
                this.parent.getParent().getPlayer().kill();
            }
        } else {
            System.out.println("Choose a correct option number or DIE!! (pretty please)");
        }
    }
}