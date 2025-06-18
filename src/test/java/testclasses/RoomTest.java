package testclasses;


import core.Game;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import testclasses.mockclasses.GameStub;
import testclasses.mockclasses.PlayerSpy;
import testclasses.mockclasses.RoomSpy;
import testclasses.mockclasses.StatusManagerStub;

class RoomTest {

    @Test
    void enter_shouldCallOnEnterAndStartHandleUncleared() {
        RoomSpy mockRoom = new RoomSpy(null, "Test Room");
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
        RoomSpy mockRoom = new RoomSpy(null, "Test Room");
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
        RoomSpy room = new RoomSpy(gameMock, "Test Room");
        RoomSpy neighbor = new RoomSpy(gameMock, "Neighbor");
        room.addNeighbor("up", neighbor);

        room.consume("up");

        assertTrue(room.consumeCalled);
        assertEquals("up", room.lastConsumeInput);
    }

    @Test
    void handleUncleared_whenEntered() {
        GameStub gameStub = new GameStub();
        RoomSpy roomSpy = new RoomSpy(gameStub, "Test Room");
        StatusManagerStub managerStub = new StatusManagerStub();
        managerStub.setStatus(false);
        gameStub.setTestStatusManager(managerStub);
        roomSpy.simulateUnclearedState();

        roomSpy.enter();

        assertTrue(roomSpy.handleUnclearedCalled);
    }
}