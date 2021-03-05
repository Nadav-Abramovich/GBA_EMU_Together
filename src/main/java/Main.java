import Gameboy.Gameboy;

public class Main {

    public static void main(String[] args) {
        Gameboy gameboy = new Gameboy();
        while (true) {
            gameboy.tick();
        }
    }
}
