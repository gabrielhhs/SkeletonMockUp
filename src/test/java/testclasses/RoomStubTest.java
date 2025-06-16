package testclasses;


import core.Game;
import core.StatusManager;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import testclasses.mockclasses.GameStub;
import testclasses.mockclasses.PlayerSpy;
import testclasses.mockclasses.RoomStub;
import testclasses.mockclasses.StatusManagerStub;

class RoomStubTest {

    @Test
    void enter_shouldCallOnEnterAndStartHandleUncleared() {
        RoomStub mockRoom = new RoomStub(null, "Test Room"); // Temporary null Game
        mockRoom.simulateUnclearedState();
        PlayerSpy mockPlayer = new PlayerSpy(mockRoom);
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
        RoomStub mockRoom = new RoomStub(null, "Test Room"); // Temporary null Game
        mockRoom.simulateClearedState();
        PlayerSpy mockPlayer = new PlayerSpy(mockRoom);
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
        GameStub gameMock = new GameStub();
        RoomStub room = new RoomStub(gameMock, "Test Room");
        StatusManager manager = new StatusManagerStub();
        gameMock.setTestStatusManager(manager);
        room.simulateUnclearedState();

        room.enter();

        assertTrue(room.handleUnclearedCalled);
    }
}