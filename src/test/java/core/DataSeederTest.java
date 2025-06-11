package core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.*;


import rooms.Outside;
import rooms.Room;
import rooms.TaskRoom;
import stratpattern.MultipleChoiceQuestion;

import java.util.Map;

class DataSeederTest {
    @Mock
    private Game mockGame;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void generateRooms_shouldReturnOutsideRoom() {
        Room result = DataSeeder.generateRooms(mockGame);
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

        Room outside = DataSeeder.generateRooms(mockGame);
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


        // Ik stop hier want wss willen we een andere layout met andere vragen maken.
    }

    @Test
    void generateRoom_shouldAsignTaskToRoom() {
        Room outside = DataSeeder.generateRooms(mockGame);
        Map<String, Room> outsideRoomList = outside.getNeighboringRooms();
        Room planning = null;

        for (Map.Entry<String, Room> entry : outsideRoomList.entrySet()) {
            if (entry.getKey().equals("enter")) {
                planning = entry.getValue();
                break;
            }
        }
        assertNotNull(planning);
        assertInstanceOf(MultipleChoiceQuestion.class, planning.k());
    }


    @Test
    void generateUselessHints() {

    }

    @Test
    void getCommands() {
    }
}