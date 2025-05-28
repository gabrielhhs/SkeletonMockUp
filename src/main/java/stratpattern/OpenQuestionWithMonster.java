package stratpattern;

import rooms.TaskRoomWithMonster;

public class OpenQuestionWithMonster extends OpenQuestion {
    private boolean monsterActive = false;

    public OpenQuestionWithMonster(String question, String answer, TaskRoomWithMonster parent) {
        super(question, answer, parent);
    }

    @Override
    public void consume(String input) {
        if (input.equalsIgnoreCase(this.answer)) super.handleCorrectAnswer();
        else if (!monsterActive) { super.handleWrongAnswer(); ((TaskRoomWithMonster) this.parent).activateMonster(); this.monsterActive = true; } //Multiline or naw? I like this
        else {
            System.out.println("Imagine failing twice. DIE!!");
            this.parent.getParent().getPlayer().kill();
        }
    }
}