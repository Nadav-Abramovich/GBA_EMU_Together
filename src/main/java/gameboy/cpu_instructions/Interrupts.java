package gameboy.cpu_instructions;

import gameboy.CPU;

import static gameboy.HelperFunctions.pop_from_stack_d16;

// We suppress this warning because this class and its methods are dynamically imported
// and therefore IntelliJ doesn't recognize their usage.
@SuppressWarnings("unused")
public class Interrupts implements CPUInstructions {
    @Opcode(value = 0xD9, length = 1, cycles = 4, should_update_pc = false)
    public static void reti(CPU cpu) {
        cpu.IME = true;
        cpu.PC.setValue(pop_from_stack_d16(cpu));
    }

    @Opcode(value = 0xF3, length = 1, cycles = 1)
    public static void di(CPU cpu) {
        cpu.IME = false;
    }

    @Opcode(value = 0xFB, length = 1, cycles = 1)
    public static void ei(CPU cpu) {
        cpu.IME = true;
    }
}
