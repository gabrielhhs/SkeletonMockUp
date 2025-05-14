package Rooms;

import Question.*;

public class Room1Planning extends Room {
    private String answer;
    private boolean correct = false;


    public Room1Planning() {
        super();
    }

    void introductionText() {
        System.out.println("You have entered the Planning Room of Death.");
        //todo: add option for skipping entire level straight to Bonfire is isCleared
    }

    void question() {
        String[] options = {"1. Snu Snu", "2. Fatality", "3. I choose to live!"};
        Question planning1 = new MultipleChoiceQuestion("Pick your method of Death:", options);

        this.answer = planning1.ask();
    }

    void answerCheck() {
        if (answer.equals("3")) correct = true;
    }

    void result() {
        if (correct) System.out.println("Yes. This will be Observed.");
        else System.out.println("You have summoned your own Doom.");
    }



    void feedback() {
        if (correct) {
            System.out.println("You have survived...This time.");
            OpenQuestion advance1 = new OpenQuestion("Do you choose to advance to the Room of Daily Scrum Suffering? (Y/N):");
            while (!this.answer.equals("Y")) {
                this.answer = advance1.ask();
            }
            roomClear();
            game.goNext(Room2Daily);
        }
        else System.out.println("You have failed your people in life, and will now suffer in death.");
    }
}
//De Sprint Planning
//Je moet inschatten welke taken passen binnen een sprint.
// Een verkeerde inschatting triggert het monster “Scope Creep”.

//Je beweegt door kamers door een commando in te typen: “ga naar kamer X”.
//In elke kamer krijg je een of meerdere vragen of opdrachten.
//Je mag pas naar de volgende kamer als je alle vragen/opdrachten goed hebt uitgevoerd.
//Een fout antwoord roept een “monster” op: een belemmering (impediment!)  die iets blokkeert.
// Je moet dan eerst de fout corrigeren of een alternatieve opdracht uitvoeren om het monster weg te krijgen.