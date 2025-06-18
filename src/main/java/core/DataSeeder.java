package core;

import entities.AssistantEntity;
import events.eventtypes.AssistantEncounterEvent;
import events.eventtypes.ReverseWeepingAngelEvent;
import hints.HintProvider;
import hints.LiteralHintProvider;
import entities.QuestionMonster;
import hints.RandomHintProvider;
import rewards.*;
import rooms.Outside;
import rooms.*;
import stratpattern.*;

public abstract class DataSeeder {
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

    static final String UP_DIRECTION = "up";
    static final String DOWN_DIRECTION = "down";
    static final String LEFT_DIRECTION = "left";
    static final String RIGHT_DIRECTION = "right";

    public static Room generateRooms(Game game) {
        /*
            visual overview of room path [DO NOT REMOVE]
            outside = 0; planning = 1; dailyScrum = 2; assistant? = 3; scrumBoard = 4; sprintReview = 5;
            genericRoom1 = 6; retrospective = 7; finalRoom = 8; :thumbsup:

                 end
                  |
                 {8}
                  |
         - {6} - {7} - {5}
            |           |
           {4} - {2} - {3} -
            U     |
                 {1}
                  |
                 {0}     {?}
         */

        //TODO: make boss room? (TaskRoom but with a list of tasks instead of just one)?
        /*
            De Sprint Planning x2 questions bc room contains monster 2/2
            De Daily Scrum x2 questions bc room contains monster 1/2
            Het Scrum Board 1/1
            De Sprint Review 1/1
            De Sprint Retrospective 1/1
            Finale TIA Kamer – Waarom Scrum?
         */

        //Entities
        QuestionMonster planningRoomMonster = new QuestionMonster("Scope Creep");
        QuestionMonster dailyScrumMonster = new QuestionMonster("Delay Monster");
        AssistantEntity assistant = new AssistantEntity();

        //Events
        ReverseWeepingAngelEvent regressionEvent = new ReverseWeepingAngelEvent(2);
        AssistantEncounterEvent assistantEvent = new AssistantEncounterEvent(assistant);

        //Rooms
        Room outside = new Outside(game, "outside");
        TaskRoomWithMonster planningRoom = new TaskRoomWithMonster(game, "planning", planningRoomMonster);
        TaskRoomWithMonster dailyScrumRoom = new TaskRoomWithMonster(game, "daily scrum", dailyScrumMonster);
        TaskRoom scrumBoardRoom = new TaskRoom(game, "scrum board");
        TaskRoom reviewRoom = new TaskRoom(game, "review");
        SpecialEventRoom assistantRoom = new SpecialEventRoom(game, "assistant", assistantEvent);
        TaskRoomWithEvent genericRoom1 = new TaskRoomWithEvent(game, "placeholder", regressionEvent);
        TaskRoom retrospectiveRoom = new TaskRoom(game, "retrospective");
        TaskRoom tiaRoom = new TaskRoom(game, "tia room");
        Room endingRoom = new EndingRoom(game);
        /*
            ItemId:
            gambling_potion
            hint_joker
            spareribs
            clearing_staff
            sword
         */
        // TODO: assign to QuestionTasks
        RewardProvider genericItemPool = new RandomRewardProvider(
                new SpecificReward("spareribs"),
                new SpecificReward("clearing_staff"),
                new SpecificReward("gambling_potion")
        );
        String specialSwordItem = "sword";
        String specialJokerItem = "hint_joker";
        //MultipleChoiceQuestion(String question, String[] options, HintProvider hint, TaskRoom parent, RewardProvider reward)
        //OpenQuestion(TaskRoom parent, HintProvider hint, RewardProvider reward, String question, String... answers)
        Task sprintPlanningQuestion1 = new MultipleChoiceQuestionWithMonster(
                "What do you NOT do while planning a sprint?",
                new String[]{"Make new user stories", "Collect user stories", "Shorten user stories", "Dealth with thou haggler`s horsesh*t"},
                new RandomHintProvider(USELESS_HINTS, new LiteralHintProvider("Most of the time, you get user stories from your customer")),
                planningRoom,
                new SpecificReward(specialJokerItem)
        );

        Task monsterSprintPlanningQuestion2 = new MultipleChoiceQuestionWithMonster(
                "Why would you play Planning Poker?",
                new String[]{"To lock in the time estimations", "To feed my gambling addiction", "To kill the hitmen coming after me"},
                new RandomHintProvider(USELESS_HINTS, new LiteralHintProvider("If I stop now, I'll never win it all back")),
                planningRoom
        );

        Task dailyScrumQuestion1 = new MultipleChoiceQuestionWithMonster(
                "What are standups for?",
                new String[]{"Discuss your tasks for the day", "Wasting time", "Prevent productivity"},
                new RandomHintProvider(USELESS_HINTS, new LiteralHintProvider("Be civilized")),
                dailyScrumRoom
        );

        Task monsterDailyScrumQuestion2 = new MultipleChoiceQuestion(
                "Why do scrum daily?",
                new String[]{"To stay productive", "To appease the scrum gods", "Lowering morale"},
                new RandomHintProvider(USELESS_HINTS, new LiteralHintProvider("What would the HR like you to say?")),
                dailyScrumRoom
        );

        Task scrumBoardQuestion = new OpenQuestion(
                scrumBoardRoom,
                new RandomHintProvider(USELESS_HINTS, new LiteralHintProvider("Most scrum masters keep track of things on their scrum boards")),
                new SpecificReward(specialSwordItem),
                "What is the scrum board for?",
                "cooperating", "working together", "planning", "track task statuses"
        );

        Task sprintReviewQuestion = new OpenQuestion(
                reviewRoom,
                new RandomHintProvider(USELESS_HINTS, new LiteralHintProvider("In what type of scenario do people roast your code in a PR?")),
                "What do you do once you've completed a sprint?",
                "review"
        );

        Task retrospectiveQuestion = new OpenQuestion(
                retrospectiveRoom,
                new RandomHintProvider(USELESS_HINTS, new LiteralHintProvider("What do cool guys NOT do while slowly walking away from an explosion?")),
                genericItemPool,
                "What do you do in a retrospective?",
                "look back", "find places to improve"
        );

        Task genericQuestion1 = new MultipleChoiceQuestion(
                "How many people are supposed to work together using scrum?",
                new String[]{"Any number of people", "0", "935.890275492 novemquadragintillion", "42", "sqrt(-1)"},
                new RandomHintProvider(USELESS_HINTS, new LiteralHintProvider("Yeah idk what that number means either.")),
                genericRoom1
        );

        /*Task genericQuestion2 = new MultipleChoiceQuestion(
                "Doth thine faith falter in the presence of his scrum majesty?",
                new String[]{"My allegiance lies with the scrum king", "I fear no man", "I fear my colleagues"},
                new RandomHintProvider(USELESS_HINTS, new LiteralHintProvider("A hint? Filthy heathen.")),

        );

        Task genericQuestion3 = new MultipleChoiceQuestion(
                "What do you do in russian roulette?",
                new String[]{"Fratricide", "Spin", "Don't spin"},
                new RandomHintProvider(USELESS_HINTS, new LiteralHintProvider("There is at least one thing in life that always solves your problems")),
                room
        );*/

        Task tiaQuestion = new MultipleChoiceQuestion(
                "What does 'TIA' stand for?",
                new String[]{"Transparency, Inspection and Adaptation.", "Tooth Is Aching", "Theory Isn't Apprehensive"},
                new RandomHintProvider(USELESS_HINTS, new LiteralHintProvider("This one is kinda obvious isn't it")),
                tiaRoom
        );

        //outside
        outside.putNeighboringRoom(UP_DIRECTION, planningRoom);

        //planningRoom
        planningRoom.putNeighboringRoom(UP_DIRECTION, dailyScrumRoom);
        planningRoom.putNeighboringRoom(DOWN_DIRECTION, outside);

        //dailyScrumRoom
        dailyScrumRoom.putNeighboringRoom(LEFT_DIRECTION, scrumBoardRoom);
        dailyScrumRoom.putNeighboringRoom(RIGHT_DIRECTION, assistantRoom);
        dailyScrumRoom.putNeighboringRoom(DOWN_DIRECTION, planningRoom);

        //scrumBoardRoom
        scrumBoardRoom.putNeighboringRoom(UP_DIRECTION, genericRoom1);
        scrumBoardRoom.putNeighboringRoom(RIGHT_DIRECTION, dailyScrumRoom);
        scrumBoardRoom.putNeighboringRoom(DOWN_DIRECTION, scrumBoardRoom);

        //assistantRoom
        assistantRoom.putNeighboringRoom(UP_DIRECTION, reviewRoom);
        assistantRoom.putNeighboringRoom(LEFT_DIRECTION, dailyScrumRoom);
        assistantRoom.putNeighboringRoom(RIGHT_DIRECTION, genericRoom1);

        //genericRoom1
        genericRoom1.putNeighboringRoom(RIGHT_DIRECTION, retrospectiveRoom);
        genericRoom1.putNeighboringRoom(DOWN_DIRECTION, scrumBoardRoom);
        genericRoom1.putNeighboringRoom(LEFT_DIRECTION, assistantRoom);

        //reviewRoom
        reviewRoom.putNeighboringRoom(LEFT_DIRECTION, retrospectiveRoom);
        reviewRoom.putNeighboringRoom(DOWN_DIRECTION, assistantRoom);

        //retrospectiveRoom
        retrospectiveRoom.putNeighboringRoom(LEFT_DIRECTION, genericRoom1);
        retrospectiveRoom.putNeighboringRoom(RIGHT_DIRECTION, reviewRoom);
        retrospectiveRoom.putNeighboringRoom(UP_DIRECTION, tiaRoom);

        //tiaRoom
        tiaRoom.putNeighboringRoom(DOWN_DIRECTION, retrospectiveRoom);
        tiaRoom.putNeighboringRoom(UP_DIRECTION, endingRoom);

        //Assign questions to monster
        planningRoomMonster.setTask(monsterSprintPlanningQuestion2);
        dailyScrumMonster.setTask(monsterDailyScrumQuestion2);

        /*TaskRoomWithMonster planningRoom = new TaskRoomWithMonster(game, "planning", planningRoomMonster);
        TaskRoomWithMonster dailyScrumRoom = new TaskRoomWithMonster(game, "daily scrum", dailyScrumMonster);
        TaskRoom scrumBoardRoom = new TaskRoom(game, "scrum board");
        TaskRoom reviewRoom = new TaskRoom(game, "review");
        SpecialEventRoom assistantRoom = new SpecialEventRoom(game, "assistant", assistantEvent);
        TaskRoomWithEvent genericRoom1 = new TaskRoomWithEvent(game, "placeholder", regressionEvent);
        TaskRoom retrospectiveRoom = new TaskRoom(game, "retrospective");
        TaskRoom tiaRoom = new TaskRoom(game, "placeholder");*/
        //Ass(ign) Tasks
        planningRoom.setTask(sprintPlanningQuestion1);
        dailyScrumRoom.setTask(dailyScrumQuestion1);
        scrumBoardRoom.setTask(scrumBoardQuestion);
        reviewRoom.setTask(sprintReviewQuestion);
        genericRoom1.setTask(genericQuestion1);
        retrospectiveRoom.setTask(retrospectiveQuestion);
        tiaRoom.setTask(tiaQuestion);

        return outside;
    }
}