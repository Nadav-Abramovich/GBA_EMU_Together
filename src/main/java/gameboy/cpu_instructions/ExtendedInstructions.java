package gameboy.cpu_instructions;

import gameboy.CPU;
import gameboy.Flags;

// We suppress this warning because this class and its methods are dynamically called
// and therefore IntelliJ doesn't recognize their usage.
@SuppressWarnings("unused")
public class ExtendedInstructions implements CPUInstructions {
    @Opcode(value = 0xCB7C, length = 2)
    public static void bit7H(CPU cpu) {
        int H = (cpu.HL.getValue() >> 15);

        if (H == 0) {
            cpu.turnOnFlags(Flags.ZERO);
        }
        else {
            cpu.turnOffFlags(Flags.ZERO);
        }
        cpu.turnOffFlags(Flags.SUBTRACTION);
        cpu.turnOnFlags(Flags.HALF_CARRY);
    }
}