package Rooms;

import Core.Game;
import Question.OpenQuestion;
import Question.Question;

import java.util.HashMap;

public class Room2Daily extends Room {
    private String answer;
    private boolean correct = false;

    public Room2Daily(Game game) {
        super(game);
        super.neighboringRooms = new HashMap<>();
    }

    public void introductionText() {
        System.out.println("You have entered the Room of Daily Scrum Suffering.");
    }

    public void question() {
        Question daily1 = new OpenQuestion("Who are you planning to defeat in this dungeon?");

        this.answer = daily1.ask();
    }

    public void answerCheck() {
        if (answer.equals("ScrumMaster")) correct = true;
    }

    public void result() {
        if (correct) {
            System.out.println("Yes. This will be Observed.");
        } else System.out.println("You have summoned your own Doom.");
    }
}