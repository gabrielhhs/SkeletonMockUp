package rooms;

import core.Game;

public abstract class QuestionRoom extends Room {
	public QuestionRoom(Game game) {
		super(game);
	}

	abstract String askQuestion();
	abstract boolean checkAnswer(String answer);
	abstract void handleQuestionResult(boolean correct);

	public final void handleUncleared() {
		this.handleQuestionResult(this.checkAnswer(this.askQuestion()));
	}
}