package gameboy.cpu_instructions;

import gameboy.CPU;

import static gameboy.HelperFunctions.pop_from_stack_d16;
import static gameboy.HelperFunctions.push_to_stack_d16;

// We suppress this warning because this class and its methods are dynamically called
// and therefore IntelliJ doesn't recognize their usage.
@SuppressWarnings("unused")
public class Stack implements CPUInstructions {
    @Opcode(value = 0x31, length = 3, cycles = 3)
    public static void ld_sp_d16() {
        int lowerByte = CPU.memory.read_byte(CPU.PC.getValue() + 1) & 255;
        int upperByte = CPU.memory.read_byte(CPU.PC.getValue() + 2) & 255;
        CPU.SP.setValue((char) ((upperByte << 8) | lowerByte));
    }

    @Opcode(value = 0xC5, length = 1, cycles = 4)
    public static void push_bc() {
        push_to_stack_d16(CPU.BC.getValue());
    }

    @Opcode(value = 0xC0, length = 1, cycles = 2, should_update_pc = false)
    public static void ret_nz() {
        if (!CPU.AF.isZeroFlagOn()) {
            CPU.PC.setValue(pop_from_stack_d16());
            CPU.performed_cycles += 3;
        } else {
            CPU.PC.increment(1);
        }
    }

    @Opcode(value = 0xC1, length = 1, cycles = 3)
    public static void pop_bc() {
        CPU.BC.setValue(pop_from_stack_d16());
    }

    @Opcode(value = 0xC4, length = 3, cycles = 6, should_update_pc = false)
    public static void call_nz_a16() {
        if (!CPU.AF.isZeroFlagOn()) {
            char target_lower = (char) (CPU.memory.read_byte(CPU.PC.getValue() + 1) & 255);
            char target_higher = (char) (CPU.memory.read_byte(CPU.PC.getValue() + 2) & 255);

            push_to_stack_d16((char) (CPU.PC.getValue() + 3));
            CPU.PC.setValue((char) ((target_higher << 8) | target_lower));
            CPU.performed_cycles += 6;
        } else {
            CPU.PC.increment(3);
            CPU.performed_cycles += 6;
        }
    }

    @Opcode(value = 0xC7, length = 1, cycles = 4, should_update_pc = false)
    public static void rst_0() {
        char target_lower = 0x00;
        char target_higher = 0;

        push_to_stack_d16((char) (CPU.PC.getValue() + 1));
        CPU.PC.setValue(target_lower);
    }

    @Opcode(value = 0xC8, length = 1, cycles = 2, should_update_pc = false)
    public static void ret_z() {
        if (CPU.AF.isZeroFlagOn()) {
            CPU.PC.setValue(pop_from_stack_d16());
            CPU.performed_cycles += 3;
        } else {
            CPU.PC.increment(1);
        }
    }

    @Opcode(value = 0xC9, length = 1, cycles = 4, should_update_pc = false)
    public static void ret() {
        CPU.PC.setValue(pop_from_stack_d16());
    }

    @Opcode(value = 0xCC, length = 3, cycles = 6, should_update_pc = false)
    public static void call_n_a16() {
        if (CPU.AF.isZeroFlagOn()) {
            char target_lower = (char) (CPU.memory.read_byte(CPU.PC.getValue() + 1) & 255);
            char target_higher = (char) (CPU.memory.read_byte(CPU.PC.getValue() + 2) & 255);

            push_to_stack_d16((char) (CPU.PC.getValue() + 3));
            CPU.PC.setValue((char) ((target_higher << 8) | target_lower));
            CPU.performed_cycles += 6;
        } else {
            CPU.PC.increment(3);
            CPU.performed_cycles += 6;
        }
    }

    @Opcode(value = 0xCD, length = 3, cycles = 6, should_update_pc = false)
    public static void call_a16() {
        char target_lower = (char) (CPU.memory.read_byte(CPU.PC.getValue() + 1) & 255);
        char target_higher = (char) (CPU.memory.read_byte(CPU.PC.getValue() + 2) & 255);

        push_to_stack_d16((char) (CPU.PC.getValue() + 3));
        CPU.PC.setValue((char) ((target_higher << 8) | target_lower));
    }

    @Opcode(value = 0xCF, length = 1, cycles = 4, should_update_pc = false)
    public static void rst_1() {
        char target_lower = 0x08;
        char target_higher = 0;

        push_to_stack_d16((char) (CPU.PC.getValue() + 1));
        CPU.PC.setValue(target_lower);
    }

    @Opcode(value = 0xD0, length = 1, cycles = 2, should_update_pc = false)
    public static void ret_nc() {
        if (!CPU.AF.isCarryFlagOn()) {
            CPU.PC.setValue(pop_from_stack_d16());
            CPU.performed_cycles += 3;
        } else {
            CPU.PC.increment(1);
        }
    }

    @Opcode(value = 0xD8, length = 1, cycles = 2, should_update_pc = false)
    public static void ret_c() {
        if (CPU.AF.isCarryFlagOn()) {
            CPU.PC.setValue(pop_from_stack_d16());
            CPU.performed_cycles += 3;
        } else {
            CPU.PC.increment(1);
        }
    }

    @Opcode(value = 0xE7, length = 1, cycles = 4, should_update_pc = false)
    public static void rst_4() {
        char target_lower = 0x20;
        char target_higher = 0;

        push_to_stack_d16((char) (CPU.PC.getValue() + 1));
        CPU.PC.setValue(target_lower);
    }

    @Opcode(value = 0xEF, length = 1, cycles = 4, should_update_pc = false)
    public static void rst_5() {
        char target_lower = 0x28;
        char target_higher = 0;

        push_to_stack_d16((char) (CPU.PC.getValue() + 1));
        CPU.PC.setValue(target_lower);
    }

    @Opcode(value = 0xD1, length = 1, cycles = 3)
    public static void pop_de() {
        CPU.DE.setValue(pop_from_stack_d16());
    }

    @Opcode(value = 0xD5, length = 1, cycles = 4)
    public static void push_de() {
        push_to_stack_d16(CPU.DE.getValue());
    }

    @Opcode(value = 0xE1, length = 1, cycles = 3)
    public static void pop_hl() {
        CPU.HL.setValue(pop_from_stack_d16());
    }

    @Opcode(value = 0xE5, length = 1, cycles = 4)
    public static void push_hl() {
        push_to_stack_d16(CPU.HL.getValue());
    }

    @Opcode(value = 0xF1, length = 1, cycles = 3)
    public static void pop_af() {
        CPU.AF.setValue(pop_from_stack_d16());
    }

    @Opcode(value = 0xF5, length = 1, cycles = 4)
    public static void push_af() {
        push_to_stack_d16(CPU.AF.getValue());
    }

    @Opcode(value = 0xF9, length = 1, cycles = 2)
    public static void ld_sp_hl() {
        CPU.SP.setValue(CPU.HL.getValue());
    }

    @Opcode(value = 0xFF, length = 1, cycles = 4, should_update_pc = false)
    public static void rst_7() {
        char target_lower = 0x38;
        char target_higher = 0;

        push_to_stack_d16((char) (CPU.PC.getValue() + 1));
        CPU.PC.setValue(target_lower);
    }
}
