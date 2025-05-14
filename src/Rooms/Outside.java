package Rooms;

import Core.Game;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Outside extends Room{
    public Outside(Game game) {
        super(game);
        super.neighboringRooms = new HashMap<>();
    }

    void introductionText() {
    }

    void question() {

    }

    void answerCheck() {
        correct = true;
    }

    void result() {
    }

    @Override
    public void bonfire(){
        if (correct) {
            StringBuilder sb = new StringBuilder();
            String start = "\nIts stormy in the world of Projectia, and your lost! \nLuckily you see a giant house in the distance." +
                    "\nWould you like to enter?\n";
            sb.append(start);
            for (Map.Entry<String, Room> entry : neighboringRooms.entrySet()){
                sb.append(" ").append(entry.getKey());
            }

            System.out.println(sb);
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
}
