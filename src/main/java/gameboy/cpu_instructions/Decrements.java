package gameboy.cpu_instructions;

import gameboy.CPU;
import gameboy.Flags;

import static gameboy.HelperFunctions.add_register;

// We suppress this warning because this class and its methods are dynamically imported
// and therefore IntelliJ doesn't recognize their usage.
@SuppressWarnings("unused")
public class Decrements implements CPUInstructions {
    @Opcode(value = 0x05, length = 1, cycles = 1)
    public static void dec_b(CPU cpu) {
        add_register(cpu, cpu.BC.B, 1, false, true);
    }

    @Opcode(value = 0x0B, length = 1, cycles = 2)
    public static void dec_bc(CPU cpu) {
        add_register(cpu, cpu.BC, 1, false, true);
    }

    @Opcode(value = 0x0D, length = 1, cycles = 1)
    public static void dec_c(CPU cpu) {
        add_register(cpu, cpu.BC.C, 1, false, true);
    }

    @Opcode(value = 0x15, length = 1, cycles = 1)
    public static void dec_d(CPU cpu) {
        add_register(cpu, cpu.DE.D, 1, false, true);
    }

    @Opcode(value = 0x1B, length = 1, cycles = 2)
    public static void dec_de(CPU cpu) {
        add_register(cpu, cpu.DE, 1, false, true);
    }

    @Opcode(value = 0x1D, length = 1, cycles = 1)
    public static void dec_e(CPU cpu) {
        add_register(cpu, cpu.DE.E, 1, false, true);
    }

    @Opcode(value = 0x2D, length = 1, cycles = 1)
    public static void dec_l(CPU cpu) {
        add_register(cpu, cpu.HL.L, 1, false, true);
    }

    @Opcode(value = 0x25, length = 1, cycles = 1)
    public static void dec_h(CPU cpu) {
        add_register(cpu, cpu.HL.H, 1, false, true);
    }

    @Opcode(value = 0x2B, length = 1, cycles = 2)
    public static void dec_hl(CPU cpu) {
        add_register(cpu, cpu.HL, 1, false, true);
    }

    @Opcode(value = 0x35, length = 1, cycles = 3)
    public static void dec_from_hl(CPU cpu) {
        char current_value = (char) (cpu.memory.read_byte(cpu.HL.getValue()) & 255);
        if (current_value == 0) {
            cpu.turnOnFlags(Flags.ZERO);
        } else {
            cpu.turnOffFlags(Flags.ZERO);
        }
        if (cpu.HL.getValue() == 0) {
            cpu.turnOnFlags(Flags.HALF_CARRY);
        } else {
            cpu.turnOffFlags(Flags.HALF_CARRY);
        }
        current_value -= 1;
        current_value &= 255;
        cpu.memory.write(cpu.HL.getValue(), (byte) current_value);
        cpu.turnOnFlags(Flags.SUBTRACTION);
    }

    @Opcode(value = 0x3D, length = 1, cycles = 1)
    public static void dec_a(CPU cpu) {
        add_register(cpu, cpu.AF.A, 1, false, true);
    }
}
