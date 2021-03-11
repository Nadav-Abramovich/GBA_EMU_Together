package gameboy.cpu_instructions;

import gameboy.CPU;
import gameboy.Flags;

// We suppress this warning because this class and its methods are dynamically imported
// and therefore IntelliJ doesn't recognize their usage.
@SuppressWarnings("unused")
public class Rotates implements CPUInstructions {
    @Opcode(value = 0x07, length = 1)
    public static void rlca(CPU cpu) {
        byte new_lsb = 0;
        if((cpu.AF.A.getValue() & 128) != 0) {
            cpu.turnOnFlags(Flags.CARRY);
            new_lsb = 1;
        } else {
            cpu.turnOffFlags(Flags.CARRY);
        }
        cpu.AF.A.setValue((byte)((cpu.AF.A.getValue()<<1) | new_lsb));
        cpu.turnOffFlags((byte)(Flags.ZERO | Flags.SUBTRACTION | Flags.HALF_CARRY));
    }

    @Opcode(value = 0x17, length = 1)
    public static void rla(CPU cpu) {
        byte new_lsb = 0;
        if(cpu.AF.isCarryFlagOn()) {
            new_lsb = 1;
        }

        if((cpu.AF.A.getValue() & 128) != 0) {
            cpu.turnOnFlags(Flags.CARRY);
        } else {
            cpu.turnOffFlags(Flags.CARRY);
        }
        cpu.AF.A.setValue((byte)((cpu.AF.A.getValue()<<1) | new_lsb));
        cpu.turnOffFlags((byte)(Flags.ZERO | Flags.SUBTRACTION | Flags.HALF_CARRY));
    }
}
