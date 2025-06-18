package stratpattern;

import core.GameStatus;
import core.Player;
import hints.HintProvider;
import rooms.TaskRoom;
import rewards.RewardProvider;
import util.ArrayShuffler;

public class MultipleChoiceQuestion extends QuestionTask {
    protected final String question;
    protected final String[] options;
    protected final int answerIndex;

    private static int[] createShuffledIndexes(int length) {
        int[] array = new int[length];

        for (int i = 0; i < array.length; i++) {
            array[i] = i;
        }

        ArrayShuffler.shuffle(array);

        return array;
    }
    public MultipleChoiceQuestion(String question, String[] options, HintProvider hint, TaskRoom parent, RewardProvider reward) {
        super(parent, hint, reward);
        this.question = question;

        final int[] shuffler = createShuffledIndexes(options.length);

        String[] newOptions = new String[options.length];
        for (int i = 0; i < options.length; i++) {
            newOptions[shuffler[i]] = options[i];
        }

        this.options = newOptions;
        this.answerIndex = shuffler[0];
    }
    public MultipleChoiceQuestion(String question, String[] options, TaskRoom parent, RewardProvider reward) {
        this(question, options, null, parent, reward);
    }
    public MultipleChoiceQuestion(String question, String[] options, HintProvider hint, TaskRoom parent) {
        this(question, options, hint, parent, null);
    }
    public MultipleChoiceQuestion(String question, String[] options, TaskRoom parent) {
        this(question, options, null, parent, null);
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
        return Integer.parseInt(input) == this.answerIndex + 1;
    }
}