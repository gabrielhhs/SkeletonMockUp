package saving;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import saving.mockclasses.GameStub;
import saving.mockclasses.PlayerMock;
import saving.mockclasses.RoomSpy;
import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
    private GameStub game;
    private PlayerMock player;

    @BeforeEach
    void setUp() {
        game = new GameStub();
        RoomSpy roomSpy = new RoomSpy(game, "testRoom");
        player = new PlayerMock(roomSpy);
    }

    @Test
    void markDead_shouldKillPlayerIfHealthIsZero() {
        player.damage(4);
        assertTrue(player.isDead());
    }
    @Test
    void markDead_shouldNotKillPlayerIfHealthIsOne() {
        player.damage(2);
        assertFalse(player.isDead());
    }
    @Test
    void updateHealth_shouldCapAtMaxHealth() {
        player.heal(4);
        assertEquals(player.getHealth(), player.getMaxHealth());
    }
    @Test
    void updateHealth_shouldCapAtMaxHealthWhenFullHeal() {
        player.heal();
        assertEquals(player.getMaxHealth(), player.getHealth());
    }
}