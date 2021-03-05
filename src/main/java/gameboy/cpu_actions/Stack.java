package gameboy.cpu_actions;

import gameboy.CPU;

// We suppress this warning because this class and its methods are dynamically called
// and therefore IntelliJ doesn't recognize their usage.
@SuppressWarnings("unused")
public class Stack implements CPUInstructions {
    @Opcode(opcode = 0x31)
    public static void ld_sp_d16(CPU cpu) {
        int lowerByte = cpu.memory[cpu.PC + 1] & 255;
        int upperByte = cpu.memory[cpu.PC + 2] & 255;
        cpu.SP = (char) (upperByte << 8 | lowerByte);
        cpu.PC += 3;
    }
}
