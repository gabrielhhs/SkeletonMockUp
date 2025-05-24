package core;

import rooms.Outside;
import rooms.Room;
import rooms.TaskRoom;
import stratpattern.MultipleChoiceQuestion;
import stratpattern.OpenQuestion;

//ToDo: Possibly split each room into a respective class (Room1Planning.java, Room2Daily.java, SideRoom.java) (pray for our fallen soldiers)
public abstract class DataSeeder {
    public static Room seed(Game game) {
        String up = "up";
        String down = "down";
        String left = "left";
        String right = "right";

        Room outside = new Outside(game, "outside");
        TaskRoom planning = new TaskRoom(game, "planning");
        TaskRoom dailyScrum = new TaskRoom(game, "daily scrum");
        TaskRoom sideRoom = new TaskRoom(game, "sideroom");

        //Outside 'Room'
        outside.putNeighboringRoom("enter", planning);

        //Planning Room
        planning.putNeighboringRoom(up, dailyScrum);
        planning.putNeighboringRoom(down, outside);

        //Daily Scrum Room
        dailyScrum.putNeighboringRoom(down, planning);
        dailyScrum.putNeighboringRoom(right, sideRoom);

        //Hidden Side Room
        sideRoom.putNeighboringRoom(left, dailyScrum);


        //Tasks
        MultipleChoiceQuestion planningTask = new MultipleChoiceQuestion(
                "What is 9 + 10?",
                new String[]{"21", "19", "I refuse to answer math questions"},
                2, planning);
        OpenQuestion dailyScrumTask = new OpenQuestion("How much wood would a woodchuck chuck if a woodchuck could chuck wood?", "42", dailyScrum);
        OpenQuestion sideRoomTask = new OpenQuestion("Hello there I'm a side room", "?", sideRoom);

        //Assigning Tasks
        planning.getTaskHandler().setTask(planningTask);
        dailyScrum.getTaskHandler().setTask(dailyScrumTask);
        sideRoom.getTaskHandler().setTask(sideRoomTask);

        return outside;
    }

    public static Player getPlayer(Room room) {
        return new Player(room);
    }
}