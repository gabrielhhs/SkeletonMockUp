package Rooms;

import Question.OpenQuestion;
import Question.Question;

public class Room2Daily extends Room {
    private String answer;
    private boolean correct = false;

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
        if (correct) System.out.println("Yes. This will be Observed.");
        else System.out.println("You have summoned your own Doom.");
    }

    public void feedback() {
        if (correct) System.out.println("You have survived...This time.");
        else System.out.println("You have failed your people in life, and will now suffer in death.");
    }
}
