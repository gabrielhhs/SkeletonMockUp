package stratpattern;

import core.RoomStatus;
import core.hints.RandomHintProvider;
import rooms.TaskRoom;

public abstract class QuestionTask extends Task {
	protected RandomHintProvider hintProvider = new RandomHintProvider();

	public QuestionTask(TaskRoom parent) {
		super(parent);
	}

	public abstract void start();
	public abstract boolean isValidAnswer(String input);
	public abstract boolean isCorrectAnswer(String input);
	public abstract void handleCorrectAnswer();
	public abstract void handleWrongAnswer();
	public abstract void giveHint(String input);

	public final void consume(String input) {
		if (RoomStatus.IN_HINT.isActive()) giveHint(input);
		else if (!this.isValidAnswer(input)) System.out.println("Invalid answer, please try again");
		this.handleResult(this.isCorrectAnswer(input));

	}

	public final void handleResult(boolean correct) {
		if (correct) this.handleCorrectAnswer();
		else this.handleWrongAnswer();
	}

	public RandomHintProvider getHintProvider() {
		return hintProvider;
	}
}