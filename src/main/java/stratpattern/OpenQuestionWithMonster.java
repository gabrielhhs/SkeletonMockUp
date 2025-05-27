package stratpattern;

import rooms.TaskRoomWithMonster;

public class OpenQuestionWithMonster extends OpenQuestion {
    private boolean monsterActive = false;

    public OpenQuestionWithMonster(String question, String answer, TaskRoomWithMonster parent) {
        super(question, answer, parent);
    }

    @Override
    public void consume(String input) {
        if (input.equalsIgnoreCase(this.answer)) {
            this.parent.setCleared();
            this.parent.chooseRoom();
        } else if (!monsterActive) {
            handleFirstWrongAnswer();
        } else {
            handleSecondWrongAnswer();
        }
    }

    private void handleFirstWrongAnswer() {
        System.out.println("You have failed you feel something something being taken away from your soul");
        this.parent.getParent().getPlayer().damage(1);
        this.parent.getParent().getPlayer().removeScore(10);
        ((TaskRoomWithMonster) this.parent).activateMonster();
    }
    private void handleSecondWrongAnswer() {
        System.out.println("Imagine failing twice. DIE!!");
        this.parent.getParent().getPlayer().kill();
    }


}