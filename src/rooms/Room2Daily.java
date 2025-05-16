package rooms;

import core.Game;
import question.OpenQuestion;
import question.Question;

public class Room2Daily extends QuestionRoom {

    public Room2Daily(Game game) {
        super(game);
    }

    public void introductionText() {
        System.out.println("You have entered the Room of Daily Scrum Suffering.");
    }

    public String askQuestion() {
        Question daily1 = new OpenQuestion("Who are you planning to defeat in this dungeon?");

        return daily1.ask();
    }

    public boolean checkAnswer(String answer) {
        return answer.equals("ScrumMaster");
    }

    public void handleQuestionResult(boolean correct) {
        if (correct) {
            System.out.println("Yes. This will be Observed.");
        } else System.out.println("You have summoned your own Doom.");
    }
}