package gameboy.cpu_instructions;

import gameboy.CPU;
import gameboy.Flags;

// We suppress this warning because this class and its methods are dynamically imported
// and therefore IntelliJ doesn't recognize their usage.
@SuppressWarnings("unused")
public class Ors implements CPUInstructions {
    @Opcode(value = 0xB0, length = 1, cycles = 1)
    public static void or_b(CPU cpu) {
        cpu.AF.A.setValue((byte) (cpu.BC.B.getValue() | cpu.AF.A.getValue()));
        if (cpu.AF.A.getValue() == 0) {
            cpu.setFlags(Flags.ZERO);
        } else {
            cpu.setFlags((byte) 0);
        }
    }

    @Opcode(value = 0xB1, length = 1, cycles = 1)
    public static void or_c(CPU cpu) {
        cpu.AF.A.setValue((byte) (((255 & cpu.BC.C.getValue()) | (255 & cpu.AF.A.getValue()))));
        if (cpu.AF.A.getValue() == 0) {
            cpu.setFlags(Flags.ZERO);
        } else {
            cpu.setFlags((byte) 0);
        }
    }

    @Opcode(value = 0xB2, length = 1, cycles = 1)
    public static void or_d(CPU cpu) {
        cpu.AF.A.setValue((byte) (cpu.DE.D.getValue() | cpu.AF.A.getValue()));
        if (cpu.AF.A.getValue() == 0) {
            cpu.setFlags(Flags.ZERO);
        } else {
            cpu.setFlags((byte) 0);
        }
    }

    @Opcode(value = 0xB3, length = 1, cycles = 1)
    public static void or_e(CPU cpu) {
        cpu.AF.A.setValue((byte) (cpu.DE.E.getValue() | cpu.AF.A.getValue()));
        if (cpu.AF.A.getValue() == 0) {
            cpu.setFlags(Flags.ZERO);
        } else {
            cpu.setFlags((byte) 0);
        }
    }

    @Opcode(value = 0xB3, length = 1, cycles = 1)
    public static void or_h(CPU cpu) {
        cpu.AF.A.setValue((byte) (cpu.HL.H.getValue() | cpu.AF.A.getValue()));
        if (cpu.AF.A.getValue() == 0) {
            cpu.setFlags(Flags.ZERO);
        } else {
            cpu.setFlags((byte) 0);
        }
    }

    @Opcode(value = 0xB5, length = 1, cycles = 1)
    public static void or_l(CPU cpu) {
        cpu.AF.A.setValue((byte) (cpu.HL.L.getValue() | cpu.AF.A.getValue()));
        if (cpu.AF.A.getValue() == 0) {
            cpu.setFlags(Flags.ZERO);
        } else {
            cpu.setFlags((byte) 0);
        }
    }


    @Opcode(value = 0xB6, length = 1, cycles = 2)
    public static void or_from_hl(CPU cpu) {
        cpu.AF.A.setValue((byte) (cpu.memory.read_byte(cpu.HL.getValue()) | cpu.AF.A.getValue()));
        if (cpu.AF.A.getValue() == 0) {
            cpu.setFlags(Flags.ZERO);
        } else {
            cpu.setFlags((byte) 0);
        }
    }

    @Opcode(value = 0xB7, length = 1, cycles = 1)
    public static void or_a(CPU cpu) {
        cpu.AF.A.setValue((byte) (cpu.AF.A.getValue() | cpu.AF.A.getValue()));
        if (cpu.AF.A.getValue() == 0) {
            cpu.setFlags(Flags.ZERO);
        } else {
            cpu.setFlags((byte) 0);
        }
    }
}
