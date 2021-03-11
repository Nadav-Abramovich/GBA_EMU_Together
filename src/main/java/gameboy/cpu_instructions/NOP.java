package gameboy.cpu_instructions;

import gameboy.CPU;

// We suppress this warning because this class and its methods are dynamically imported
// and therefore IntelliJ doesn't recognize their usage.
@SuppressWarnings("unused")
public class NOP implements CPUInstructions {
    @Opcode(value = 0x0, length = 1)
    public static void nop(CPU cpu) {
    }
}
