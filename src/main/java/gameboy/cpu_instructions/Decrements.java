package gameboy.cpu_instructions;

import gameboy.CPU;
import gameboy.Flags;

import static gameboy.HelperFunctions.add_register;

// We suppress this warning because this class and its methods are dynamically imported
// and therefore IntelliJ doesn't recognize their usage.
@SuppressWarnings("unused")
public class Decrements implements CPUInstructions {
    @Opcode(value = 0x05, length = 1, cycles = 1)
    public static void dec_b() {
        add_register(CPU.BC.B, 1, false, true, true);
    }

    @Opcode(value = 0x0B, length = 1, cycles = 2)
    public static void dec_bc() {
        add_register(CPU.BC, 1, false, true, true);
    }

    @Opcode(value = 0x0D, length = 1, cycles = 1)
    public static void dec_c() {
        add_register(CPU.BC.C, 1, false, true, true);
    }

    @Opcode(value = 0x15, length = 1, cycles = 1)
    public static void dec_d() {
        add_register(CPU.DE.D, 1, false, true, true);
    }

    @Opcode(value = 0x1B, length = 1, cycles = 2)
    public static void dec_de() {
        add_register(CPU.DE, 1, false, true, true);
    }

    @Opcode(value = 0x1D, length = 1, cycles = 1)
    public static void dec_e() {
        add_register(CPU.DE.E, 1, false, true, true);
    }

    @Opcode(value = 0x2D, length = 1, cycles = 1)
    public static void dec_l() {
        add_register(CPU.HL.L, 1, false, true, true);
    }

    @Opcode(value = 0x25, length = 1, cycles = 1)
    public static void dec_h() {
        add_register(CPU.HL.H, 1, false, true, true);
    }

    @Opcode(value = 0x2B, length = 1, cycles = 2)
    public static void dec_hl() {
        add_register(CPU.HL, 1, false, true, true);
    }

    @Opcode(value = 0x35, length = 1, cycles = 3)
    public static void dec_from_hl() {
        char current_value = (char) (CPU.memory.read_byte(CPU.HL.getValue()) & 255);
        if (current_value == 0) {
            CPU.turnOnFlags(Flags.ZERO);
        } else {
            CPU.turnOffFlags(Flags.ZERO);
        }
        if((CPU.memory.read_byte(CPU.HL.getValue()) & 1<<4) != (CPU.memory.read_byte(CPU.HL.getValue()) - 1 & 1<<4))
        {
            CPU.turnOnFlags(Flags.HALF_CARRY);
        } else {
            CPU.turnOffFlags(Flags.HALF_CARRY);
        }
        current_value -= 1;
        current_value &= 255;
        CPU.memory.write(CPU.HL.getValue(), (byte) current_value);
        CPU.turnOnFlags(Flags.SUBTRACTION);
    }

    @Opcode(value = 0x3B, length = 1, cycles = 2)
    public static void dec_sp() {
        add_register(CPU.SP, 1, false, true, true);
    }

    @Opcode(value = 0x3D, length = 1, cycles = 1)
    public static void dec_a() {
        add_register(CPU.AF.A, 1, false, true, true);
    }
}
