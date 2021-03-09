package gameboy.cpu_instructions;

import gameboy.CPU;

// We suppress this warning because this class and its methods are dynamically imported
// and therefore IntelliJ doesn't recognize their usage.
@SuppressWarnings("unused")
public class Load implements CPUInstructions {
    @Opcode(value = 0x0E, length = 2)
    public static void ld_c_d8(CPU cpu) {
        byte B = (byte) (cpu.BC >> 8);
        byte C = cpu.memory[cpu.PC + 1];
        B = (byte) (B << 8);
        cpu.BC = (char) (B | C);
    }

    @Opcode(value = 0x1E, length = 2)
    public static void ld_e_d8(CPU cpu) {
        byte D = (byte) (cpu.DE >> 8);
        byte E = cpu.memory[cpu.PC + 1];
        D = (byte) (D << 8);
        cpu.BC = (char) (D | E);
    }

    @Opcode(value = 0x2E, length = 2)
    public static void ld_l_d8(CPU cpu) {
        byte H = (byte) (cpu.HL >> 8);
        byte L = cpu.memory[cpu.PC + 1];
        H = (byte) (H << 8);
        cpu.HL = (char) (L | H);
    }

    @Opcode(value = 0x3E, length = 2)
    public static void ld_a_d8(CPU cpu) {
        byte F = (byte) (cpu.AF & 255);
        byte A = cpu.memory[cpu.PC + 1];
        A = (byte) (A << 8);
        cpu.AF = (char) (A | F);
    }

    @Opcode(value = 0xE2, length = 1)
    public static void ld_pointer_c_a(CPU cpu) {
        byte A = (byte)(cpu.AF>>8);
        cpu.memory[0xFF00|(cpu.BC>>8)] = A;
    }
}
