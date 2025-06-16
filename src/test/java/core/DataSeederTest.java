// this is wrong mb, too proud to let it go

package core;

import commands.Command;
import commands.commandslist.*;
import events.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.*;


import rooms.*;
import stratpattern.MultipleChoiceQuestion;

import java.util.Map;
import java.util.Set;

class DataSeederTest {
    @Mock
    private Game mockGame;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void generateRooms_shouldReturnOutsideRoom() {
        Room result = DataSeeder.generateRooms(this.mockGame);
        assertNotNull(result);
        assertInstanceOf(Outside.class, result);
    }

    @Test
    void generateRooms_shouldCreateRoomConnections() throws Exception {
        Room planning = null;
        Room dailyScrum = null;
        Room sideRoom = null;
        Room monsterRoom1 = null;
        Room monsterRoom2 = null;
        Room angelRoom = null;
        Room assistantRoom = null;

        String up = "up";
        String down = "down";
        String left = "left";
        String right = "right";
        String enter = "enter";

        Room outside = DataSeeder.generateRooms(this.mockGame);
        Map<String, Room> outsideRoomList = outside.getNeighboringRooms();


        for (Map.Entry<String, Room> entry : outsideRoomList.entrySet()) {
            if (entry.getKey().equals(enter)) {
                planning = entry.getValue();
                break;
            }
        }
        assertNotNull(planning);
        assertEquals("planning", planning.getName());

        Map<String, Room> planningRoomList = planning.getNeighboringRooms();
        outside = null;

        for (Map.Entry<String, Room> entry : planningRoomList.entrySet()) {
            if (entry.getKey().equals(up)) {
                dailyScrum = entry.getValue();
            } else if (entry.getKey().equals(down)) {
                outside = entry.getValue();
            }
        }
        assertNotNull(dailyScrum);
        assertEquals("daily scrum", dailyScrum.getName());
        assertNotNull(outside);
        assertEquals("outside", outside.getName());

        Map<String, Room> dailyScrumRoomList = dailyScrum.getNeighboringRooms();
        planning = null;

        for (Map.Entry<String, Room> entry : dailyScrumRoomList.entrySet()) {
            if (entry.getKey().equals(down)) {
                planning = entry.getValue();
            }else if (entry.getKey().equals(right)) {
                sideRoom = entry.getValue();
            }else if (entry.getKey().equals(up)) {
                monsterRoom1 = entry.getValue();
            }
        }
        assertNotNull(planning);
        assertEquals("planning", planning.getName());
        assertNotNull(sideRoom);
        assertEquals("sideroom", sideRoom.getName());
        assertNotNull(monsterRoom1);
        assertEquals("MonsterRoom1", monsterRoom1.getName());


        // Ik stop hier want wss willen we een andere layout met andere vragen/rooms maken.
    }

    @Test
    void generateRoom_shouldAsignTaskToRoom() {
        Room outside = DataSeeder.generateRooms(this.mockGame);
        Map<String, Room> outsideRoomList = outside.getNeighboringRooms();
        Room planning = null;

        for (Map.Entry<String, Room> entry : outsideRoomList.entrySet()) {
            if (entry.getKey().equals("enter")) {
                planning = entry.getValue();
                break;
            }
        }
        TaskRoom taskPlanning = (TaskRoom) planning;
        assert taskPlanning != null;
        assertNotNull(taskPlanning.getTaskHandler().getTask());
        assertInstanceOf(MultipleChoiceQuestion.class, taskPlanning.getTaskHandler().getTask());
    }

    @Test
    void generateRoom_shouldCreateMonsterWithTask() {
        Room outside = DataSeeder.generateRooms(this.mockGame);
        Map<String, Room> outsideRoomList = outside.getNeighboringRooms();
        Room planning = null;

        for (Map.Entry<String, Room> entry : outsideRoomList.entrySet()) {
            if (entry.getKey().equals("enter")) planning = entry.getValue();
        }

        assert planning != null;
        Map<String, Room> planningRoomList = planning.getNeighboringRooms();
        Room dailyScrum = null;

        for (Map.Entry<String, Room> entry : planningRoomList.entrySet()) {
            if (entry.getKey().equals("up")) dailyScrum = entry.getValue();
        }

        assert dailyScrum != null;
        Map<String, Room> dailyScrumRoomList = dailyScrum.getNeighboringRooms();
        Room mainMonsterRoom = null;

        for (Map.Entry<String, Room> entry : dailyScrumRoomList.entrySet()) {
            if (entry.getKey().equals("up")) mainMonsterRoom = entry.getValue();
        }

        assertNotNull(mainMonsterRoom);
        assertInstanceOf(TaskRoomWithMonster.class, mainMonsterRoom);
    }

    @Test
    void generateRoom_shouldCreateRoomWithEvents() {
        Room outside = DataSeeder.generateRooms(this.mockGame);
        Map<String, Room> outsideRoomList = outside.getNeighboringRooms();
        Room planning = null;

        for (Map.Entry<String, Room> entry : outsideRoomList.entrySet()) {
            if (entry.getKey().equals("enter")) planning = entry.getValue();
        }

        assert planning != null;
        Map<String, Room> planningRoomList = planning.getNeighboringRooms();
        Room dailyScrum = null;

        for (Map.Entry<String, Room> entry : planningRoomList.entrySet()) {
            if (entry.getKey().equals("up")) dailyScrum = entry.getValue();
        }

        assert dailyScrum != null;
        Map<String, Room> dailyScrumRoomList = dailyScrum.getNeighboringRooms();
        Room monsterRoom1 = null;

        for (Map.Entry<String, Room> entry : dailyScrumRoomList.entrySet()) {
            if (entry.getKey().equals("up")) monsterRoom1 = entry.getValue();
        }

        assert monsterRoom1 != null;
        Map<String, Room> monsterRoom1List = monsterRoom1.getNeighboringRooms();
        Room monsterRoom2 = null;

        for (Map.Entry<String, Room> entry : monsterRoom1List.entrySet()) {
            if (entry.getKey().equals("right")) monsterRoom2 = entry.getValue();
        }

        assert monsterRoom2 != null;
        Map<String, Room> monsterRoom2List = monsterRoom2.getNeighboringRooms();
        Room angelRoom = null;

        for (Map.Entry<String, Room> entry : monsterRoom2List.entrySet()) {
            if (entry.getKey().equals("right")) angelRoom = entry.getValue();
        }
        TaskRoomWithEvent eventAngel = (TaskRoomWithEvent) angelRoom;
        assertNotNull(angelRoom);
        assertInstanceOf(Event.class, eventAngel.getEventHandler().getEvent());
    }
}