package rooms;

import core.Game;

public class Room1Planning extends Room {

    public Room1Planning(Game game) {
        super(game);
    }

    void introductionText() {
        System.out.println("You have entered the Planning Room of Death.");
        //todo: add option for skipping entire level straight to Bonfire is isCleared
    }

    @Override
    void handleUncleared() {
        if (!this.taskHandler.startTask(this.task)) {
            this.game.getPlayer().damage(1);
            //ToDo: summon monster for new Task
        }
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