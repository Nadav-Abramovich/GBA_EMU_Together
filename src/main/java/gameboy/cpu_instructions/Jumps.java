package gameboy.cpu_instructions;

import gameboy.CPU;

// We suppress this warning because this class and its methods are dynamically imported
// and therefore IntelliJ doesn't recognize their usage.
@SuppressWarnings("unused")
public class Jumps implements CPUInstructions {
    @Opcode(value = 0x18, length = 2, cycles = 3)
    public static void jr_s8(CPU cpu) {
        byte jump_amount = cpu.memory.read_byte(cpu.PC.getValue() + 1);
        cpu.PC.increment(jump_amount);
    }

    @Opcode(value = 0x20, length = 2, cycles = 2)
    public static void jr_nz_s8(CPU cpu) {
        byte jump_amount = cpu.memory.read_byte(cpu.PC.getValue() + 1);
        if (!cpu.AF.isZeroFlagOn()) {
            cpu.PC.increment(jump_amount);
            cpu.performed_cycles += 1;
        }
    }

    @Opcode(value = 0x28, length = 2, cycles = 2)
    public static void jr_z_s8(CPU cpu) {
        byte jump_amount = cpu.memory.read_byte(cpu.PC.getValue() + 1);
        if (cpu.AF.isZeroFlagOn()) {
            cpu.PC.increment(jump_amount);
            cpu.performed_cycles += 1;
        }
    }

    @Opcode(value = 0x38, length = 2, cycles = 2)
    public static void jr_c_s8(CPU cpu) {
        byte jump_amount = cpu.memory.read_byte(cpu.PC.getValue() + 1);
        if (cpu.AF.isCarryFlagOn()) {
            cpu.PC.increment(jump_amount);
            cpu.performed_cycles += 1;
        }
    }


    @Opcode(value = 0xC2, length = 3, cycles = 3, should_update_pc = false)
    public static void jp_nz_a16(CPU cpu) {
        if(!cpu.AF.isZeroFlagOn()) {
            char lower = (char) (cpu.memory.read_byte(cpu.PC.getValue() + 1) & 255);
            char higher = (char) (cpu.memory.read_byte(cpu.PC.getValue() + 2) & 255);
            cpu.PC.setValue((char) (lower | (higher << 8)));
            cpu.performed_cycles += 1;
        } else {
            cpu.PC.increment(3);
        }
    }

    @Opcode(value = 0xC3, length = 3, cycles = 4, should_update_pc = false)
    public static void jp_a16(CPU cpu) {
        char lower = (char)(cpu.memory.read_byte(cpu.PC.getValue() + 1) & 255);
        char higher = (char)(cpu.memory.read_byte(cpu.PC.getValue() + 2) & 255);
        cpu.PC.setValue((char)(lower | (higher<<8)));
    }

    @Opcode(value = 0xCA, length = 3, cycles = 3, should_update_pc = false)
    public static void jp_z_a16(CPU cpu) {
        if(cpu.AF.isZeroFlagOn()) {
            char lower = (char) (cpu.memory.read_byte(cpu.PC.getValue() + 1) & 255);
            char higher = (char) (cpu.memory.read_byte(cpu.PC.getValue() + 2) & 255);
            cpu.PC.setValue((char) (lower | (higher << 8)));
            cpu.performed_cycles += 1;
        } else {
            cpu.PC.increment(3);
        }
    }

    @Opcode(value = 0xD2, length = 3, cycles = 3, should_update_pc = false)
    public static void jp_nc_a16(CPU cpu) {
        if(!cpu.AF.isCarryFlagOn()) {
            char lower = (char) (cpu.memory.read_byte(cpu.PC.getValue() + 1) & 255);
            char higher = (char) (cpu.memory.read_byte(cpu.PC.getValue() + 2) & 255);
            cpu.PC.setValue((char) (lower | (higher << 8)));
            cpu.performed_cycles += 1;
        } else {
            cpu.PC.increment(3);
        }
    }

    @Opcode(value = 0xE9, length = 1, cycles = 1, should_update_pc = false)
    public static void jp_hl(CPU cpu) {
        cpu.PC.setValue(cpu.HL.getValue());
    }
}
