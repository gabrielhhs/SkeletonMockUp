package stratpattern;

import core.GameStatus;
import hints.HintProvider;
import rooms.TaskRoom;
import rewards.RewardProvider;

public class MultipleChoiceQuestion extends QuestionTask {
    protected final String question;
    protected final String[] options;
    protected final int answer;
    protected final RewardProvider rewardProvider;

    public MultipleChoiceQuestion(String question, String[] options, int answer, HintProvider hint, TaskRoom parent, RewardProvider rewardProvider) {
        super(parent, hint);
        this.question = question;
        this.options = options;
        this.answer = answer;
        this.rewardProvider = rewardProvider;
    }
    public MultipleChoiceQuestion(String question, String[] options, int answer, TaskRoom parent, RewardProvider rewardProvider) {
        this(question, options, answer, null, parent, rewardProvider);
    }

    public void handleCorrectAnswer() {
        System.out.println("Well done you may live");
        this.getParent().getParent().getPlayer().addScore(10);
        this.giveReward();
        this.setCleared();
    }

    protected void giveReward() {
        this.getParent().getParent().getPlayer().giveItem(rewardProvider.reward());
    }

    public void handleWrongAnswer() {
        System.out.println("You have failed you feel something being taken away from your soul");
        this.getParent().getParent().getPlayer().removeScore(10);
        System.out.println("\nDo you want a hint? Y/N");
        this.getParent().getParent().getStatusManager().set(GameStatus.IN_HINT);
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