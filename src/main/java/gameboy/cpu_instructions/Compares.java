package gameboy.cpu_instructions;

import gameboy.CPU;
import gameboy.Flags;

// We suppress this warning because this class and its methods are dynamically imported
// and therefore IntelliJ doesn't recognize their usage.
@SuppressWarnings("unused")
public class Compares implements CPUInstructions {
    @Opcode(value = 0xB8, length = 1, cycles = 1)
    public static void cp_b(CPU cpu) {
        char compare_value = cpu.BC.B.getValue();
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

    @Opcode(value = 0xBE, length = 1, cycles = 2)
    public static void cp_from_hl(CPU cpu) {
        char compare_value = (char)(cpu.memory.read_byte(cpu.HL.getValue()) & 255);
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

    @Opcode(value = 0xFE, length = 2, cycles = 2)
    public static void cp_d8(CPU cpu) {
        char compare_value = (char)(cpu.memory.read_byte(cpu.PC.getValue() + 1) & 255);
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
