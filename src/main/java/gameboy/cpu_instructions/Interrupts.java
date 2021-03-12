package gameboy.cpu_instructions;

import gameboy.CPU;

import static gameboy.HelperFunctions.pop_from_stack_d16;

// We suppress this warning because this class and its methods are dynamically imported
// and therefore IntelliJ doesn't recognize their usage.
@SuppressWarnings("unused")
public class Interrupts implements CPUInstructions {
    @Opcode(value = 0xD9, length = 1)
    public static void reti(CPU cpu) {
        // TODO: Enable master interrupt flag
        cpu.PC.setValue(pop_from_stack_d16(cpu));
    }
}
