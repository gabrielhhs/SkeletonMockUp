package core;

import commands.*;
import commands.commandslist.*;
import hints.*;
import entities.QuestionMonster;
import rooms.*;
import stratpattern.*;
import util.PathGetter;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//ToDo: Possibly split each room into a respective class (Room1Planning.java, Room2Daily.java, SideRoom.java) (pray for our fallen soldiers)
//ToDo: Create monster AND non monster task rooms
public abstract class DataSeeder {
    private static ArrayList<Room> roomList = new ArrayList<>();
    private static Room firstRoom;
    private static Set<Command> COMMANDS = new HashSet<>();
    private static List<UselessHint> uselessHints = new ArrayList<>();
    private static List<FunctionalHint> functionalHints = new ArrayList<>();

    static {
        COMMANDS.add(new StatusCommand("status"));
        COMMANDS.add(new SuicideCommand("kill"));
        COMMANDS.add(new InventoryCommand("inventory"));
        COMMANDS.add(new InventoryCommand("inv"));
        COMMANDS.add(new UseCommand());
        COMMANDS.add(new MenuCommand("menu"));

        uselessHints.add(new UselessHint("YOU CAN DO IT!!!"));
        uselessHints.add(new UselessHint("YOU CAN NOT DO IT!!!"));
        uselessHints.add(new UselessHint("We believe this question is too easy to give a Hint..."));
        uselessHints.add(new UselessHint("you can do this trust me"));
        uselessHints.add(new UselessHint("You could try that… if failing is your thing."));
        uselessHints.add(new UselessHint("Maybe try using your brain next time"));
        uselessHints.add(new UselessHint("Confidence is great. Maybe aim for competence too?"));
        uselessHints.add(new UselessHint("You’re closer than you think."));
        uselessHints.add(new UselessHint("Every expert was once where you are."));
        uselessHints.add(new UselessHint("Take a breath. You know this."));
    }

    public static Room getFirstRoom() {
        return firstRoom;
    }

    public static Room generateRooms(Game game) {
        String up = "up";
        String down = "down";
        String left = "left";
        String right = "right";

        //Monsters
        QuestionMonster monster1 = new QuestionMonster("Monstro Uno");
        QuestionMonster monster2 = new QuestionMonster("Monstro Dos");

        //Rooms
        Room outside = new Outside(game, "outside");
        firstRoom = outside;
        roomList.add(outside);
        functionalHints.add(new FunctionalHint("type enter", outside));
        TaskRoom planning = new TaskRoom(game, "planning");
        roomList.add(planning);
        functionalHints.add(new FunctionalHint("NOT THE MEME (a shame though)", planning));
        TaskRoom dailyScrum = new TaskRoom(game, "daily scrum");
        roomList.add(dailyScrum);
        functionalHints.add(new FunctionalHint("Really i dont know with this one", dailyScrum));
        TaskRoom sideRoom = new TaskRoom(game, "sideroom");
        roomList.add(sideRoom);
        functionalHints.add(new FunctionalHint("?", sideRoom));
        TaskRoomWithMonster mainRoomMonster1 = new TaskRoomWithMonster(game, "MonsterRoom1", monster1);
        roomList.add(mainRoomMonster1);
        functionalHints.add(new FunctionalHint("The Knights Who Say \"Ni!\", also called the Knights of Ni, are a band of knights encountered by King Arthur and his followers in the 1975 film Monty Python and the Holy Grail", mainRoomMonster1));
        TaskRoomWithMonster mainRoomMonster2 = new TaskRoomWithMonster(game, "MonsterRoom2", monster2);
        roomList.add(mainRoomMonster2);
        functionalHints.add(new FunctionalHint("this dev man ...", mainRoomMonster2));
        /*
            visual overview of room path [DO NOT REMOVE]
            outside = 0; planning = 1; dailyScrum = 2; sideRoom = 3; mainRoomMonster1 = 4; mainRoomMonster2 = 5;


                {4}, {5}
                {2}, {3}
                {1}
                {0}
         */

        //Outside 'Room'
        outside.putNeighboringRoom("enter", planning);

        //Planning Room
        planning.putNeighboringRoom(up, dailyScrum);
        planning.putNeighboringRoom(down, outside);

        //Daily Scrum Room
        dailyScrum.putNeighboringRoom(down, planning);
        dailyScrum.putNeighboringRoom(right, sideRoom);
        dailyScrum.putNeighboringRoom(up, mainRoomMonster1);

        //Hidden Side Room
        sideRoom.putNeighboringRoom(left, dailyScrum);

        //mainRoomMonster1
        mainRoomMonster1.putNeighboringRoom(down, dailyScrum);
        mainRoomMonster1.putNeighboringRoom(right, mainRoomMonster2);

        //mainRoomMonster2
        mainRoomMonster2.putNeighboringRoom(left, mainRoomMonster1);


        //Room Tasks
        MultipleChoiceQuestion planningTask = new MultipleChoiceQuestion(
                "What is 9 + 10?",
                new String[] {"21", "19", "I refuse to answer math questions"},
                2, planning) {
        };
        OpenQuestion dailyScrumTask = new OpenQuestion("How much wood would a woodchuck chuck if a woodchuck could chuck wood?", "42", dailyScrum);
        OpenQuestion sideRoomTask = new OpenQuestion("Hello there I'm a side room", "?", sideRoom);

        MultipleChoiceQuestionWithMonster monsterRoomQuestion1 = new MultipleChoiceQuestionWithMonster(
                "What is the airspeed velocity of an unladen swallow? (if you do not get this reference please remove yourself from my vicinity)",
                new String[] {"Over 9000", "African or European?", "I don’t know that—AAAAHHHH (gets yeeted)"},
                2,
                mainRoomMonster1
        );

        MultipleChoiceQuestionWithMonster monsterRoomQuestion2 = new MultipleChoiceQuestionWithMonster(
                "What is love?",
                new String[] {"Baby don’t hurt me", "A social construct", "Just a burning memory"},
                1,
                mainRoomMonster2
        );

        //Monster Tasks
        OpenQuestionWithMonster monsterQuestion1 = new OpenQuestionWithMonster(
                "What do the Knights Who Say 'Ni!' actually want?",
                "A shrubbery",
                mainRoomMonster1
        );

        OpenQuestionWithMonster monsterQuestion2 = new OpenQuestionWithMonster(
                "I ran out of question ideas",
                "...",
                mainRoomMonster2
        );

        //Assigning Tasks Rooms
        planning.getTaskHandler().setTask(planningTask);
        dailyScrum.getTaskHandler().setTask(dailyScrumTask);
        sideRoom.getTaskHandler().setTask(sideRoomTask);
        mainRoomMonster1.getTaskHandler().setTask(monsterRoomQuestion1);
        mainRoomMonster2.getTaskHandler().setTask(monsterRoomQuestion2);

        //Assigning Tasks Monsters
        monster1.setTask(monsterQuestion1);
        monster2.setTask(monsterQuestion2);

        return outside;
    }

