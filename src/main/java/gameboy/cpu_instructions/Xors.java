package gameboy.cpu_instructions;

import gameboy.CPU;
import gameboy.Flags;

// We suppress this warning because this class and its methods are dynamically called
// and therefore IntelliJ doesn't recognize their usage.
@SuppressWarnings("unused")
public class Xors implements CPUInstructions {
    @Opcode(value = 0xAF, length = 1, cycles = 1)
    public static void xorA(CPU cpu) {
        cpu.AF.A.setValue((byte) 0);
        cpu.setFlags(Flags.ZERO);
    }

    @Opcode(value = 0xA8, length = 1, cycles = 1)
    public static void xorB(CPU cpu) {
        cpu.BC.B.setValue((byte) 0);

        // Make the ZERO flag the only turned on flag
        cpu.setFlags(Flags.ZERO);
    }

    @Opcode(value = 0xA9, length = 1, cycles = 1)
    public static void xorC(CPU cpu) {
        cpu.BC.C.setValue((byte) 0);

        // Make the ZERO flag the only turned on flag
        cpu.setFlags(Flags.ZERO);
    }

    @Opcode(value = 0xAA, length = 1, cycles = 1)
    public static void xorD(CPU cpu) {
        cpu.DE.D.setValue((byte) 0);

        // Make the ZERO flag the only turned on flag
        cpu.setFlags(Flags.ZERO);
    }

    @Opcode(value = 0xAB, length = 1, cycles = 1)
    public static void xorE(CPU cpu) {
        cpu.DE.E.setValue((byte) 0);

        // Make the ZERO flag the only turned on flag
        cpu.setFlags(Flags.ZERO);
    }

    @Opcode(value = 0xAC, length = 1, cycles = 1)
    public static void xorH(CPU cpu) {
        cpu.HL.H.setValue((byte) 0);

        // Make the ZERO flag the only turned on flag
        cpu.setFlags(Flags.ZERO);
    }

    @Opcode(value = 0xAD, length = 1, cycles = 1)
    public static void xorL(CPU cpu) {
        cpu.HL.L.setValue((byte) 0);

        // Make the ZERO flag the only turned on flag
        cpu.setFlags(Flags.ZERO);
    }
}
