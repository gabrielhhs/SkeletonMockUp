package core;

import commands.Command;
import commands.commandslist.*;
import dialogue.DialogueManager;
import dialogue.DialogueNode;
import entities.AssistantEntity;
import entities.DialogueEntity;
import events.eventtypes.AssistantEncounterEvent;
import events.eventtypes.ReverseWeepingAngelEvent;
import hints.HintProvider;
import hints.LiteralHintProvider;
import entities.QuestionMonster;
import hints.RandomHintProvider;
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
        AssistantEntity assistant = new AssistantEntity(); generateDialogue(assistant);

        //Events
        ReverseWeepingAngelEvent angelEvent = new ReverseWeepingAngelEvent(2);
        AssistantEncounterEvent assistantEvent = new AssistantEncounterEvent(assistant);

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
        TaskRoomWithEvent angelRoom = new TaskRoomWithEvent(game, "Weeping Room");
        roomList.add(angelRoom);
        SpecialEventRoom assistantRoom = new SpecialEventRoom(game, "The Doll Room", assistantEvent);

        /*
            visual overview of room path [DO NOT REMOVE]
            outside = 0; planning = 1; dailyScrum = 2; sideRoom = 3; mainRoomMonster1 = 4; mainRoomMonster2 = 5;
            angelRoom = 6; assistantRoom = 7;


                {4}, {5}, {6}, {7}
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
        mainRoomMonster2.putNeighboringRoom(right, angelRoom);

        //Angel Room
        angelRoom.putNeighboringRoom(left, mainRoomMonster2);
        angelRoom.putNeighboringRoom(right, assistantRoom);

        //Assistant Room
        assistantRoom.putNeighboringRoom(left, angelRoom);


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

        OpenQuestion angelRoomQuestion = new OpenQuestion(
                "What are witches made of?",
                "wood",
                angelRoom,
                new RandomHintProvider(new HintProvider[]{new LiteralHintProvider("Monty Python And The Holy Grail 1974, 18:55"), USELESS_HINTS})
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
        angelRoom.getTaskHandler().setTask(angelRoomQuestion);

        //Assigning Tasks Monsters
        monster1.setTask(monsterQuestion1);
        monster2.setTask(monsterQuestion2);

        //Assigning Event
        angelRoom.getEventHandler().setEvent(angelEvent);

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

    private static void generateDialogue(DialogueEntity entity) {
        AssistantEntity assistant = (AssistantEntity) entity;
        DialogueManager manager = assistant.getDialogueManager();
        DialogueNode introNode = new DialogueNode("[Wooden doll] Its rare to see another soul enter this place\n[Assistant] Im known as the assistant");
        introNode.addOption("1", "Introduce yourself", "explanation");
        introNode.addOption("2", "stay silent", "explanation"); //basic etiquette skill cmon man (I need a better sleeping schedule)

        DialogueNode explanationNode = new DialogueNode("[Assistant] I'm here to help you during your travels");
        explanationNode.addOption("1", "Fucking finally took you long enough", "mean0");
        explanationNode.addOption("2", "It's nice to finally speak to someone that doesn't want to kill me", "nice0");

        DialogueNode meanDoll0 = new DialogueNode("[Assistant] Well that's kind of mean but I understand your perspective");
        meanDoll0.addOption("1", "Well? What kind of assistance will you be providing me with?", "mean1");

        DialogueNode niceDoll0 = new DialogueNode("[Assistant] Sadly it wont end here but you're in luck for I am here");
        niceDoll0.addOption("1", "You bring me joy what kind of amazing assistance can I be expecting?", "nice1");

        DialogueNode meanDoll1 = new DialogueNode("""
                [Assistant] You should worry more about your karma rating
                But anyway I will provide my assistance by giving you hints when you are in need
                Simply use '/assist' and I will come to your aid
                """);
        meanDoll1.addOption("1", "I pray you make yourself useful", null);
        meanDoll1.addOption("2", "Sorry about the earlier comments that was mean", "redemption");

        DialogueNode niceDoll1 = new DialogueNode("""
                [Assistant] You flatter me, I will guide you whenever you call upon me by using '/assist'
                Sadly due to rules set by beings beyond our comprehension I can only help you twice, so make sure you decide carefully when you call me
                """); //still lf sleep schedule
        niceDoll1.addOption("1", "Thank you for your help I will make great use of your assistance", null);

        DialogueNode meanDoll2 = new DialogueNode("[Assistant] No problems apology accepted now go forth");
        DialogueNode meanDoll3 = new DialogueNode("[Assistant] I reiterate watch ur karma rating man");

        DialogueNode niceDoll2 = new DialogueNode("[Assistant] I'm please to help");

        manager.addNode(introNode, "intro");
        manager.addNode(explanationNode, "explanation");
        manager.addNode(meanDoll0, "mean0");
        manager.addNode(niceDoll0, "nice0");
        manager.addNode(niceDoll1, "nice1");
        manager.addNode(niceDoll2, null);
        manager.addNode(meanDoll1, "mean1");
        manager.addNode(meanDoll2, "redemption");
        manager.addNode(meanDoll3, null);
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