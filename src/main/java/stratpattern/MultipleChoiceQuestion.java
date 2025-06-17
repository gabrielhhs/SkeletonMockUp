package stratpattern;

import core.GameStatus;
import core.Player;
import hints.HintProvider;
import rooms.TaskRoom;
import rewards.RewardProvider;

public class MultipleChoiceQuestion extends QuestionTask {
    protected final String question;
    protected final String[] options;
    protected final int answer;

    public MultipleChoiceQuestion(String question, String[] options, int answer, HintProvider hint, TaskRoom parent, RewardProvider provider) {
        super(parent, hint, provider);
        this.question = question;
        this.options = options;
        this.answer = answer;
    }
    public MultipleChoiceQuestion(String question, String[] options, int answer, TaskRoom parent, RewardProvider provider) {
        this(question, options, answer, null, parent, provider);
    }
    public MultipleChoiceQuestion(String question, String[] options, int answer, HintProvider hint, TaskRoom parent) {
        this(question, options, answer, hint, parent, null);
    }
    public MultipleChoiceQuestion(String question, String[] options, int answer, TaskRoom parent) {
        this(question, options, answer, null, parent, null);
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