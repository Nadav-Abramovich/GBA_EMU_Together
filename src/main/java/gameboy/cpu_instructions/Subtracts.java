package gameboy.cpu_instructions;

import gameboy.CPU;
import gameboy.Flags;

// We suppress this warning because this class and its methods are dynamically imported
// and therefore IntelliJ doesn't recognize their usage.
@SuppressWarnings("unused")
public class Subtracts implements CPUInstructions {
    @Opcode(value = 0x90, length = 1, cycles = 1)
    public static void sub_b(CPU cpu) {
        char new_value = (char)(cpu.AF.A.getValue()&255);
        // TODO: Are these 2 flags correct?
        if (new_value < (cpu.BC.B.getValue()&255)) {
            cpu.turnOnFlags((byte)(Flags.CARRY | Flags.HALF_CARRY));
        } else {
            cpu.turnOffFlags((byte)(Flags.CARRY | Flags.HALF_CARRY));
        }
        new_value -= cpu.BC.B.getValue()&255;
        cpu.AF.A.setValue((byte)new_value);
        if(new_value == 0) {
            cpu.turnOnFlags(Flags.ZERO);
        } else {
            cpu.turnOffFlags(Flags.ZERO);
        }

        cpu.turnOnFlags(Flags.SUBTRACTION);
    }

    @Opcode(value = 0x94, length = 1, cycles = 1)
    public static void sub_h(CPU cpu) {
        char new_value = (char)(cpu.AF.A.getValue()&255);
        // TODO: Are these 2 flags correct?
        if (new_value < (cpu.HL.H.getValue()&255)) {
            cpu.turnOnFlags((byte)(Flags.CARRY | Flags.HALF_CARRY));
        } else {
            cpu.turnOffFlags((byte)(Flags.CARRY | Flags.HALF_CARRY));
        }
        new_value -= cpu.HL.H.getValue()&255;
        cpu.AF.A.setValue((byte)new_value);
        if(new_value == 0) {
            cpu.turnOnFlags(Flags.ZERO);
        } else {
            cpu.turnOffFlags(Flags.ZERO);
        }

        cpu.turnOnFlags(Flags.SUBTRACTION);
    }
}
