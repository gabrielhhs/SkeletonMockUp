package Core;

import Rooms.Room;

public class Player {
    private Room room;
    private int health;
    private int score;

    public Player(Room room, int status) {
        this.room = room;
    }

    public void setPosition(Room room) {
        this.room = room;
    }

    public void getInfo(){
//        System.out.println("Your current room is: " + room.getName());
        System.out.println("Your HP is: " + health);
        System.out.println("Your score is: " + score);
    }
    public void death(){
        this.isDead = true;
    }
}
//Alles gebeurt in tekst (CLI)
//Je typt commando’s zoals: 'ga naar kamer 2'
//Je kunt op elk moment status intypen om te zien waar je bent, hoeveel kamers je al hebt gehaald,
// en of je nog monsters (impediments) hebt om op te lossen.