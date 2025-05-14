package Rooms;

import Core.*;

import java.util.HashMap;
import java.util.Map;

public abstract class Room {
    protected Game game;
    protected String name;
    private Map<String, Room> roomMap;
    private boolean isCleared = false;

    public Room() {
    }

    public Room(Game game, String name, Map<String, Room> roomMap) {
        this.roomMap = roomMap;
    }

    public void addRoomMap(String direction, Room room){

    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setGame(Game game){
        this.game = game;
    }
    public final void enter(){
        introductionText();
        question();
        answerCheck();
        result();
        feedback();
    }
    abstract void introductionText();
    abstract void question();
    abstract void answerCheck();
    abstract void result();
    abstract void feedback();

    public Map<String, Room> getRoomMap() {
        return roomMap;
    }

    public void roomClear(){
        this.isCleared = true;
    }

    public void setRoomMap(Map<String, Room> roomMap) {
        this.roomMap = roomMap;
    }
}


//- introductietekst
//- opdracht <-- Hier context class
//- controle van het antwoord <-- Hier Observable
//- resultaat
//- feedback
//In de kamer 'Sprint Planning' krijgt de speler een scenario waarin hij moet inschatten welke taken in de sprint passen.
// In de kamer 'Review' krijgt hij feedback van een klant en moet hij aangeven wat de impact is.
// De structuur blijft hetzelfde, maar de inhoud verschilt per kamer.
// Nieuwe kamers kunnen eenvoudig worden toegevoegd zonder aanpassing van bestaande logica.