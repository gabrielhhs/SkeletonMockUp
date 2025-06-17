package stratpattern;

import core.GameStatus;
import core.Player;
import hints.HintProvider;
import rewards.RewardProvider;
import rooms.TaskRoom;

public abstract class QuestionTask extends Task {
	private final HintProvider hintProvider;
	private final RewardProvider rewardProvider;

	public QuestionTask(TaskRoom parent, HintProvider hint, RewardProvider rewardProvider) {
		super(parent);
		this.hintProvider = hint;
		this.rewardProvider = rewardProvider;
	}

	public QuestionTask(TaskRoom parent, RewardProvider rewardProvider) {
		this(parent, null, rewardProvider);
	}

	public QuestionTask(TaskRoom parent, HintProvider hintProvider) {
		this(parent, hintProvider,null);
	}

	public QuestionTask(TaskRoom parent) {
		this(parent, null, null);
	}

	public abstract void start();
	public abstract boolean isValidAnswer(String input);
	public abstract boolean isCorrectAnswer(String input);
	public abstract void handleCorrectAnswer();
	public abstract void handleWrongAnswer();

	public void giveReward(Player player) {
			if (this.rewardProvider == null) { System.out.println("You leave empty handed."); }
			else { player.giveItem(rewardProvider.getReward()); }
	}

	public void askHint(String input) {
		if (this.hintProvider == null) {
			System.out.println("No hints");
			return;
		}

		this.getParent().getParent().getStatusManager().revert();
		if (input.equalsIgnoreCase("Y")) {
			System.out.println(this.hintProvider.getHint());
			this.getParent().enter();
		}
	}

	public final void consume(String input) {
		if (this.getParent().getParent().getStatusManager().is(GameStatus.IN_HINT)) this.askHint(input);
		else if (!this.isValidAnswer(input)) System.out.println("Invalid answer, please try again");
		else this.handleResult(this.isCorrectAnswer(input));
	}

	public final void handleResult(boolean correct) {
		if (correct) this.handleCorrectAnswer();
		else this.handleWrongAnswer();
	}
}