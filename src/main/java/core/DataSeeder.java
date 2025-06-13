package core;

import commands.Command;
import commands.commandslist.*;
import entities.AssistantEntity;
import events.eventtypes.AssistantEncounterEvent;
import events.eventtypes.ReverseWeepingAngelEvent;
import hints.HintProvider;
import hints.LiteralHintProvider;
import entities.QuestionMonster;
import hints.RandomHintProvider;
import rooms.Outside;
import rooms.*;
import stratpattern.*;

import java.util.HashSet;
import java.util.Set;

//ToDo: Possibly split each room into a respective class (Room1Planning.java, Room2Daily.java, SideRoom.java) (pray for our fallen soldiers)
//ToDo: Create monster AND non monster task rooms
public abstract class DataSeeder {
    private static Set<Command> COMMANDS = new HashSet<>() {{
        add(new StatusCommand("status"));
        add(new SuicideCommand("kill"));
        add(new InventoryCommand("inventory"));
        add(new InventoryCommand("inv"));
        add(new UseCommand());
        add(new MenuCommand("menu"));
    }};

    private static HintProvider USELESS_HINTS = new RandomHintProvider(
            new LiteralHintProvider("YOU CAN DO IT!!!"),
			new LiteralHintProvider("YOU CAN NOT DO IT!!!"),
			new LiteralHintProvider("We believe this question is too easy to give a Hint..."),
			new LiteralHintProvider("you can do this trust me"),
			new LiteralHintProvider("You could try that… if failing is your thing."),
			new LiteralHintProvider("Maybe try using your brain next time"),
			new LiteralHintProvider("Confidence is great. Maybe aim for competence too?"),
			new LiteralHintProvider("You’re closer than you think."),
			new LiteralHintProvider("Every expert was once where you are."),
			new LiteralHintProvider("Take a breath. You know this.")
    );

    public static Room generateRooms(Game game) {
        String up = "up";
        String down = "down";
        String left = "left";
        String right = "right";

        //Monsters
        QuestionMonster monster1 = new QuestionMonster("Monstro Uno");
        QuestionMonster monster2 = new QuestionMonster("Monstro Dos");
        AssistantEntity assistant = new AssistantEntity();

        //Events
        ReverseWeepingAngelEvent angelEvent = new ReverseWeepingAngelEvent(2);
        AssistantEncounterEvent assistantEvent = new AssistantEncounterEvent(assistant);

        //Rooms
        Room outside = new Outside(game, "outside");
        TaskRoom planning = new TaskRoom(game, "planning");
        TaskRoom dailyScrum = new TaskRoom(game, "daily scrum");
        TaskRoom sideRoom = new TaskRoom(game, "sideroom");
        TaskRoomWithMonster mainRoomMonster1 = new TaskRoomWithMonster(game, "MonsterRoom1", monster1);
        TaskRoomWithMonster mainRoomMonster2 = new TaskRoomWithMonster(game, "MonsterRoom2", monster2);
        TaskRoomWithEvent angelRoom = new TaskRoomWithEvent(game, "Weeping Room");
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
                new RandomHintProvider(new LiteralHintProvider("NOT THE MEME (a shame though)"), USELESS_HINTS),
                planning
        );
        OpenQuestion dailyScrumTask = new OpenQuestion("How much wood would a woodchuck chuck if a woodchuck could chuck wood?", "42", dailyScrum, new RandomHintProvider(new LiteralHintProvider("Really i dont know with this one"), USELESS_HINTS));
        OpenQuestion sideRoomTask = new OpenQuestion("Hello there I'm a side room", "?", sideRoom, new RandomHintProvider(new LiteralHintProvider("?"), USELESS_HINTS));

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
                new RandomHintProvider(new LiteralHintProvider("Monty Python And The Holy Grail 1974, 18:55"), USELESS_HINTS)
        );

        //Monster Tasks
        OpenQuestionWithMonster monsterQuestion1 = new OpenQuestionWithMonster(
                "What do the Knights Who Say 'Ni!' actually want?",
                "A shrubbery",
                new RandomHintProvider(new LiteralHintProvider("The Knights Who Say \"Ni!\", also called the Knights of Ni, are a band of knights encountered by King Arthur and his followers in the 1975 film Monty Python and the Holy Grail"), USELESS_HINTS),
                mainRoomMonster1
        );

        OpenQuestionWithMonster monsterQuestion2 = new OpenQuestionWithMonster(
                "I ran out of question ideas",
                "...",
                new RandomHintProvider(new LiteralHintProvider("this dev man ..."), USELESS_HINTS),
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

    public static Set<Command> getCommands() {
        return COMMANDS;
    }
}