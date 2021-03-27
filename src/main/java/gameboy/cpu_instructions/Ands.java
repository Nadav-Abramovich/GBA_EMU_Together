package gameboy.cpu_instructions;

import gameboy.CPU;
import gameboy.Flags;

// We suppress this warning because this class and its methods are dynamically imported
// and therefore IntelliJ doesn't recognize their usage.
@SuppressWarnings("unused")
public class Ands implements CPUInstructions {
    @Opcode(value = 0xA7, length = 1, cycles = 1)
    public static void and_a() {
        if (CPU.AF.A.getValue() == 0) {
            CPU.setFlags((byte) (Flags.ZERO | Flags.HALF_CARRY));
        } else {
            CPU.setFlags(Flags.HALF_CARRY);
        }
    }

    @Opcode(value = 0xA0, length = 1, cycles = 1)
    public static void and_b() {
        CPU.AF.A.setValue((byte) (CPU.AF.A.getValue() & CPU.BC.B.getValue()));
        if (CPU.AF.A.getValue() == 0) {
            CPU.setFlags((byte) (Flags.ZERO | Flags.HALF_CARRY));
        } else {
            CPU.setFlags(Flags.HALF_CARRY);
        }
    }

    @Opcode(value = 0xA1, length = 1, cycles = 1)
    public static void and_c() {
        CPU.AF.A.setValue((byte) (CPU.AF.A.getValue() & CPU.BC.C.getValue()));
        if (CPU.AF.A.getValue() == 0) {
            CPU.setFlags((byte) (Flags.ZERO | Flags.HALF_CARRY));
        } else {
            CPU.setFlags(Flags.HALF_CARRY);
        }
    }


    @Opcode(value = 0xA2, length = 1, cycles = 1)
    public static void and_d() {
        CPU.AF.A.setValue((byte) (CPU.AF.A.getValue() & CPU.DE.D.getValue()));
        if (CPU.AF.A.getValue() == 0) {
            CPU.setFlags((byte) (Flags.ZERO | Flags.HALF_CARRY));
        } else {
            CPU.setFlags(Flags.HALF_CARRY);
        }
    }

    @Opcode(value = 0xA4, length = 1, cycles = 1)
    public static void and_h() {
        CPU.AF.A.setValue((byte) (CPU.AF.A.getValue() & CPU.HL.H.getValue()));
        if (CPU.AF.A.getValue() == 0) {
            CPU.setFlags((byte) (Flags.ZERO | Flags.HALF_CARRY));
        } else {
            CPU.setFlags(Flags.HALF_CARRY);
        }
    }


    @Opcode(value = 0xE6, length = 2, cycles = 2)
    public static void and_d8() {
        byte d8 = CPU.memory.read_byte(CPU.PC.getValue() + 1);
        CPU.AF.A.setValue((byte) (CPU.AF.A.getValue() & d8));
        CPU.setFlags(Flags.HALF_CARRY);
        if(CPU.AF.A.getValue() == 0) {
            CPU.turnOnFlags(Flags.ZERO);
        }
    }
}
