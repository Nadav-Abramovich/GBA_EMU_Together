package gameboy.cpu_instructions;

import gameboy.CPU;
import gameboy.Flags;

// We suppress this warning because this class and its methods are dynamically imported
// and therefore IntelliJ doesn't recognize their usage.
@SuppressWarnings("unused")
public class NOP implements CPUInstructions {
    @Opcode(value = 0x0, length = 1, cycles = 1)
    public static void nop() {
    }

    @Opcode(value = 0x37, length = 1, cycles = 1)
    public static void scf() {
        CPU.turnOffFlags((byte) (Flags.HALF_CARRY | Flags.SUBTRACTION));
        CPU.turnOnFlags(Flags.CARRY);
    }
}
