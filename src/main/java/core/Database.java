package core;

import org.w3c.dom.ls.LSOutput;
import rooms.Room;

import java.sql.*;
import java.util.ArrayList;


public abstract class Database {
    public static Room setProgress(String playerid, ArrayList<Room> rooms){
        Room outsideroom = null;
        String url = "jdbc:sqlite:/";
        String query = "Select * from savedata";
        try(Connection connection = DriverManager.getConnection(url)) {
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(query);


            int count = 0;
            while(resultSet.next()){
                count++;
                String currentRoom = resultSet.getString("currentroom");
                boolean outside = resultSet.getBoolean("outside");
                boolean planning = resultSet.getBoolean("planning");
                boolean dailyScrum = resultSet.getBoolean("dailyscrum");
                boolean sideRoom = resultSet.getBoolean("sideroom");
                for (Room room : rooms) {
                    switch (room.getName()){
                        case "outside" -> {
                            room.setCleared(outside);
                            outsideroom = room;
                        }
                        case "planning" -> room.setCleared(planning);
                        case "dailyscrum" -> room.setCleared(dailyScrum);
                        case "sideroom" -> room.setCleared(sideRoom);
                    }
                    if (currentRoom.equals(room.getName())) return room;



                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
     if (outsideroom.equals(null)) throw new RuntimeException();
     else return outsideroom;
    }
}
