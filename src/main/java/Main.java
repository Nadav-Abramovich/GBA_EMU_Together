import gameboy.Gameboy;

public class Main {
    // TODO: replace System.exit with running = false somehow.
    public static boolean running = true;
    public static void main(String[] args) {
        Gameboy gameboy = new Gameboy();
        while (running) {
            gameboy.tick();
        }
    }
}
