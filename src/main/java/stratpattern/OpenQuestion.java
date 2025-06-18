package stratpattern;

import core.Player;
import hints.HintProvider;
import rewards.RewardProvider;
import rooms.TaskRoom;

public class OpenQuestion extends QuestionTask {
    protected final String question;
    protected final String[] answers;

    public OpenQuestion(TaskRoom parent, HintProvider hint, RewardProvider reward, String question, String... answers) {
        super(parent, hint, reward);
        this.question = question;
        this.answers = answers;
    }
    public OpenQuestion(TaskRoom parent, RewardProvider reward, String question, String... answers) {
        this(parent, null, reward, question, answers);
    }
    public OpenQuestion(TaskRoom parent, HintProvider hint, String question, String... answers) {
        this(parent, hint, null, question, answers);
    }
    public OpenQuestion(TaskRoom parent, String question, String... answers) {
        this(parent, null, null, question, answers);
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
        for (String answer : this.answers) if (answer.equalsIgnoreCase(input)) return true;
        return false;
    }
}