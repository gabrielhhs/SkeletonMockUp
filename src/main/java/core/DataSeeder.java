package core;

import rooms.*;


import java.util.ArrayList;

abstract public class DataSeeder {
    private static ArrayList<Room> rooms = new ArrayList<>();



    public static Room seed(Game game) {
        String up = "up";
        String down = "down";
        String left = "left";
        String right = "right";

        Room outside = new Outside(game);
        rooms.add(outside);
        Room planning = new Room1Planning(game);
        rooms.add(planning);
        Room dailyScrum = new Room2Daily(game);
        rooms.add(dailyScrum);
        Room sideRoom = new SideRoom(game);
        rooms.add(sideRoom);

        Room room = Database.setProgress("1", rooms);

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

        return room;


    }
}