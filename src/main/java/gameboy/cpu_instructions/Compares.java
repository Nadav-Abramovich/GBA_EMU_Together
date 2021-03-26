package gameboy.cpu_instructions;

import gameboy.CPU;
import gameboy.Flags;

// We suppress this warning because this class and its methods are dynamically imported
// and therefore IntelliJ doesn't recognize their usage.
@SuppressWarnings("unused")
public class Compares implements CPUInstructions {
    @Opcode(value = 0xB8, length = 1, cycles = 1)
    public static void cp_b() {
        char compare_value = CPU.BC.B.getValue();
        if (CPU.AF.A.getValue() < compare_value) {
            CPU.turnOnFlags((byte) (Flags.CARRY | Flags.HALF_CARRY));
        } else {
            CPU.turnOffFlags((byte) (Flags.CARRY | Flags.HALF_CARRY));
        }
        if (CPU.AF.A.getValue() == compare_value) {
            CPU.turnOnFlags(Flags.ZERO);
        } else {
            CPU.turnOffFlags(Flags.ZERO);
        }

        CPU.turnOnFlags(Flags.SUBTRACTION);
    }

    @Opcode(value = 0xB9, length = 1, cycles = 1)
    public static void cp_c() {
        char compare_value = CPU.BC.C.getValue();
        if (CPU.AF.A.getValue() < compare_value) {
            CPU.turnOnFlags((byte) (Flags.CARRY | Flags.HALF_CARRY));
        } else {
            CPU.turnOffFlags((byte) (Flags.CARRY | Flags.HALF_CARRY));
        }
        if (CPU.AF.A.getValue() == compare_value) {
            CPU.turnOnFlags(Flags.ZERO);
        } else {
            CPU.turnOffFlags(Flags.ZERO);
        }

        CPU.turnOnFlags(Flags.SUBTRACTION);
    }

    @Opcode(value = 0xBB, length = 1, cycles = 1)
    public static void cp_e() {
        char compare_value = CPU.DE.E.getValue();
        if (CPU.AF.A.getValue() < compare_value) {
            CPU.turnOnFlags((byte) (Flags.CARRY | Flags.HALF_CARRY));
        } else {
            CPU.turnOffFlags((byte) (Flags.CARRY | Flags.HALF_CARRY));
        }
        if (CPU.AF.A.getValue() == compare_value) {
            CPU.turnOnFlags(Flags.ZERO);
        } else {
            CPU.turnOffFlags(Flags.ZERO);
        }

        CPU.turnOnFlags(Flags.SUBTRACTION);
    }

    @Opcode(value = 0xBE, length = 1, cycles = 2)
    public static void cp_from_hl() {
        char compare_value = (char) (CPU.memory.read_byte(CPU.HL.getValue()) & 255);
        if (CPU.AF.A.getValue() < compare_value) {
            CPU.turnOnFlags((byte) (Flags.CARRY | Flags.HALF_CARRY));
        } else {
            CPU.turnOffFlags((byte) (Flags.CARRY | Flags.HALF_CARRY));
        }
        if (CPU.AF.A.getValue() == compare_value) {
            CPU.turnOnFlags(Flags.ZERO);
        } else {
            CPU.turnOffFlags(Flags.ZERO);
        }

        CPU.turnOnFlags(Flags.SUBTRACTION);
    }

    @Opcode(value = 0xFE, length = 2, cycles = 2)
    public static void cp_d8() {
        char compare_value = (char) (CPU.memory.read_byte(CPU.PC.getValue() + 1) & 255);
        if (CPU.AF.A.getValue() < compare_value) {
            CPU.turnOnFlags((byte) (Flags.CARRY | Flags.HALF_CARRY));
        } else {
            CPU.turnOffFlags((byte) (Flags.CARRY | Flags.HALF_CARRY));
        }
        if (CPU.AF.A.getValue() == compare_value) {
            CPU.turnOnFlags(Flags.ZERO);
        } else {
            CPU.turnOffFlags(Flags.ZERO);
        }

        CPU.turnOnFlags(Flags.SUBTRACTION);
    }
}
