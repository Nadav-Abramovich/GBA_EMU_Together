package gameboy.cpu_instructions;

import gameboy.CPU;

// We suppress this warning because this class and its methods are dynamically called
// and therefore IntelliJ doesn't recognize their usage.
@SuppressWarnings("unused")
public class Xors implements CPUInstructions {
    @Opcode(value = 0xAF, length = 1)
    public static void xorA(CPU cpu) {
        cpu.AF = (char) 0;
        cpu.AF |= 128;
    }

    @Opcode(value = 0xA8, length = 1)
    public static void xorB(CPU cpu) {
        cpu.BC = (char) (255 & cpu.BC);

        // Make the ZERO flag the only turned on flag
        cpu.xor_flags();
        cpu.turn_on_zero_flag();
    }

    @Opcode(value = 0xA9, length = 1)
    public static void xorC(CPU cpu) {
        cpu.BC = (char) ((cpu.BC >> 8) << 8);

        // Make the ZERO flag the only turned on flag
        cpu.xor_flags();
        cpu.turn_on_zero_flag();
    }

    @Opcode(value = 0xAA, length = 1)
    public static void xorD(CPU cpu) {
        cpu.DE = (char) (255 & cpu.DE);

        // Make the ZERO flag the only turned on flag
        cpu.xor_flags();
        cpu.turn_on_zero_flag();
    }

    @Opcode(value = 0xAB, length = 1)
    public static void xorE(CPU cpu) {
        cpu.DE = (char) ((cpu.BC >> 8) << 8);

        // Make the ZERO flag the only turned on flag
        cpu.xor_flags();
        cpu.turn_on_zero_flag();
    }

    @Opcode(value = 0xAC, length = 1)
    public static void xorH(CPU cpu) {
        cpu.HL = (char) (255 & cpu.HL);

        // Make the ZERO flag the only turned on flag
        cpu.xor_flags();
        cpu.turn_on_zero_flag();
    }

    @Opcode(value = 0xAD, length = 1)
    public static void xorL(CPU cpu) {
        cpu.HL = (char) ((cpu.BC >> 8) << 8);

        // Make the ZERO flag the only turned on flag
        cpu.xor_flags();
        cpu.turn_on_zero_flag();
    }


}
