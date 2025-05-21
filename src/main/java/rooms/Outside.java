package rooms;

import core.Game;

import java.util.Map;
import java.util.Scanner;

public class Outside extends Room {
    public Outside(Game game) {
        super(game);
    }

    void introductionText() {
        // TODO: Implement?
    }

    void handleUncleared() {
        StringBuilder sb = new StringBuilder("""
            Its stormy in the world of Projectia, and your lost!
            Luckily you see a giant house in the distance.
            Would you like to enter?
            """);

        for (Map.Entry<String, Room> entry : this.neighboringRooms.entrySet()){
            sb.append(" ").append(entry.getKey());
        }

        System.out.println(sb);
        Scanner sc = new Scanner(System.in);

        String direction = sc.nextLine();

        for (Map.Entry<String, Room> entry : this.neighboringRooms.entrySet()){
            if (direction.equals(entry.getKey())){
                this.setCleared();
                this.game.goNext(entry.getValue());
            }
        }
    }

    @Override
    public void feedback() {
        System.out.println("The doors seem to be locked, might as well take a look inside.");
        for (Room room : this.neighboringRooms.values()) {
            this.game.goNext(room);
        }
    }
}
