package gameboy.cpu_instructions;

import gameboy.CPU;
import gameboy.Flags;

// We suppress this warning because this class and its methods are dynamically imported
// and therefore IntelliJ doesn't recognize their usage.
@SuppressWarnings("unused")
public class Ors implements CPUInstructions {
    @Opcode(value = 0xB0, length = 1, cycles = 1)
    public static void or_b() {
        CPU.AF.A.setValue((byte) (CPU.BC.B.getValue() | CPU.AF.A.getValue()));
        if (CPU.AF.A.getValue() == 0) {
            CPU.setFlags(Flags.ZERO);
        } else {
            CPU.setFlags((byte) 0);
        }
    }

    @Opcode(value = 0xB1, length = 1, cycles = 1)
    public static void or_c() {
        CPU.AF.A.setValue((byte) (((255 & CPU.BC.C.getValue()) | (255 & CPU.AF.A.getValue()))));
        if (CPU.AF.A.getValue() == 0) {
            CPU.setFlags(Flags.ZERO);
        } else {
            CPU.setFlags((byte) 0);
        }
    }

    @Opcode(value = 0xB2, length = 1, cycles = 1)
    public static void or_d() {
        CPU.AF.A.setValue((byte) (CPU.DE.D.getValue() | CPU.AF.A.getValue()));
        if (CPU.AF.A.getValue() == 0) {
            CPU.setFlags(Flags.ZERO);
        } else {
            CPU.setFlags((byte) 0);
        }
    }

    @Opcode(value = 0xB3, length = 1, cycles = 1)
    public static void or_e() {
        CPU.AF.A.setValue((byte) (CPU.DE.E.getValue() | CPU.AF.A.getValue()));
        if (CPU.AF.A.getValue() == 0) {
            CPU.setFlags(Flags.ZERO);
        } else {
            CPU.setFlags((byte) 0);
        }
    }

    @Opcode(value = 0xB3, length = 1, cycles = 1)
    public static void or_h() {
        CPU.AF.A.setValue((byte) (CPU.HL.H.getValue() | CPU.AF.A.getValue()));
        if (CPU.AF.A.getValue() == 0) {
            CPU.setFlags(Flags.ZERO);
        } else {
            CPU.setFlags((byte) 0);
        }
    }

    @Opcode(value = 0xB5, length = 1, cycles = 1)
    public static void or_l() {
        CPU.AF.A.setValue((byte) (CPU.HL.L.getValue() | CPU.AF.A.getValue()));
        if (CPU.AF.A.getValue() == 0) {
            CPU.setFlags(Flags.ZERO);
        } else {
            CPU.setFlags((byte) 0);
        }
    }


    @Opcode(value = 0xB6, length = 1, cycles = 2)
    public static void or_from_hl() {
        CPU.AF.A.setValue((byte) (CPU.memory.read_byte(CPU.HL.getValue()) | CPU.AF.A.getValue()));
        if (CPU.AF.A.getValue() == 0) {
            CPU.setFlags(Flags.ZERO);
        } else {
            CPU.setFlags((byte) 0);
        }
    }

    @Opcode(value = 0xB7, length = 1, cycles = 1)
    public static void or_a() {
        CPU.AF.A.setValue((byte) (CPU.AF.A.getValue() | CPU.AF.A.getValue()));
        if (CPU.AF.A.getValue() == 0) {
            CPU.setFlags(Flags.ZERO);
        } else {
            CPU.setFlags((byte) 0);
        }
    }
}
