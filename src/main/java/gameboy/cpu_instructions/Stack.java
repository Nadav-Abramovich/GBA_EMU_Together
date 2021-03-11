package gameboy.cpu_instructions;

import gameboy.CPU;

// We suppress this warning because this class and its methods are dynamically called
// and therefore IntelliJ doesn't recognize their usage.
@SuppressWarnings("unused")
public class Stack implements CPUInstructions {
    @Opcode(value = 0x31, length = 3)
    public static void ld_sp_d16(CPU cpu) {
        int lowerByte = cpu.memory[cpu.PC.getValue() + 1] & 255;
        int upperByte = cpu.memory[cpu.PC.getValue() + 2] & 255;
        cpu.SP.setValue((char) (upperByte << 8 | lowerByte));
    }

    @Opcode(value = 0xC5, length = 1)
    public static void push_bc(CPU cpu) {
        cpu.memory[cpu.SP.getValue() - 1] = (byte)cpu.BC.B.getValue();
        cpu.memory[cpu.SP.getValue() - 2] = (byte)cpu.BC.C.getValue();
        cpu.SP.increment(-2);
    }

    @Opcode(value = 0xC1, length = 1)
    public static void pop_bc(CPU cpu) {
        cpu.BC.C.setValue(cpu.memory[cpu.SP.getValue()]);
        cpu.BC.B.setValue(cpu.memory[cpu.SP.getValue() + 1]);
        cpu.SP.increment(2);
    }

    @Opcode(value = 0xC9, length = 1, should_update_pc = false)
    public static void ret(CPU cpu) {
        cpu.PC.C.setValue(cpu.memory[cpu.SP.getValue()]);
        cpu.PC.P.setValue(cpu.memory[cpu.SP.getValue() + 1]);
        cpu.SP.increment(2);
    }

    @Opcode(value = 0xCD, length = 3, should_update_pc = false)
    public static void call_a16(CPU cpu) {
        char target_lower = (char)(cpu.memory[cpu.PC.getValue() + 1]&255);
        char target_higher = (char)(cpu.memory[cpu.PC.getValue() + 2]&255);
        cpu.PC.increment(3);
        cpu.memory[cpu.SP.getValue() - 1] = (byte)cpu.PC.P.getValue();
        cpu.memory[cpu.SP.getValue() - 2] = (byte)cpu.PC.C.getValue();
        cpu.SP.increment(-2);
        cpu.PC.setValue((char)((target_higher << 8) | target_lower));
    }
}
