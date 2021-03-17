package gameboy;

import gameboy.Screen.Screen;
import gameboy.Screen.Screen_low;

public class Gameboy {
    public final Memory _memory = new Memory();
    private final CPU cpu;
    private final Screen screen;

//    private final Screen_low screen_low;

    public Gameboy() {
        this.cpu = new CPU(_memory, this);
        this.screen = new Screen(cpu);
//        this.screen_low = new Screen_low(cpu);
    }

    public void tick() {
        if (cpu.PC.getValue() == 0x235) {
            cpu.AF.A.setValue((byte) 148);
        }
        if (cpu.PC.getValue() == 0x282a) {
            cpu.AF.A.setValue((byte) 145);
        }
        cpu.tick();

        screen.loop(cpu.cycles);
//        screen_low.loop(cpu.cycles);
    }
}
