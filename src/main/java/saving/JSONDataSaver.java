package saving;

import core.Game;
import core.GameStatus;
import core.Player;
import org.json.JSONObject;
import rooms.Room;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class JSONDataSaver implements DataSaver {
	private final Path savesPath;

	public JSONDataSaver(Path savesPath) {
		this.savesPath = savesPath;
		try {
			Files.createDirectory(savesPath);
		} catch (FileAlreadyExistsException _) {} catch (IOException e) {
			throw new SaverException(e);
		}
	}

	private Path getSavePath(String saveName) {
		return this.savesPath.resolve(String.format("%s.json", saveName));
	}

	private String serialize(Game game) {
		JSONObject dataObject = new JSONObject();

		JSONObject roomClearsObject = new JSONObject();
		for (Room room : game.collectRooms()) {
			roomClearsObject.put(room.getName(), room.isCleared());
		}
		dataObject.put("roomClears", roomClearsObject);

		dataObject.put("status", game.getStatusManager().get().toString());

		JSONObject playerObject = new JSONObject();
		{
			Player player = game.getPlayer();
			playerObject.put("position", player.getCurrentRoom().getName());
			playerObject.put("health", player.getHealth());
			playerObject.put("score", player.getScore());
			playerObject.put("inventory", new JSONObject(player.getInventory()));
		}
		dataObject.put("player", playerObject);

		return dataObject.toString();
	}

	private void deserialize(Game game, String data) {
		JSONObject dataObject = new JSONObject(data);
		Set<Room> allRooms = game.collectRooms();

		JSONObject roomClearsObject = dataObject.getJSONObject("roomClears");
		for (Room room : allRooms) {
			String key = room.getName();
			if (roomClearsObject.has(key)) {
				room.setCleared(roomClearsObject.getBoolean(key));
			}
		}

		game.getStatusManager().set(GameStatus.valueOf(dataObject.getString("status")));

		JSONObject playerObject = dataObject.getJSONObject("player");
		{
			Player player = game.getPlayer();
			String roomName = playerObject.getString("position");
			for (Room room : allRooms) {
				if (room.getName().equals(roomName)) {
					player.setCurrentRoom(room);
					break;
				}
			}
			player.setHealth(playerObject.getInt("health"));
			player.setScore(playerObject.getInt("score"));
			// TODO: Put items in a registry to be able to retrieve them
		}
	}

	@Override
	public void save(Game game, String saveName) throws SaverException {
		String data = this.serialize(game);

		try (FileWriter writer = new FileWriter(this.getSavePath(saveName).toFile())) {
			writer.write(data);
		} catch (IOException e) {
			throw new SaverException(e);
		}
	}

	@Override
	public void load(Game game, String saveName) throws SaverException {
		try (BufferedReader reader = new BufferedReader(new FileReader(this.getSavePath(saveName).toFile()))) {
			String data = reader.lines().reduce("", (prev, v) -> prev + v);

			this.deserialize(game, data);

			game.getPlayer().getCurrentRoom().enter();
		} catch (IOException e) {
			throw new SaverException(e);
		}
	}

	@Override
	public Set<String> getSaves() {
		return Arrays.stream(this.savesPath.toFile().listFiles())
			.map(file -> file.getName())
			.collect(Collectors.toSet());
	}

	@Override
	public boolean saveExists(String saveName) {
		return this.getSavePath(saveName).toFile().exists();
	}
}