package items.consumables;

import core.DataSeeder;
import core.Game;
import core.Player;
import items.Item;
import rooms.Room;
import util.PathGetter;

import java.io.File;
import java.util.Objects;

public class StaffOfClearingItem implements Item {
    @Override
    public void use(Player player, Room room) {
        this.resetGame(player);
    }
    
    private void resetGame(Game game) {
        Player player = game.getPlayer();

        purgeDirectory(new File(PathGetter.resourcePath()));
        player.clearInventory();
        player.setCurrentRoom(game.getInitialRoom());

        for (Room room : DataSeeder.getRoomList()) room.setCleared(false);
    }

    private void purgeDirectory(File dir) {
        for (File file : Objects.requireNonNull(dir.listFiles())) {
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