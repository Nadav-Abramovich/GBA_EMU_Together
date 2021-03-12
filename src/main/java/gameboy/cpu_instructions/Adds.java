package gameboy.cpu_instructions;

import gameboy.CPU;
import gameboy.Flags;

// We suppress this warning because this class and its methods are dynamically imported
// and therefore IntelliJ doesn't recognize their usage.
@SuppressWarnings("unused")
public class Adds implements CPUInstructions {
    @Opcode(value = 0x09, length = 1, cycles = 2)
    public static void add_hl_bc(CPU cpu) {
        int sum = cpu.HL.getValue() + cpu.BC.getValue();
        if (Integer.toUnsignedLong(sum) > 65535) {
            cpu.turnOnFlags((byte)(Flags.CARRY | Flags.HALF_CARRY));
        } else {
            cpu.turnOffFlags((byte)(Flags.CARRY | Flags.HALF_CARRY));
        }
        cpu.HL.increment(cpu.BC.getValue());
        cpu.turnOffFlags(Flags.SUBTRACTION);
    }

    @Opcode(value = 0x19, length = 1, cycles = 2)
    public static void add_hl_de(CPU cpu) {
        int sum = cpu.HL.getValue() + cpu.DE.getValue();
        if (Integer.toUnsignedLong(sum) > 65535) {
            cpu.turnOnFlags((byte)(Flags.CARRY | Flags.HALF_CARRY));
        } else {
            cpu.turnOffFlags((byte)(Flags.CARRY | Flags.HALF_CARRY));
        }
        cpu.HL.increment(cpu.DE.getValue());
        cpu.turnOffFlags(Flags.SUBTRACTION);
    }
}
