package stratpattern;

import core.RoomStatus;
import rooms.Room;

public class MultipleChoiceQuestion implements Task {
    private String question;
    private String[] options;
    private String answerNum;
    private String answerString;
    private Room room;

    public MultipleChoiceQuestion(String question, String[] options, int answerNum, String answerString, Room room) {
        this.question = question;
        this.options = options;
        this.answerNum = String.valueOf(answerNum);
        this.answerString = answerString;
        this.room = room;
    }

    private void askQuestion() {
        System.out.println("Mysterious void: ANSWER OR DIE");
        System.out.println(this.question);
        for (var entry : this.options) {
            System.out.println(entry);
        }
    }

    private void handleAnswer(String input) {
        if (input.equalsIgnoreCase(this.answerString) || input.equals(this.answerNum)) {
            System.out.println("Well done you may live");
            this.room.setCleared();
            this.room.chooseRoom();
        } else {
            System.out.println("DIE!!");
            RoomStatus.CONFRONTING_QUESTION_MONSTER.setTrue();
            this.room.getParent().getPlayer().damage(1);
            //ToDo: implement summoning the monster
        }
    }

    @Override
    public void consume(String input) {
        this.handleAnswer(input);
    }

    @Override
    public final void start() {
        askQuestion();
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}