package stratpattern;

import rooms.TaskRoom;

public class OpenQuestion implements Task {
    protected final String question;
    protected final String answer;
    protected TaskRoom parent;

    public OpenQuestion(String question, String answer, TaskRoom parent) {
        this.question = question;
        this.answer = answer;
        this.parent = parent;
    }

    @Override
    public void consume(String input) {
        if (input.equalsIgnoreCase(this.answer)) this.handleCorrectAnswer();
        else { this.handleWrongAnswer(); this.setCleared(); }
    }

    protected void handleCorrectAnswer() {
        System.out.println("Well done you may live");
        this.parent.getParent().getPlayer().addScore(10);
        this.giveReward();
        this.setCleared();
    }

    protected void handleWrongAnswer() {
        System.out.println("You have failed you feel something being taken away from your soul");
        this.parent.getParent().getPlayer().removeScore(10);
    }

    protected void giveReward() {
        //ToDo: implement
    }

    protected void setCleared() {
        this.parent.setCleared();
        this.parent.chooseRoom();
    }

    @Override
    public void start() {
        System.out.println(this.question);
    }

    protected TaskRoom getParent() {
        return this.parent;
    }
}