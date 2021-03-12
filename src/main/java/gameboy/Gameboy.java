package gameboy;

import gameboy.Screen.Screen;

import java.nio.file.Files;
import java.nio.file.Paths;

public class Gameboy {
    public final Memory _memory = new Memory();
    private final CPU cpu;
    private final Screen screen;

    public Gameboy() {
        this.cpu = new CPU(_memory, this);
        this.screen = new Screen(cpu);
    }

    public void tick() {
        if(cpu.PC.getValue() == 0x235) {
            cpu.AF.A.setValue((byte)148);
        }
        if(cpu.PC.getValue() == 0x282a) {
            cpu.AF.A.setValue((byte)145);
        }
        cpu.tick();
        if(cpu.cycles % 500 == 0) {
            screen.loop();
        }
    }
}
