package gameboy;

import java.nio.file.Files;
import java.nio.file.Paths;

public class Memory {
    public static final String SUCCESSFULLY_LOADED_BOOT_ROM_MSG = "[INFO] Successfully loaded boot ROM!";
    public static final String FAILED_TO_LOAD_BOOT_ROM_EXCEPTION = "[CRITICAL] Failed to load Boot ROM!";
    public static final String BOOSTRAP_ROM_PATH = "ROMS/BootWorld.gb";
    public static final String GAME_ROM_PATH = "ROMS/tetris.gb";
    private int bank_number = 1;

    private final byte[] _memory;
    public final byte[] boot_rom = new byte[0xFF + 1];
    public final byte[] game_rom = new byte[0xFFFFFF];
    public final int MEMORY_LENGTH = 0xFFFF + 1;
    public static boolean actions_buttons_selected = false;

    public byte keys_pressed = 0;

    public Memory() {
        _memory = new byte[MEMORY_LENGTH];
        _load_game_rom();
        _load_boot_rom();
    }

    public void write(int address, byte value) {
        if (address == 0xFF00) {
            // Action buttons
            if (value == 32 || value == 48) {
                actions_buttons_selected = true;
            }
            // direction keys
            else if (value == 16) {
                actions_buttons_selected = false;
            } else {
                System.out.println("Bad value written to 0xff00");
            }
        }
        if (address == 0x1C3A) {
            System.out.println("BAD MEMORY WRITE!");
            System.out.println(value);
            System.exit(0);
        }

        if (address == 0xFF46) {
            for (int i = 0; i <= 0x9F; i++) {
//                System.out.println("OK");
                char src = (char) (((value << 8) | i) & 0xFFFF);
                char dest = (char) ((0xFE00 | i) & 0xFFFF);
                _memory[dest] = _memory[src];
//                System.out.println("OK2");
            }
        }
        if (address < 0x2000) {
//            System.out.println("BADALACH");
        }
        if (address >= 0x2000 && address <= 0x3FFF) {
            System.out.println("OPALACH " + Integer.toHexString(value).toUpperCase());
            bank_number = value & 0x7F;
            if (value == 0) {
                bank_number = 1;
            }
//            System.out.println(bank_number);
        } else if (address <= 0x7FFF) {
            System.out.println("MANDALACH");
        }
        if (address == 0xFF50) {
            System.out.println("MM");
        }
        if (address == 0xFFFF) {
//            System.out.println("Interrupts");
        }
        if (address == 0xFF0F) {
//            System.out.println("Interrupts 2");
        }

        if (address == 0xFF11) {
//            System.out.println("Interrupts 2");
        }
        if (address == 0xFFB8 || address == 0xFFB9) {
            System.out.println("BANKING 2");
        }
        if (address == 0xC000 || address == 0xC09F) {
            System.out.println("TRANSFER");
        }
        _memory[address] = value;
    }

    public byte read_byte(int address) {
        return read_byte(address, false);
    }

    public byte read_byte(int address, boolean is_PC) {
        // KEYS
        if (address == 0xFF00) {
            // Action
            if (actions_buttons_selected) {
                return (byte) (keys_pressed & 15);
            }
            // Direction
            return (byte) ((keys_pressed >> 4) & 15);
        }
        if (address == 0xFF81) {
            return keys_pressed;
        }
        if (address >= 0x4a07 && address <= 0x4b6f) {
//            System.out.println("NOW");
        }
        if (address <= 0x7FFF) {
            if (_memory[0xFF50] == 0 && address <= 0xFF) {
                return boot_rom[address];
            }
            if(address <= 0x3FFF) {
                return game_rom[address];
            }
            return game_rom[(0x7FFF - 0x4000 + 1) * (bank_number-1) + address];
        }
        return _memory[address];
    }

    private void _load_boot_rom() {
        try {
            byte[] temp = Files.readAllBytes(Paths.get(BOOSTRAP_ROM_PATH));
            System.arraycopy(temp, 0, boot_rom, 0, temp.length);
            System.out.println(SUCCESSFULLY_LOADED_BOOT_ROM_MSG);
        } catch (java.io.IOException exception) {
            System.out.println(FAILED_TO_LOAD_BOOT_ROM_EXCEPTION);
        }
    }

    public void _load_game_rom() {
        // TODO: Fix length
        try {
            byte[] temp = Files.readAllBytes(Paths.get(GAME_ROM_PATH));
            System.arraycopy(temp, 0, game_rom, 0, temp.length);
            System.out.println(SUCCESSFULLY_LOADED_BOOT_ROM_MSG);
        } catch (java.io.IOException exception) {
            System.out.println(FAILED_TO_LOAD_BOOT_ROM_EXCEPTION);
        }
    }
}