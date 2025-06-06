package core;

public class StatusManager {
	private GameStatus currentStatus;
	private GameStatus previousStatus;

	public StatusManager() {}
	public StatusManager(GameStatus initialStatus) {
		this();
		this.set(initialStatus);
	}

	public void set(GameStatus status) {
		this.previousStatus = this.currentStatus;
		this.currentStatus = status;
	}
	public GameStatus get() {
		return this.currentStatus;
	}

	public GameStatus getPrevious() {
		return this.previousStatus;
	}

	public boolean is(GameStatus status) {
		return this.currentStatus == status;
	}
	public boolean was(GameStatus status) {
		return this.previousStatus == status;
	}

	public void revert() {
		this.set(this.previousStatus);
	}
}