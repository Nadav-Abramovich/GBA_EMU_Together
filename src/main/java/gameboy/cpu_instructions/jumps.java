package gameboy.cpu_instructions;

import gameboy.CPU;

// We suppress this warning because this class and its methods are dynamically imported
// and therefore IntelliJ doesn't recognize their usage.
@SuppressWarnings("unused")
public class jumps implements CPUInstructions {
    @Opcode(opcode = 0x20, length = 0, should_update_pc = false)
    public static void jr_nz(CPU cpu) {
        boolean is_z_on = (cpu.AF & (1 << 7)) != 0;
        byte jump_amount = cpu.memory[cpu.PC + 1];
        if (!is_z_on) {
            cpu.PC += jump_amount & 255;
        } else {
            cpu.PC += 2;
        }
    }
}
