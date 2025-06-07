package core;

import commands.Command;
import commands.commandslist.*;
import hints.HintProvider;
import hints.LiteralHintProvider;
import entities.QuestionMonster;
import hints.RandomHintProvider;
import items.Item;
import items.consumables.GamblingPotionItem;
import rooms.Outside;
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
import java.util.function.Supplier;

//ToDo: Possibly split each room into a respective class (Room1Planning.java, Room2Daily.java, SideRoom.java) (pray for our fallen soldiers)
//ToDo: Create monster AND non monster task rooms
public abstract class DataSeeder {
    private static ArrayList<Room> roomList = new ArrayList<>();

    private static Set<Command> COMMANDS = new HashSet<>() {{
        add(new StatusCommand("status"));
        add(new SuicideCommand("kill"));
        add(new InventoryCommand("inventory"));
        add(new InventoryCommand("inv"));
        add(new UseCommand());
        add(new MenuCommand("menu"));
    }};

    private static HintProvider USELESS_HINTS = new RandomHintProvider(new HintProvider[]{
        new LiteralHintProvider("YOU CAN DO IT!!!"),
        new LiteralHintProvider("YOU CAN NOT DO IT!!!"),
        new LiteralHintProvider("We believe this question is too easy to give a Hint..."),
        new LiteralHintProvider("you can do this trust me"),
        new LiteralHintProvider("You could try that… if failing is your thing."),
        new LiteralHintProvider("Maybe try using your brain next time"),
        new LiteralHintProvider("Confidence is great. Maybe aim for competence too?"),
        new LiteralHintProvider("You’re closer than you think."),
        new LiteralHintProvider("Every expert was once where you are."),
        new LiteralHintProvider("Take a breath. You know this."),
    });

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
        roomList.add(outside);
        TaskRoom planning = new TaskRoom(game, "planning");
        roomList.add(planning);
        TaskRoom dailyScrum = new TaskRoom(game, "daily scrum");
        roomList.add(dailyScrum);
        TaskRoom sideRoom = new TaskRoom(game, "sideroom");
        roomList.add(sideRoom);
        TaskRoomWithMonster mainRoomMonster1 = new TaskRoomWithMonster(game, "MonsterRoom1", monster1);
        roomList.add(mainRoomMonster1);
        TaskRoomWithMonster mainRoomMonster2 = new TaskRoomWithMonster(game, "MonsterRoom2", monster2);
        roomList.add(mainRoomMonster2);
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
                2,
                new RandomHintProvider(new HintProvider[]{new LiteralHintProvider("NOT THE MEME (a shame though)"), USELESS_HINTS}),
                planning
        );
        OpenQuestion dailyScrumTask = new OpenQuestion("How much wood would a woodchuck chuck if a woodchuck could chuck wood?", "42", dailyScrum, new RandomHintProvider(new HintProvider[]{new LiteralHintProvider("Really i dont know with this one"), USELESS_HINTS}));
        OpenQuestion sideRoomTask = new OpenQuestion("Hello there I'm a side room", "?", sideRoom, new RandomHintProvider(new HintProvider[]{new LiteralHintProvider("?"), USELESS_HINTS}));

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
                new RandomHintProvider(new HintProvider[]{new LiteralHintProvider("The Knights Who Say \"Ni!\", also called the Knights of Ni, are a band of knights encountered by King Arthur and his followers in the 1975 film Monty Python and the Holy Grail"), USELESS_HINTS}),
                mainRoomMonster1
        );

        OpenQuestionWithMonster monsterQuestion2 = new OpenQuestionWithMonster(
                "I ran out of question ideas",
                "...",
                new RandomHintProvider(new HintProvider[]{new LiteralHintProvider("this dev man ..."), USELESS_HINTS}),
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
        List<LiteralHintProvider> uselessHints = new ArrayList<>();

        uselessHints.add(new LiteralHintProvider("YOU CAN DO IT!!!"));
        uselessHints.add(new LiteralHintProvider("YOU CAN NOT DO IT!!!"));
        uselessHints.add(new LiteralHintProvider("We believe this question is too easy to give a Hint..."));
        uselessHints.add(new LiteralHintProvider("you can do this trust me"));
        uselessHints.add(new LiteralHintProvider("You could try that… if failing is your thing."));
        uselessHints.add(new LiteralHintProvider("Maybe try using your brain next time"));
        uselessHints.add(new LiteralHintProvider("Confidence is great. Maybe aim for competence too?"));
        uselessHints.add(new LiteralHintProvider("You’re closer than you think."));
        uselessHints.add(new LiteralHintProvider("Every expert was once where you are."));
        uselessHints.add(new LiteralHintProvider("Take a breath. You know this."));
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

            for (Room room : roomList) {
                if (room.isCurrentRoom(currentRoom)) player.setCurrentRoom(room);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setInventory(Player player) {
        String filePath = PathGetter.resourcePath() + "/inventoryInfo.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            for (Item item : player.getInventory().keySet()) {
                Supplier<GamblingPotionItem> supplier = GamblingPotionItem::new;
                Item instance = supplier.get();

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<Room> getRoomList() {
        return roomList;
    }

    public static Player getPlayer(Room room) {
        return new Player(room);
    }

    public static void setStatus(StatusManager statusManager) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(PathGetter.resourcePath() + "/positionalInfo.txt"));

        reader.readLine(); // skip currentRoom value
        GameStatus status = GameStatus.valueOf(reader.readLine());

        statusManager.set(status);
    }
}