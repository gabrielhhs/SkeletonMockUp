package Rooms;

import Core.*;
import Question.OpenQuestion;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public abstract class Room {
    protected Game game;
    protected String name;
    protected Map<String, Room> neighboringRooms;
    protected boolean isCleared = false;
    protected String answer;
    protected boolean correct = false;

    abstract void introductionText();
    abstract void question();
    abstract void answerCheck();
    abstract void result();

    public void bonfire(){
        if (correct) {
            System.out.println("You have survived...This time.");
            StringBuilder sb = new StringBuilder();
            String start = "In which door do you wish to go to. ";
            sb.append(start);
            for (Map.Entry<String, Room> entry : neighboringRooms.entrySet()){
                sb.append(" ").append(entry.getKey());
            }

            System.out.println(sb.toString());
            Scanner sc = new Scanner(System.in);

            String direction = sc.nextLine();

            for (Map.Entry<String, Room> entry : neighboringRooms.entrySet()){
                if (direction.equals(entry.getKey())){
                    game.goNext(entry.getValue());
                }
            }

        }
        else{
            System.out.println("You have failed your people in life, and will now suffer in death.");
        }
    }

    public Room(Game game) {
        this.game = game;
    }

    public Room(Game game, String name, Map<String, Room> roomMap) {
        this.neighboringRooms = roomMap;
    }

    public void addNeighboringRoom(String direction, Room room){
        neighboringRooms.put(direction, room);
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
        if (!isCleared) {
            question();
            answerCheck();
            result();
        }
        bonfire();
    }

    public Map<String, Room> getNeiboringRooms() {
        return neighboringRooms;
    }

    public void roomClear(){
        this.isCleared = true;
    }
}