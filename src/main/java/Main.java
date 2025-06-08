import core.Game;
import saving.JSONDataSaver;

import java.nio.file.Path;

public class Main {
    public static void main(String [] args) {
        new Game(System.in, new JSONDataSaver(Path.of(System.getProperty("user.dir")).resolve("./saves"))).start();
    }
}