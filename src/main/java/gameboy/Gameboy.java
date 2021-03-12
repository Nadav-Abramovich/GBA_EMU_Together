package gameboy;

import gameboy.Scrn.Screen;

import java.nio.file.Files;
import java.nio.file.Paths;

public class Gameboy {
    public static final String SUCCESSFULLY_LOADED_BOOT_ROM_MSG = "[INFO] Successfully loaded boot ROM!";
    public static final String FAILED_TO_LOAD_BOOT_ROM_EXCEPTION = "[CRITICAL] Failed to load Boot ROM!";
    public static final String BOOSTRAP_ROM_PATH = "ROMS/BootWorld.gb";
    public static final String GAME_ROM_PATH = "ROMS/Game.gb";

    final CPU cpu;
    final byte[] _memory = new byte[0xFFFF];
    final Screen screen;

    public Gameboy() {
        _load_game_rom();
        _load_boot_rom();
        this.cpu = new CPU(_memory);
        this.screen = new Screen(cpu);
    }

    private void _load_boot_rom() {
        try {
            byte[] temp = Files.readAllBytes(Paths.get(BOOSTRAP_ROM_PATH));
            System.arraycopy(temp, 0, _memory, 0, temp.length);
            System.out.println(SUCCESSFULLY_LOADED_BOOT_ROM_MSG);
        } catch (java.io.IOException exception) {
            System.out.println(FAILED_TO_LOAD_BOOT_ROM_EXCEPTION);
        }
    }

    private void _load_game_rom() {
        // TODO: Fix length
        try {
            byte[] temp = Files.readAllBytes(Paths.get(GAME_ROM_PATH));
            System.arraycopy(temp, 0, _memory, 0, _memory.length);
            System.out.println(SUCCESSFULLY_LOADED_BOOT_ROM_MSG);
        } catch (java.io.IOException exception) {
            System.out.println(FAILED_TO_LOAD_BOOT_ROM_EXCEPTION);
        }
    }

    public void tick() {
        cpu.tick();
        screen.loop();
    }
}
