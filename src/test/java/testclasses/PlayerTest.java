package testclasses;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import testclasses.mockclasses.GameStub;
import testclasses.mockclasses.PlayerSpy;
import testclasses.mockclasses.RoomSpy;
import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
    private GameStub game;
    private PlayerSpy player;

    @BeforeEach
    void setUp() {
        game = new GameStub();
        RoomSpy roomSpy = new RoomSpy(game, "testRoom");
        player = new PlayerSpy(roomSpy);
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

    @Nested
    class DamageTests{
        @Test
        void damage_shouldThrowIllegalArgumentExeption() {
            assertThrows(java.lang.IllegalArgumentException.class, () -> player.damage(-1), "\"Cannot damage a negative amount\"");
        }
        @Test
        void damage_shouldDecreaseHealth() {
            int startingHealth = player.getHealth();

            player.damage(1);

            assertEquals(startingHealth - 1, player.getHealth());
        }
    }
    @Nested
    class HealTests{
        @Test
        void heal_shouldThrowIllegalArgumentExeption() {
            assertThrows(java.lang.IllegalArgumentException.class, () -> player.heal(-1), "\"Cannot heal a negative amount\"");
        }
        @Test
        void heal_shouldIncreaseHealth() {
            player.damage(2);
            int startingHealth = player.getHealth();

            player.heal(1);

            assertEquals(startingHealth + 1, player.getHealth());
        }
    }
}