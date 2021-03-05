package Gameboy.CPUActions;

import Gameboy.CPU;

// We suppress this warning because this class and its methods are dynamically called
// and therefore IntelliJ doesn't recognize their usage.
@SuppressWarnings("unused")
public class Memory implements CPUInstructions {
    @OpcodeBinding(opcode = 0x21)
    public static void ld_hl_d16(CPU cpu) {
        System.out.println("_ld_hl_d16");
        int lowerByte = cpu.memory[cpu.PC + 1] & 255;
        int upperByte = cpu.memory[cpu.PC + 2] & 255;
        cpu.HL = (char) (upperByte << 8 | lowerByte);
        cpu.PC += 3;
    }

    @OpcodeBinding(opcode = 0x22)
    public static void ld_hl_plus_a(CPU cpu) {
        System.out.println("_ld_hl_plus_a");

        byte A = (byte) (cpu.AF >> 8);

        cpu.memory[cpu.HL] = A;
        cpu.HL++;
        cpu.PC++;
    }

    @OpcodeBinding(opcode = 0x32)
    public static void ld_hl_minus_a(CPU cpu) {
        System.out.println("_ld_hl_minus_a");

        byte A = (byte) (cpu.AF >> 8);

        cpu.memory[cpu.HL] = A;
        cpu.HL--;
        cpu.PC++;
    }

}
