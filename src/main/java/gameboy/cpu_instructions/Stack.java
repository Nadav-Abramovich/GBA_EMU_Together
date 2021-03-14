package gameboy.cpu_instructions;

import gameboy.CPU;

import static gameboy.HelperFunctions.pop_from_stack_d16;
import static gameboy.HelperFunctions.push_to_stack_d16;

// We suppress this warning because this class and its methods are dynamically called
// and therefore IntelliJ doesn't recognize their usage.
@SuppressWarnings("unused")
public class Stack implements CPUInstructions {
    @Opcode(value = 0x31, length = 3, cycles = 3)
    public static void ld_sp_d16(CPU cpu) {
        int lowerByte = cpu.memory.read_byte(cpu.PC.getValue() + 1) & 255;
        int upperByte = cpu.memory.read_byte(cpu.PC.getValue() + 2) & 255;
        cpu.SP.setValue((char) ((upperByte << 8) | lowerByte));
    }

    @Opcode(value = 0xC5, length = 1, cycles = 4)
    public static void push_bc(CPU cpu) {
        push_to_stack_d16(cpu, cpu.BC.getValue());
    }

    @Opcode(value = 0xC0, length = 1, cycles = 2, should_update_pc = false)
    public static void ret_nz(CPU cpu) {
        if (!cpu.AF.isZeroFlagOn()) {
            cpu.PC.setValue(pop_from_stack_d16(cpu));
            cpu.performed_cycles += 3;
        } else {
            cpu.PC.increment(1);
        }
    }

    @Opcode(value = 0xC1, length = 1, cycles = 3)
    public static void pop_bc(CPU cpu) {
        cpu.BC.C.setValue(cpu.memory.read_byte(cpu.SP.getValue()));
        cpu.BC.B.setValue(cpu.memory.read_byte(cpu.SP.getValue() + 1));
        cpu.SP.increment(2);
    }

    @Opcode(value = 0xC4, length = 3, cycles = 6, should_update_pc = false)
    public static void call_nz_a16(CPU cpu) {
        if (!cpu.AF.isZeroFlagOn()) {
            char target_lower = (char) (cpu.memory.read_byte(cpu.PC.getValue() + 1) & 255);
            char target_higher = (char) (cpu.memory.read_byte(cpu.PC.getValue() + 2) & 255);

            push_to_stack_d16(cpu, (char) (cpu.PC.getValue() + 3));
            cpu.PC.setValue((char) ((target_higher << 8) | target_lower));
            cpu.performed_cycles += 6;
        } else {
            cpu.PC.increment(3);
            cpu.performed_cycles += 6;
        }
    }

    @Opcode(value = 0xC8, length = 1, cycles = 2, should_update_pc = false)
    public static void ret_z(CPU cpu) {
        if (cpu.AF.isZeroFlagOn()) {
            cpu.PC.setValue(pop_from_stack_d16(cpu));
            cpu.performed_cycles += 3;
        } else {
            cpu.PC.increment(1);
        }
    }


    @Opcode(value = 0xC9, length = 1, cycles = 4, should_update_pc = false)
    public static void ret(CPU cpu) {
        cpu.PC.setValue(pop_from_stack_d16(cpu));
    }

    @Opcode(value = 0xCD, length = 3, cycles = 6, should_update_pc = false)
    public static void call_a16(CPU cpu) {
        char target_lower = (char) (cpu.memory.read_byte(cpu.PC.getValue() + 1) & 255);
        char target_higher = (char) (cpu.memory.read_byte(cpu.PC.getValue() + 2) & 255);

        push_to_stack_d16(cpu, (char) (cpu.PC.getValue() + 3));
        cpu.PC.setValue((char) ((target_higher << 8) | target_lower));
    }

    @Opcode(value = 0xD0, length = 1, cycles = 2, should_update_pc = false)
    public static void ret_nc(CPU cpu) {
        if (!cpu.AF.isCarryFlagOn()) {
            cpu.PC.setValue(pop_from_stack_d16(cpu));
            cpu.performed_cycles += 3;
        } else {
            cpu.PC.increment(1);
        }
    }

    @Opcode(value = 0xEF, length = 1, cycles = 4, should_update_pc = false)
    public static void rst_5(CPU cpu) {
        char target_lower = 0x28;
        char target_higher = 0;

        push_to_stack_d16(cpu, (char) (cpu.PC.getValue() + 1));
        cpu.PC.setValue(target_lower);
    }

    @Opcode(value = 0xD1, length = 1, cycles = 3)
    public static void pop_de(CPU cpu) {
        cpu.DE.E.setValue(cpu.memory.read_byte(cpu.SP.getValue()));
        cpu.DE.D.setValue(cpu.memory.read_byte(cpu.SP.getValue() + 1));
        cpu.SP.increment(2);
    }

    @Opcode(value = 0xD5, length = 1, cycles = 4)
    public static void push_de(CPU cpu) {
        push_to_stack_d16(cpu, cpu.DE.getValue());
    }

    @Opcode(value = 0xE1, length = 1, cycles = 3)
    public static void pop_hl(CPU cpu) {
        cpu.HL.L.setValue(cpu.memory.read_byte(cpu.SP.getValue()));
        cpu.HL.H.setValue(cpu.memory.read_byte(cpu.SP.getValue() + 1));
        cpu.SP.increment(2);
    }

    @Opcode(value = 0xE5, length = 1, cycles = 4)
    public static void push_hl(CPU cpu) {
        push_to_stack_d16(cpu, cpu.HL.getValue());
    }

    @Opcode(value = 0xF1, length = 1, cycles = 3)
    public static void pop_AF(CPU cpu) {
        cpu.AF.F.setValue(cpu.memory.read_byte(cpu.SP.getValue()));
        cpu.AF.A.setValue(cpu.memory.read_byte(cpu.SP.getValue() + 1));
        cpu.SP.increment(2);
    }

    @Opcode(value = 0xF5, length = 1, cycles = 4)
    public static void push_af(CPU cpu) {
        push_to_stack_d16(cpu, cpu.AF.getValue());
    }
}
