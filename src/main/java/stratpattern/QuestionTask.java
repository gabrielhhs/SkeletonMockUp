package stratpattern;

import rooms.TaskRoom;

public abstract class QuestionTask extends Task {
	public QuestionTask(TaskRoom parent) {
		super(parent);
	}

	public abstract void start();
	public abstract boolean isValidAnswer(String input);
	public abstract boolean isCorrectAnswer(String input);
	public abstract void handleCorrectAnswer();
	public abstract void handleWrongAnswer();

	public final void consume(String input) {
		if (!this.isValidAnswer(input)) System.out.println("Invalid answer, please try again");
		this.handleResult(this.isCorrectAnswer(input));
	}

	public final void handleResult(boolean correct) {
		if (correct) this.handleCorrectAnswer();
		else this.handleWrongAnswer();
	}
}