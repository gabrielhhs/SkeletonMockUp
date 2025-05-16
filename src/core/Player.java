package core;

import rooms.Room;

public class Player {
    private Room currentRoom;
    private int health;
    private int maxHealth;
    private int score;
    private boolean dead = false;

    public Player(Room room) {
        this.currentRoom = room;

        this.maxHealth = 3;
        this.health = this.maxHealth;
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
    }

    private void updateHealth() {
        if (this.health <= 0) this.markDead();
        else if (this.health > this.maxHealth) this.health = this.maxHealth;
    }

    private void markDead() {
        this.dead = true;
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
}
//Alles gebeurt in tekst (CLI)
//Je typt commando’s zoals: 'ga naar kamer 2'
//Je kunt op elk moment status intypen om te zien waar je bent, hoeveel kamers je al hebt gehaald,
// en of je nog monsters (impediments) hebt om op te lossen.