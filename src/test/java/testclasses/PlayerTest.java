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
        this.game = new GameStub();
        RoomSpy roomSpy = new RoomSpy(this.game, "testRoom");
        this.player = new PlayerSpy(roomSpy);
    }

    @Test
    void markDead_shouldKillPlayerIfHealthIsZero() {
        this.player.damage(4);
        assertTrue(this.player.isDead());
    }
    @Test
    void markDead_shouldNotKillPlayerIfHealthIsOne() {
        this.player.damage(2);
        assertFalse(this.player.isDead());
    }
    @Test
    void updateHealth_shouldCapAtMaxHealth() {
        this.player.heal(4);
        assertEquals(this.player.getHealth(), this.player.getMaxHealth());
    }
    @Test
    void updateHealth_shouldCapAtMaxHealthWhenFullHeal() {
        this.player.heal();
        assertEquals(this.player.getMaxHealth(), this.player.getHealth());
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