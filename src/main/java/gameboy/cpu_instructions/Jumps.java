package gameboy.cpu_instructions;

import gameboy.CPU;

// We suppress this warning because this class and its methods are dynamically imported
// and therefore IntelliJ doesn't recognize their usage.
@SuppressWarnings("unused")
public class Jumps implements CPUInstructions {
    @Opcode(value = 0x18, length = 2, cycles = 3)
    public static void jr_s8() {
        byte jump_amount = CPU.memory.read_byte(CPU.PC.getValue() + 1);
        CPU.PC.increment(jump_amount);
    }

    @Opcode(value = 0x20, length = 2, cycles = 2)
    public static void jr_nz_s8() {
        byte jump_amount = CPU.memory.read_byte(CPU.PC.getValue() + 1);
        if (!CPU.AF.isZeroFlagOn()) {
            CPU.PC.increment(jump_amount);
            CPU.performed_cycles += 1;
        }
    }

    @Opcode(value = 0x28, length = 2, cycles = 2)
    public static void jr_z_s8() {
        byte jump_amount = CPU.memory.read_byte(CPU.PC.getValue() + 1);
        if (CPU.AF.isZeroFlagOn()) {
            CPU.PC.increment(jump_amount);
            CPU.performed_cycles += 1;
        }
    }

    @Opcode(value = 0x30, length = 2, cycles = 2)
    public static void jr_nc_s8() {
        byte jump_amount = CPU.memory.read_byte(CPU.PC.getValue() + 1);
        if (!CPU.AF.isCarryFlagOn()) {
            CPU.PC.increment(jump_amount);
            CPU.performed_cycles += 1;
        }
    }

    @Opcode(value = 0x38, length = 2, cycles = 2)
    public static void jr_c_s8() {
        byte jump_amount = CPU.memory.read_byte(CPU.PC.getValue() + 1);
        if (CPU.AF.isCarryFlagOn()) {
            CPU.PC.increment(jump_amount);
            CPU.performed_cycles += 1;
        }
    }


    @Opcode(value = 0xC2, length = 3, cycles = 3, should_update_pc = false)
    public static void jp_nz_a16() {
        if (!CPU.AF.isZeroFlagOn()) {
            char lower = (char) (CPU.memory.read_byte(CPU.PC.getValue() + 1) & 255);
            char higher = (char) (CPU.memory.read_byte(CPU.PC.getValue() + 2) & 255);
            CPU.PC.setValue((char) (lower | (higher << 8)));
            CPU.performed_cycles += 1;
        } else {
            CPU.PC.increment(3);
        }
    }

    @Opcode(value = 0xC3, length = 3, cycles = 4, should_update_pc = false)
    public static void jp_a16() {
        char lower = (char) (CPU.memory.read_byte(CPU.PC.getValue() + 1) & 255);
        char higher = (char) (CPU.memory.read_byte(CPU.PC.getValue() + 2) & 255);
        CPU.PC.setValue((char) (lower | (higher << 8)));
    }

    @Opcode(value = 0xCA, length = 3, cycles = 3, should_update_pc = false)
    public static void jp_z_a16() {
        if (CPU.AF.isZeroFlagOn()) {
            char lower = (char) (CPU.memory.read_byte(CPU.PC.getValue() + 1) & 255);
            char higher = (char) (CPU.memory.read_byte(CPU.PC.getValue() + 2) & 255);
            CPU.PC.setValue((char) (lower | (higher << 8)));
            CPU.performed_cycles += 1;
        } else {
            CPU.PC.increment(3);
        }
    }

    @Opcode(value = 0xD2, length = 3, cycles = 3, should_update_pc = false)
    public static void jp_nc_a16() {
        if (!CPU.AF.isCarryFlagOn()) {
            char lower = (char) (CPU.memory.read_byte(CPU.PC.getValue() + 1) & 255);
            char higher = (char) (CPU.memory.read_byte(CPU.PC.getValue() + 2) & 255);
            CPU.PC.setValue((char) (lower | (higher << 8)));
            CPU.performed_cycles += 1;
        } else {
            CPU.PC.increment(3);
        }
    }

    @Opcode(value = 0xDA, length = 3, cycles = 3, should_update_pc = false)
    public static void jp_c_a16() {
        if(CPU.AF.isCarryFlagOn()) {
            char lower = (char) (CPU.memory.read_byte(CPU.PC.getValue() + 1) & 255);
            char higher = (char) (CPU.memory.read_byte(CPU.PC.getValue() + 2) & 255);
            CPU.PC.setValue((char) (lower | (higher << 8)));
            CPU.performed_cycles += 1;
        } else {
            CPU.PC.increment(3);
        }
    }

    @Opcode(value = 0xE9, length = 1, cycles = 1, should_update_pc = false)
    public static void jp_hl() {
        CPU.PC.setValue(CPU.HL.getValue());
    }
}
