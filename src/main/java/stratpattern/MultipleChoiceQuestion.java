package stratpattern;

import core.RoomStatus;
import rooms.TaskRoom;

public class MultipleChoiceQuestion extends QuestionTask {
    protected final String question;
    protected final String[] options;
    protected final int answer;

    public MultipleChoiceQuestion(String question, String[] options, int answer, TaskRoom parent) {
        super(parent);
        this.question = question;
        this.options = options;
        this.answer = answer;
    }

    public void handleCorrectAnswer() {
        System.out.println("Well done you may live");
        this.getParent().getParent().getPlayer().addScore(10);
        this.giveReward();
        this.setCleared();
    }

    protected void giveReward() {
        //ToDo: implement
    }

    public void handleWrongAnswer() {
        System.out.println("You have failed you feel something being taken away from your soul");
        this.getParent().getParent().getPlayer().removeScore(10);
        System.out.println("\nDo you want a hint? Y/N");
        RoomStatus.IN_HINT.activate();
    }

    @Override
    public final void start() {
        System.out.println("Mysterious void: ANSWER OR DIE");
        System.out.println(this.question);
        for (int index = 0; index < this.options.length; index++) {
            System.out.println((index + 1) + ". " + this.options[index]);
        }
    }

    @Override
    public boolean isValidAnswer(String input) {
        return input.matches("\\d+");
    }

    @Override
    public boolean isCorrectAnswer(String input) {
        return Integer.parseInt(input) == this.answer;
    }
}