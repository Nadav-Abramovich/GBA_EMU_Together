package gameboy;

import java.nio.file.Files;
import java.nio.file.Paths;

public class Gameboy {
    final CPU cpu;
    final byte[] _memory = new byte[0xFFFF];

    public Gameboy() {
        _load_rom();
        this.cpu = new CPU(_memory);
    }

    private void _load_rom() {
        try {
            byte[] temp = Files.readAllBytes(Paths.get("ROMS/BootWorld.gb"));
            System.arraycopy(temp, 0, _memory, 0, temp.length);
            System.out.println("[INFO] Successfully loaded boot ROM!");
        } catch (java.io.IOException exception) {
            System.out.println("[CRITICAL] Failed to load Boot ROM!");
        }
    }

    public void tick() {
        cpu.tick();
    }
}
