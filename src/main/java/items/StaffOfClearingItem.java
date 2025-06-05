package items;

import core.DataSeeder;
import core.Player;
import rooms.Room;
import util.PathGetter;

import java.io.File;
import java.util.Objects;

public class StaffOfClearingItem implements Item {
    @Override
    public void use(Player player, Room room) {
        Room startingRoom = DataSeeder.getFirstRoom();
        if (startingRoom != null) resetGame(player, startingRoom);
        else System.out.println("Nothing happened");
    }
    
    private void resetGame(Player player, Room startingRoom) {
        purgeDirectory(new File(PathGetter.resourcePath()));
        player.setCurrentRoom(startingRoom);

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