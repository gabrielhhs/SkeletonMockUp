package stratpattern;

import core.Player;
import hints.HintProvider;
import rewards.RewardProvider;
import rooms.TaskRoom;

public class OpenQuestion extends QuestionTask {
    protected final String question;
    protected final String answer;

    public OpenQuestion(String question, String answer, TaskRoom parent, HintProvider hint, RewardProvider provider) {
        super(parent, hint, provider);
        this.question = question;
        this.answer = answer;
    }
    public OpenQuestion(String question, String answer, TaskRoom parent, RewardProvider provider) {
        this(question, answer, parent, null, provider);
    }
    public OpenQuestion(String question, String answer, TaskRoom parent, HintProvider hint) {
        this(question, answer, parent, hint, null);
    }
    public OpenQuestion(String question, String answer, TaskRoom parent) {
        this(question, answer, parent, null, null);
    }

    public void handleCorrectAnswer() {
        System.out.println("Well done you may live");
        Player player = this.getParent().getParent().getPlayer();
        player.addScore(10);
        this.giveReward(player);
        this.setCleared();
    }
    public void handleWrongAnswer() {
        System.out.println("You have failed you feel something being taken away from your soul");
        this.getParent().getParent().getPlayer().removeScore(10);
        this.setCleared();
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