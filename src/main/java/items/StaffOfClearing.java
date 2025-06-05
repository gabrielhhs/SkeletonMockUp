package items;

import core.DataSeeder;
import core.Player;
import rooms.Outside;
import rooms.Room;

import java.io.File;
import java.util.Objects;

public class StaffOfClearing implements Item {
    @Override
    public void use(Player player, Room room) {
        Room startingRoom = getStartingRoom(player);
        if (startingRoom != null) resetGame(player, room, startingRoom);
        else System.out.println("Nothing happened");
    }
    
    private Room getStartingRoom(Player player) {
        for (Room room : DataSeeder.getRoomList()) if (room instanceof Outside) return room;
        return null;
    }
    
    private void resetGame(Player player, Room room, Room startingRoom) {
        purgeDIrectory(PathGetter.getResource());
        player.setCurrentPosition(startingRoom);

        for (Room room : DataSeeder.getRoomList()) room.setCleared(false);
    }

    private void purgeDirectory(File dir) {
        for (File file: Objects.requireNonNull(dir.listFiles())) {
            if (file.isDirectory())
                purgeDirectory(file);
            file.delete();
        }
    }

    @Override
    public String getName() {
        return "Staff of Clearing™";
    }
}