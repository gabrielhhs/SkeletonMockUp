package saving;

import core.Game;
import core.GameStatus;
import core.Player;
import core.StatusManager;
import items.Item;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.MockedStatic;
import rooms.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static core.GameStatus.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JSONDataSaverTest {
    private Game mockGame;
    private Player mockPlayer;
    private Room mockRoom1;
    private Room mockRoom2;
    private Item mockItem1;
    private Item mockItem2;
    private StatusManager mockManager;
    private JSONDataSaver saver;

    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp() {
        mockGame = mock(Game.class);
        mockPlayer = mock(Player.class);
        mockRoom1 = mock(Room.class);
        mockRoom2 = mock(Room.class);
        mockItem1 = mock(Item.class);
        mockItem2 = mock(Item.class);
        mockManager = mock(StatusManager.class);
        Map<Item, Integer> inventory = new HashMap<>();
        inventory.put(mockItem1, 1);
        inventory.put(mockItem2, 2);

        when(mockGame.getPlayer()).thenReturn(mockPlayer);
        when(mockPlayer.getInventory()).thenReturn(inventory);
        when(mockRoom1.isCleared()).thenReturn(true);
        when(mockRoom2.isCleared()).thenReturn(false);
        when(mockRoom1.getName()).thenReturn("testRoom1");
        when(mockRoom2.getName()).thenReturn("testRoom2");
        when(mockGame.getStatusManager()).thenReturn(mockManager);
        when(mockManager.get()).thenReturn(SELECTING_ROOM);

        Set<Room> rooms = new HashSet<>(Arrays.asList(mockRoom1, mockRoom2));
        when(mockGame.collectRooms()).thenReturn(rooms);

        saver = new JSONDataSaver(tempDir);
    }

    @Test
    void save_shouldWriteToFile() throws Exception {
        when(mockPlayer.getHealth()).thenReturn(75);
        when(mockPlayer.getScore()).thenReturn(10);
        when(mockPlayer.getCurrentRoom()).thenReturn(mockRoom1);

        saver.save(mockGame, "testSave");

        Path saveFile = tempDir.resolve("testSave.json");
        assertTrue(Files.exists(saveFile));

        String content = Files.readString(saveFile);
        JSONObject json = new JSONObject(content);
        assertEquals("testRoom1", json.getJSONObject("player").getString("position"));
    }

    @Test
    void save_shouldThrowSaverExceptionOnIOExeption() throws Exception {
        try (MockedStatic<Files> mockedFiles = mockStatic(Files.class)) {
            mockedFiles.when(() -> Files.createDirectory(any(Path.class)))
                    .thenThrow(new IOException("Test exception"));

            assertThrows(SaverException.class, () -> new JSONDataSaver(tempDir));
        }
    }

    @Test
    void serialize_shouldIncludePlayerData() throws Exception {
        when(mockPlayer.getCurrentRoom()).thenReturn(mockRoom1);
        when(mockRoom1.getName()).thenReturn("testRoom1");
        when(mockPlayer.getHealth()).thenReturn(100);
        when(mockPlayer.getScore()).thenReturn(10);

        saver.save(mockGame, "testSave");

        Path saveFile = tempDir.resolve("testSave.json");
        String jsonContent = Files.readString(saveFile);
        JSONObject json = new JSONObject(jsonContent);
        JSONObject playerJson = json.getJSONObject("player");

        assertEquals("testRoom1", playerJson.getString("position"));
        assertEquals(100, playerJson.getInt("health"));
        assertEquals(10, playerJson.getInt("score"));
    }

    @Test
    void serialize_shouldBeTrueRoomClear() throws Exception{
        when(mockPlayer.getCurrentRoom()).thenReturn(mockRoom1);
        when(mockRoom1.isCleared()).thenReturn(true);
        when(mockRoom2.isCleared()).thenReturn(false);
        when(mockPlayer.getHealth()).thenReturn(100);
        when(mockPlayer.getScore()).thenReturn(10);

        saver.save(mockGame, "testSave");

        Path saveFile = tempDir.resolve("testSave.json");
        String jsonContent = Files.readString(saveFile);
        JSONObject json = new JSONObject(jsonContent);
        JSONObject roomJson = json.getJSONObject("roomClears");

        assertTrue(roomJson.getBoolean(mockRoom1.getName()));
        assertFalse(roomJson.getBoolean(mockRoom2.getName()));
    }

    @Test
    void load_shouldReadFromFile() throws Exception {
        String testData = """
                {
                    "roomClears": {
                        "testRoom1": true,
                        "testRoom2": false
                    },
                    "status": "SELECTING_ROOM",
                    "player": {
                        "position": "testRoom1",
                        "health": 100,
                        "score": 10,
                        "inventory": {}
                    }
                }""";
        Path saveFile = tempDir.resolve("testLoad.json");
        when(mockPlayer.getCurrentRoom()).thenReturn(mockRoom1);
        Files.writeString(saveFile, testData);


        saver.load(mockGame, "testLoad");

        verify(mockRoom1).setCleared(true);
        verify(mockRoom2).setCleared(false);

        verify(mockPlayer).setCurrentRoom(mockRoom1);
        verify(mockManager).set(SELECTING_ROOM);
        verify(mockPlayer).setHealth(100);
        verify(mockPlayer).setScore(10);
        verify(mockRoom1).enter();
    }

    @Test
    void saveExists_shouldExist() throws Exception {
        when(mockPlayer.getCurrentRoom()).thenReturn(mockRoom1);
        when(mockRoom1.isCleared()).thenReturn(true);
        when(mockRoom2.isCleared()).thenReturn(false);
        when(mockPlayer.getHealth()).thenReturn(100);
        when(mockPlayer.getScore()).thenReturn(10);

        saver.save(mockGame, "testSave");

        assertTrue(saver.saveExists("testSave"));
    }

    @Test
    void saveExist_shouldNotExist() throws Exception {
        assertFalse(saver.saveExists("testSave"));
    }

    @Test
    void getSaves_shouldReturnSaveNames() throws Exception {
        Files.createFile(tempDir.resolve("testSave1.json"));
        Files.createFile(tempDir.resolve("testSave2.json"));

        Set<String> saves = saver.getSaves();

        assertTrue(saves.contains("testSave1.json"));
        assertTrue(saves.contains("testSave2.json"));
    }
}