package gameboy.cpu_instructions;

import gameboy.CPU;

// We suppress this warning because this class and its methods are dynamically called
// and therefore IntelliJ doesn't recognize their usage.
@SuppressWarnings("unused")
public class Xors implements CPUInstructions {
    @Opcode(opcode = 0xAF)
    public static void xorA(CPU cpu) {
        cpu.AF = (char) 0;
        cpu.PC++;
        cpu.AF |= 128;
    }

    @Opcode(opcode = 0xA8)
    public static void xorB(CPU cpu) {
        cpu.BC = (char) (255 & cpu.BC);
        cpu.PC++;

        // Make the ZERO flag the only turned on flag
        cpu.xor_flags();
        cpu.turn_on_zero_flag();
    }

    @Opcode(opcode = 0xA9)
    public static void xorC(CPU cpu) {
        cpu.BC = (char) ((cpu.BC >> 8) << 8);
        cpu.PC++;

        // Make the ZERO flag the only turned on flag
        cpu.xor_flags();
        cpu.turn_on_zero_flag();
    }

    @Opcode(opcode = 0xAA)
    public static void xorD(CPU cpu) {
        cpu.DE = (char) (255 & cpu.DE);
        cpu.PC++;

        // Make the ZERO flag the only turned on flag
        cpu.xor_flags();
        cpu.turn_on_zero_flag();
    }

    @Opcode(opcode = 0xAB)
    public static void xorE(CPU cpu) {
        cpu.DE = (char) ((cpu.BC >> 8) << 8);
        cpu.PC++;

        // Make the ZERO flag the only turned on flag
        cpu.xor_flags();
        cpu.turn_on_zero_flag();
    }

    @Opcode(opcode = 0xAC)
    public static void xorH(CPU cpu) {
        cpu.HL = (char) (255 & cpu.HL);
        cpu.PC++;

        // Make the ZERO flag the only turned on flag
        cpu.xor_flags();
        cpu.turn_on_zero_flag();
    }

    @Opcode(opcode = 0xAD)
    public static void xorL(CPU cpu) {
        cpu.HL = (char) ((cpu.BC >> 8) << 8);
        cpu.PC++;

        // Make the ZERO flag the only turned on flag
        cpu.xor_flags();
        cpu.turn_on_zero_flag();
    }
}