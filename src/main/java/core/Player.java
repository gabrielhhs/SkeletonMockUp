package core;

import observer.*;
import rooms.Room;

import java.util.*;

public class Player {
    public interface Observer {
        default void onScoreChange(Player player) {}
        default void onHealthChange(Player player) {}
        default void onDeath(Player player) {}
        default void onResultChange(Player player, int result) {}
    }
    private final Set<Observer> observers = new HashSet<>(); //ToDo: assign observers in DataSeeder or wherever
    private Room currentRoom;
    private int health;
    private int maxHealth;
    private int score = 0;
    private int planningRoomResult;
    private int dailyScrumRoomResult;
    private int scrumBoardRoomResult;
    private int reviewRoomResult;
    private int genericRoom1Result;
    private int retrospectiveRoomResult;
    private int tiaRoomResult;
    private boolean dead = false;
    private final Map<String, Integer> inventory = new HashMap<>();

    public Player(Room room) {
        this.currentRoom = room;

        this.maxHealth = 3;
        this.health = this.maxHealth;

        this.addObserver(new DeathNotifier(System.out));
        this.addObserver(new Scoreboard(System.out));
        // this.addObserver(new RoomObserver(System.out));
    }

    public void setPlanningRoomResult(int planningRoomResult) {
        this.planningRoomResult = planningRoomResult;
    }

    public void setDailyScrumRoomResult(int dailyScrumRoomResult) {
        this.dailyScrumRoomResult = dailyScrumRoomResult;
    }

    public void setScrumBoardRoomResult(int scrumBoardRoomResult) {
        this.scrumBoardRoomResult = scrumBoardRoomResult;
    }

    public void setReviewRoomResult(int reviewRoomResult) {
        this.reviewRoomResult = reviewRoomResult;
    }

    public void setGenericRoom1Result(int genericRoom1Result) {
        this.genericRoom1Result = genericRoom1Result;
    }

    public void setRetrospectiveRoomResult(int retrospectiveRoomResult) {
        this.retrospectiveRoomResult = retrospectiveRoomResult;
    }

    public void setTiaRoomResult(int tiaRoomResult) {
        this.tiaRoomResult = tiaRoomResult;
    }

    //planningRoom.setTask(sprintPlanningQuestion1);
    //        dailyScrumRoom.setTask(dailyScrumQuestion1);
    //        scrumBoardRoom.setTask(scrumBoardQuestion);
    //        reviewRoom.setTask(sprintReviewQuestion);
    //        genericRoom1.setTask(genericQuestion1);
    //        retrospectiveRoom.setTask(retrospectiveQuestion);
    //        tiaRoom.setTask(tiaQuestion);
    public void displayRoomResults() {
        new PlanningRoomObserver().displayResult(this, this.planningRoomResult);
        new DailyScrumRoomObserver().displayResult(this, this.dailyScrumRoomResult);
        new ScrumBoardRoomObserver().displayResult(this, this.scrumBoardRoomResult);
        new ReviewRoomObserver().displayResult(this, this.reviewRoomResult);
        new GenericRoom1Observer().displayResult(this, this.genericRoom1Result);
        new RetrospectiveRoomObserver().displayResult(this, this.retrospectiveRoomResult);
        new TiaRoomObserver().displayResult(this, this.tiaRoomResult);
    }

    public String getInfo() {
        return String.format("HP: %d%n Score: %d", this.health, this.score);
    }

    public int getHealth() {
        return this.health;
    }
    public int getMaxHealth() {
        return this.maxHealth;
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

    public void giveItem(String itemId, int count) {
        if (this.inventory.containsKey(itemId)) {
            this.inventory.put(itemId, this.inventory.get(itemId) + count);
        } else {
            this.inventory.put(itemId, count);
        }
    }
    public void giveItem(String itemId) {
        this.giveItem(itemId, 1);
    }

    public void takeItem(String itemId, int count) {
        if (this.inventory.containsKey(itemId)) {
            this.inventory.put(itemId, this.inventory.get(itemId) - count);
            if (this.inventory.get(itemId) <= 0) this.inventory.remove(itemId);
        } else {
            throw new IllegalStateException("Cannot take items that a player does not have");
        }
    }
    public void takeItem(String itemId) {
        this.takeItem(itemId, 1);
    }

    public void clearInventory() {
        this.inventory.clear();
    }

    public Map<String, Integer> getInventory() {
        return Collections.unmodifiableMap(this.inventory);
    }

    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }
}