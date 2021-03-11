package gameboy.cpu_instructions;

import gameboy.CPU;

// We suppress this warning because this class and its methods are dynamically imported
// and therefore IntelliJ doesn't recognize their usage.
@SuppressWarnings("unused")
public class Jumps implements CPUInstructions {
    @Opcode(value = 0x18, length = 2)
    public static void jr_s8(CPU cpu) {
        byte jump_amount = cpu.memory[cpu.PC.getValue() + 1];
        cpu.PC.increment(jump_amount);
    }

    @Opcode(value = 0x20, length = 2)
    public static void jr_nz(CPU cpu) {
        byte jump_amount = cpu.memory[cpu.PC.getValue() + 1];
        if (!cpu.AF.isZeroFlagOn()) {
            cpu.PC.increment(jump_amount);
        }
    }

    @Opcode(value = 0x28, length = 2)
    public static void jr_z(CPU cpu) {
        byte jump_amount = cpu.memory[cpu.PC.getValue() + 1];
        if (cpu.AF.isZeroFlagOn()) {
            cpu.PC.increment(jump_amount);
        }
    }

    @Opcode(value = 0xC3, length = 3, should_update_pc = false)
    public static void jp_a16(CPU cpu) {
        byte lower = cpu.memory[cpu.PC.getValue() + 1];
        byte higher = cpu.memory[cpu.PC.getValue() + 2];
        cpu.PC.setValue((char)(lower | (higher<<8)));
    }
}
