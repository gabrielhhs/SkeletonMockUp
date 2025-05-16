package rooms;

import core.Game;
import question.*;

import java.util.HashMap;
import java.util.Map;

public class Room1Planning extends Room {
    public Room1Planning(Game game) {
        super(game);
        super.neighboringRooms = new HashMap<>();
    }

    void introductionText() {
        System.out.println("You have entered the Planning Room of Death.");
        //todo: add option for skipping entire level straight to Bonfire is isCleared
    }

    void question() {
        String[] options = {"1. Snu Snu", "2. Fatality", "3. I choose to live!"};
        Question planning1 = new MultipleChoiceQuestion("Pick your method of Death:", options);

        answer = planning1.ask();
    }

    void answerCheck() {
        if (answer.equals("3")) correct = true;
    }

    void result() {
        if (correct) System.out.println("Yes. This will be Observed.");
        else System.out.println("You have summoned your own Doom.");
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