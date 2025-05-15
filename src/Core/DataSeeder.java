package Core;

import Rooms.*;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

abstract public class DataSeeder {
    public static Room seed(Game game){

        String up = "up";
        String down = "down";
        String left = "left";
        String right = "right";

        Room outside = new Outside(game);
        Room planning = new Room1Planning(game);
        Room dailyScrum = new Room2Daily(game);
        Room sideRoom = new SideRoom(game);

        //Outside 'Room'
        outside.addNeighboringRoom("enter", planning);

        //Planning Room
        planning.addNeighboringRoom(up, dailyScrum);
        planning.addNeighboringRoom(down, outside);

        //Daily Scrum Room
        dailyScrum.addNeighboringRoom(down, planning);
        dailyScrum.addNeighboringRoom(right, sideRoom);

        //Hidden Side Room
        sideRoom.addNeighboringRoom(left, dailyScrum);
        return outside;
    }
}