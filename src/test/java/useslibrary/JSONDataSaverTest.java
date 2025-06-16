package useslibrary;

import core.Game;
import core.Player;
import core.StatusManager;
import items.Item;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import rooms.*;
import saving.JSONDataSaver;
import saving.SaverException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static core.GameStatus.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JSONDataSaverTest {
    @Mock private Game mockGame;
    @Mock private Player mockPlayer;
    @Mock private Room mockRoom1;
    @Mock private Room mockRoom2;
    @Mock private Item mockItem1;
    @Mock private Item mockItem2;
    @Mock private StatusManager mockManager;
    @Mock private JSONDataSaver saver;

    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        Map<Item, Integer> inventory = new HashMap<>();
        inventory.put(this.mockItem1, 1);
        inventory.put(this.mockItem2, 2);

        when(this.mockGame.getPlayer()).thenReturn(this.mockPlayer);
        when(this.mockRoom1.isCleared()).thenReturn(true);
        when(this.mockRoom2.isCleared()).thenReturn(false);
        when(this.mockRoom1.getName()).thenReturn("testRoom1");
        when(this.mockRoom2.getName()).thenReturn("testRoom2");
        when(this.mockGame.getStatusManager()).thenReturn(this.mockManager);
        when(this.mockManager.get()).thenReturn(SELECTING_ROOM);

        Set<Room> rooms = new HashSet<>(Arrays.asList(this.mockRoom1, this.mockRoom2));
        when(this.mockGame.collectRooms()).thenReturn(rooms);

        this.saver = new JSONDataSaver(this.tempDir);
    }

    @Test
    void save_shouldWriteToFile() throws Exception {
        when(this.mockPlayer.getHealth()).thenReturn(75);
        when(this.mockPlayer.getScore()).thenReturn(10);
        when(this.mockPlayer.getCurrentRoom()).thenReturn(this.mockRoom1);

        this.saver.save(this.mockGame, "testSave");

        Path saveFile = this.tempDir.resolve("testSave.json");
        assertTrue(Files.exists(saveFile));

        String content = Files.readString(saveFile);
        JSONObject json = new JSONObject(content);
        assertEquals("testRoom1", json.getJSONObject("player").getString("position"));
    }

    @Test
    void save_shouldThrowSaverExceptionOnIOExeption() {
        try (MockedStatic<Files> mockedFiles = mockStatic(Files.class)) {
            mockedFiles.when(() -> Files.createDirectory(any(Path.class)))
                    .thenThrow(new IOException("Test exception"));

            assertThrows(SaverException.class, () -> new JSONDataSaver(this.tempDir));
        }
    }

    @Test
    void serialize_shouldIncludePlayerData() throws Exception {
        when(this.mockPlayer.getCurrentRoom()).thenReturn(this.mockRoom1);
        when(this.mockRoom1.getName()).thenReturn("testRoom1");
        when(this.mockPlayer.getHealth()).thenReturn(100);
        when(this.mockPlayer.getScore()).thenReturn(10);

        this.saver.save(this.mockGame, "testSave");

        Path saveFile = this.tempDir.resolve("testSave.json");
        String jsonContent = Files.readString(saveFile);
        JSONObject json = new JSONObject(jsonContent);
        JSONObject playerJson = json.getJSONObject("player");

        assertEquals("testRoom1", playerJson.getString("position"));
        assertEquals(100, playerJson.getInt("health"));
        assertEquals(10, playerJson.getInt("score"));
    }

    @Test
    void serialize_shouldBeTrueRoomClear() throws Exception {
        when(this.mockPlayer.getCurrentRoom()).thenReturn(this.mockRoom1);
        when(this.mockRoom1.isCleared()).thenReturn(true);
        when(this.mockRoom2.isCleared()).thenReturn(false);
        when(this.mockPlayer.getHealth()).thenReturn(100);
        when(this.mockPlayer.getScore()).thenReturn(10);

        this.saver.save(this.mockGame, "testSave");

        Path saveFile = this.tempDir.resolve("testSave.json");
        String jsonContent = Files.readString(saveFile);
        JSONObject json = new JSONObject(jsonContent);
        JSONObject roomJson = json.getJSONObject("roomClears");

        assertTrue(roomJson.getBoolean(this.mockRoom1.getName()));
        assertFalse(roomJson.getBoolean(this.mockRoom2.getName()));
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
        Path saveFile = this.tempDir.resolve("testLoad.json");
        when(this.mockPlayer.getCurrentRoom()).thenReturn(this.mockRoom1);
        Files.writeString(saveFile, testData);

        this.saver.load(this.mockGame, "testLoad");

        verify(this.mockRoom1).setCleared(true);
        verify(this.mockRoom2).setCleared(false);
        verify(this.mockPlayer).setCurrentRoom(this.mockRoom1);
        verify(this.mockManager).set(SELECTING_ROOM);
        verify(this.mockPlayer).setHealth(100);
        verify(this.mockPlayer).setScore(10);
        verify(this.mockRoom1).enter();
    }

    @Test
    void saveExists_shouldExist() {
        when(this.mockPlayer.getCurrentRoom()).thenReturn(this.mockRoom1);
        when(this.mockRoom1.isCleared()).thenReturn(true);
        when(this.mockRoom2.isCleared()).thenReturn(false);
        when(this.mockPlayer.getHealth()).thenReturn(100);
        when(this.mockPlayer.getScore()).thenReturn(10);

        this.saver.save(this.mockGame, "testSave");

        assertTrue(this.saver.saveExists("testSave"));
    }

    @Test
    void saveExist_shouldNotExist() {
        assertFalse(this.saver.saveExists("testSave"));
    }

    @Test
    void getSaves_shouldReturnSaveNames() throws Exception {
        Files.createFile(this.tempDir.resolve("testSave1.json"));
        Files.createFile(this.tempDir.resolve("testSave2.json"));

        Set<String> saves = this.saver.getSaves();

        assertTrue(saves.contains("testSave1.json"));
        assertTrue(saves.contains("testSave2.json"));
    }
}