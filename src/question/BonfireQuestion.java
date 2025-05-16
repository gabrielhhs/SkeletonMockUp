package question;

import java.util.Scanner;

public class BonfireQuestion implements Question {
    //todo: constructor with currentRoom Map, then list options and add nav

    public String ask() {
        System.out.println("You find yourself at a restful Bonfire, what do you do next?");
        return new Scanner(System.in).nextLine(); // TODO: idk if this is correct
    }
}
