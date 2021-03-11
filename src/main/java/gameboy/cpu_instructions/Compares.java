package gameboy.cpu_instructions;

import gameboy.CPU;
import gameboy.Flags;

// We suppress this warning because this class and its methods are dynamically imported
// and therefore IntelliJ doesn't recognize their usage.
@SuppressWarnings("unused")
public class Compares implements CPUInstructions {
    @Opcode(value = 0xBE, length = 1)
    public static void cp_from_hl(CPU cpu) {
        char compare_value = (char)(cpu.memory[cpu.HL.getValue()] & 255);
        // TODO: Maybe the flags arent correct? specifically the HALF_CARRY (And possibly carry)
        if(cpu.AF.A.getValue() < compare_value) {
            cpu.turnOnFlags((byte)(Flags.CARRY | Flags.HALF_CARRY));
        } else {
            cpu.turnOffFlags((byte)(Flags.CARRY | Flags.HALF_CARRY));
        }
        if(cpu.AF.A.getValue() == compare_value) {
            cpu.turnOnFlags(Flags.ZERO);
        } else {
            cpu.turnOffFlags(Flags.ZERO);
        }

        cpu.turnOnFlags(Flags.SUBTRACTION);
    }

    @Opcode(value = 0xFE, length = 2)
    public static void cp_d8(CPU cpu) {
        char compare_value = (char)(cpu.memory[cpu.PC.getValue() + 1] & 255);
        // TODO: Maybe the flags arent correct? specifically the HALF_CARRY (And possibly carry)
        if(cpu.AF.A.getValue() < compare_value) {
            cpu.turnOnFlags((byte)(Flags.CARRY | Flags.HALF_CARRY));
        } else {
            cpu.turnOffFlags((byte)(Flags.CARRY | Flags.HALF_CARRY));
        }
        if(cpu.AF.A.getValue() == compare_value) {
            cpu.turnOnFlags(Flags.ZERO);
        } else {
            cpu.turnOffFlags(Flags.ZERO);
        }

        cpu.turnOnFlags(Flags.SUBTRACTION);
    }
}