    public static void generateUselessHints() {
        List<UselessHint> uselessHints = new ArrayList<>();

        uselessHints.add(new UselessHint("YOU CAN DO IT!!!"));
        uselessHints.add(new UselessHint("YOU CAN NOT DO IT!!!"));
        uselessHints.add(new UselessHint("We believe this question is too easy to give a Hint..."));
        uselessHints.add(new UselessHint("you can do this trust me"));
        uselessHints.add(new UselessHint("You could try that… if failing is your thing."));
        uselessHints.add(new UselessHint("Maybe try using your brain next time"));
        uselessHints.add(new UselessHint("Confidence is great. Maybe aim for competence too?"));
        uselessHints.add(new UselessHint("You’re closer than you think."));
        uselessHints.add(new UselessHint("Every expert was once where you are."));
        uselessHints.add(new UselessHint("Take a breath. You know this."));
    }

    public static Set<Command> getCommands() {
        return COMMANDS;
    }

    public static void setRoomClears() throws FileNotFoundException {
        for (Room room : roomList) {
            String filePath = PathGetter.resourcePath() + "/" + room.getName() + ".txt"; // update this path

            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line = reader.readLine();
                if (line != null) {
                    boolean value = Boolean.parseBoolean(line.trim());
                    room.setCleared(value);
                } else {
                    System.out.println("File is empty.");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void setPosition(Player player) {
        String filePath = PathGetter.resourcePath() + "/positionalInfo.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String currentRoom = reader.readLine();
            String activeRoomStatus = reader.readLine();

            for (Room room : roomList) {
                if (room.isCurrentRoom(currentRoom)) player.setCurrentRoom(room);
            }
            RoomStatus.valueOf(activeRoomStatus).activate();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<Room> getRoomList() {
        return roomList;
    }

    public static List<UselessHint> getUselessHints() {
        return uselessHints;
    }

    public static List<FunctionalHint> getFunctionalHints() {
        return functionalHints;
    }

    public static Player getPlayer(Room room) {
        Player player = new Player(room);
        player.giveItem(new JokerItem());
        return player;
    }
}