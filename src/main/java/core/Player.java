package core;

import observer.DeathNotifier;
import observer.Scoreboard;
import rooms.Room;

import java.util.HashSet;
import java.util.Set;

public class Player {
    public interface Observer {
        default void onScoreChange(Player player) {}
        default void onHealthChange(Player player) {}
        default void onDeath(Player player) {}
    }
    private final Set<Observer> observers = new HashSet(); //ToDo: assign observers in DataSeeder or wherever
    private Room currentRoom;
    private int health;
    private int maxHealth;
    private int score = 0;
    private boolean dead = false;

    public Player(Room room) {
        this.currentRoom = room;

        this.maxHealth = 3;
        this.health = this.maxHealth;

        this.addObserver(DeathNotifier.INSTANCE);
        this.addObserver(Scoreboard.INSTANCE);
    }

    public void setPosition(Room room) {
        this.currentRoom = room;
    }

    public String getInfo() {
        return String.format("HP: %d%n Score: %d", this.health, this.score);
    }

    public int getHealth() {
        return this.health;
    }

    public void damage(int amount) {
        if (amount < 0) throw new IllegalArgumentException("Cannot damage a negative amount");
        this.changeHealth(-amount);
    }
    public void heal(int amount) {
        if (amount < 0) throw new IllegalArgumentException("Cannot heal a negative amount");
        this.changeHealth(amount);
    }

    public void heal() {
        this.setHealth(Integer.MAX_VALUE);
    }
    public void kill() {
        this.setHealth(Integer.MIN_VALUE);
    }

    public void changeHealth(int delta) {
        this.setHealth(this.getHealth() + delta);
    }

    public void setHealth(int health) {
        this.health = health;
        this.updateHealth();
        for (Observer observer : this.observers) observer.onHealthChange(this);
    }

    private void updateHealth() {
        if (this.health <= 0) this.markDead();
        else if (this.health > this.maxHealth) this.health = this.maxHealth;
    }

    private void markDead() {
        this.dead = true;
        for (Observer observer : this.observers) observer.onDeath(this);
    }

    public boolean isDead() {
        return this.dead;
    }

    public void revive() {
        if (!this.dead) throw new IllegalStateException("Cannot revive an alive player");
        this.dead = false;
        this.heal();
    }

    public int getScore() {
        return this.score;
    }
    public void setScore(int score) {
        this.score = score;
        for (Observer observer : this.observers) observer.onScoreChange(this);
    }
    public void addScore(int amount) {
        if (amount < 0) throw new IllegalArgumentException("Cannot give negative score");
        this.changeScore(amount);
    }
    public void removeScore(int amount) {
        if (amount < 0) throw new IllegalArgumentException("Cannot remove negative score");
        this.changeScore(-amount);
    }
    public void changeScore(int delta) {
        this.setScore(this.getScore() + delta);
    }

    public void addObserver(Observer observer) {
        this.observers.add(observer);
    }
    public void removeObserver(Observer observer) {
        this.observers.remove(observer);
    }
}