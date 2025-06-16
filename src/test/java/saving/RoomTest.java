package saving;


import core.Game;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import saving.mockclasses.GameStub;
import saving.mockclasses.PlayerMock;
import saving.mockclasses.RoomStub;
import saving.mockclasses.StatusManagerStub;

class RoomTest {

    @Test
    void enter_shouldCallOnEnterAndStartHandleUncleared()
        RoomStub mockRoom = new RoomStub(null, "Test Room");
        mockRoom.simulateUnclearedState();
        PlayerMock mockPlayer = new PlayerMock(mockRoom);
        GameStub gameMock = new GameStub(mockPlayer);
        mockRoom.setParent(gameMock);
        StatusManagerStub mockManager = new StatusManagerStub();
        gameMock.setTestStatusManager(mockManager);
        gameMock.testStatusManager = mockManager;

        mockRoom.enter();

        assertTrue(mockRoom.onEnterCalled);
        assertTrue(mockPlayer.setCurrentRoomCalled);
        assertTrue(mockManager.getCalled);
        assertTrue(mockRoom.handleUnclearedCalled);
    }

    @Test
    void enter_shouldCallOnEnterAndStartChooseRoom() {
        RoomStub mockRoom = new RoomStub(null, "Test Room");
        mockRoom.simulateClearedState();
        PlayerMock mockPlayer = new PlayerMock(mockRoom);
        GameStub gameMock = new GameStub(mockPlayer);
        mockRoom.setParent(gameMock);
        StatusManagerStub mockManager = new StatusManagerStub();
        gameMock.setTestStatusManager(mockManager);
        gameMock.testStatusManager = mockManager;

        mockRoom.enter();

        assertTrue(mockRoom.onEnterCalled);
        assertTrue(mockPlayer.setCurrentRoomCalled);
        assertTrue(mockManager.getCalled);
        assertTrue(mockRoom.chooseRoomCalled);
    }

    @Test
    void consume_shouldTrackInput() {
        Game gameMock = new GameStub();
        RoomStub room = new RoomStub(gameMock, "Test Room");
        RoomStub neighbor = new RoomStub(gameMock, "Neighbor");
        room.addNeighbor("up", neighbor);

        room.consume("up");

        assertTrue(room.consumeCalled);
        assertEquals("up", room.lastConsumeInput);
    }

    @Test
    void handleUncleared_whenEntered() {
        Game gameMock = new GameStub();
        RoomStub room = new RoomStub(gameMock, "Test Room");
        room.simulateUnclearedState();

        room.enter();

        assertTrue(room.handleUnclearedCalled);
    }
}