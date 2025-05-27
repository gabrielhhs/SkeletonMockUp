package stratpattern;

import core.RoomStatus;
import rooms.TaskRoomWithMonster;

public class MultipleChoiceQuestionWithMonster extends MultipleChoiceQuestion {
    private boolean monsterActive = false;
    public MultipleChoiceQuestionWithMonster(String question, String[] options, int answer, TaskRoomWithMonster room) {
        super(question, options, answer, room);
    }

    @Override
    public void consume(String input) {
        if (!input.matches("\\d+")) {
            System.out.println("Choose a correct option number or DIE!! (pretty please)");
            return;
        }

        int answer = Integer.parseInt(input);
        if (answer == this.answer) {
            System.out.println("Well done you may live");
            this.parent.setCleared();
            this.parent.chooseRoom();
        } else if (!monsterActive) {
            handleFirstWrongAnswer();
        } else {
            handleSecondWrongAnswer();
        }
    }

    private void handleFirstWrongAnswer() {
        System.out.println("DIE!!");
        RoomStatus.CONFRONTING_QUESTION_MONSTER.activate();
        this.parent.getParent().getPlayer().damage(1);
        this.monsterActive = true;
        ((TaskRoomWithMonster) this.parent).activateMonster();
    }

    private void handleSecondWrongAnswer() {
        System.out.println("Imagine failing twice. DIE!!");
        this.parent.getParent().getPlayer().kill();
    }
}