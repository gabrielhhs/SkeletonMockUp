package stratpattern;

import rooms.TaskRoom;

public class OpenQuestion extends QuestionTask {
    protected final String question;
    protected final String answer;

    public OpenQuestion(String question, String answer, TaskRoom parent) {
        super(parent);
        this.question = question;
        this.answer = answer;
    }

    public void handleCorrectAnswer() {
        System.out.println("Well done you may live");
        this.getParent().getParent().getPlayer().addScore(10);
        this.giveReward();
        this.setCleared();
    }
    public void handleWrongAnswer() {
        System.out.println("You have failed you feel something being taken away from your soul");
        this.getParent().getParent().getPlayer().removeScore(10);
        this.setCleared();
    }

    @Override
    public void giveHint(String input) {
        if (input.equalsIgnoreCase("Y")) {
            getHintProvider().getHint(getParent().getParent().getPlayer().getCurrentRoom());
        }
    }

    protected void giveReward() {
        //ToDo: implement
    }

    @Override
    public void start() {
        System.out.println(this.question);
    }

    @Override
    public boolean isValidAnswer(String input) {
        return true;
    }

    @Override
    public boolean isCorrectAnswer(String input) {
        return input.equalsIgnoreCase(this.answer);
    }
}