package gameboy.cpu_instructions;

import gameboy.CPU;
import gameboy.Flags;

import static gameboy.HelperFunctions.add_register;

// We suppress this warning because this class and its methods are dynamically imported
// and therefore IntelliJ doesn't recognize their usage.
@SuppressWarnings("unused")
public class Increments implements CPUInstructions {
    @Opcode(value = 0x03, length = 1, cycles = 1)
    public static void inc_bc() {
        CPU.BC.increment(1);
    }

    @Opcode(value = 0x04, length = 1, cycles = 1)
    public static void inc_b() {
        add_register(CPU.BC.B, 1, false, false);
    }


    @Opcode(value = 0x0C, length = 1, cycles = 1)
    public static void inc_c() {
        add_register(CPU.BC.C, 1, false, false);
    }

    @Opcode(value = 0x13, length = 1, cycles = 1)
    public static void inc_de() {
        CPU.DE.increment(1);
    }

    @Opcode(value = 0x14, length = 1, cycles = 1)
    public static void inc_d() {
        add_register(CPU.DE.D, 1, false, false);
    }

    @Opcode(value = 0x1C, length = 1, cycles = 1)
    public static void inc_e() {
        add_register(CPU.DE.E, 1, false, false);
    }


    @Opcode(value = 0x23, length = 1, cycles = 1)
    public static void inc_hl() {
        CPU.HL.increment(1);
    }

    @Opcode(value = 0x24, length = 1, cycles = 1)
    public static void inc_h() {
        add_register(CPU.HL.H, 1, false, false);
    }

    @Opcode(value = 0x2C, length = 1, cycles = 1)
    public static void inc_l() {
        add_register(CPU.HL.L, 1, false, false);
    }

    @Opcode(value = 0x33, length = 1, cycles = 2)
    public static void inc_sp() {
        CPU.SP.increment(1);
    }

    @Opcode(value = 0x34, length = 1, cycles = 3)
    public static void inc_from_hl() {
        char current_Value = (char) (CPU.memory.read_byte(CPU.HL.getValue()) & 255);
        if((current_Value & 1<<3) != 0 && (current_Value & 1<< 2) != 0 && (current_Value & 1<<1) != 0 && (current_Value & 1) != 0)
        {
            CPU.turnOnFlags(Flags.HALF_CARRY);
        } else {
            CPU.turnOffFlags(Flags.HALF_CARRY);
        }

        if (current_Value == 255) {
            CPU.turnOnFlags(Flags.ZERO);
        } else {
            CPU.turnOffFlags(Flags.ZERO);
        }
        current_Value += 1;
        CPU.memory.write(CPU.HL.getValue(), (byte) (current_Value & 255));
        CPU.turnOffFlags(Flags.SUBTRACTION);
    }

    @Opcode(value = 0x3C, length = 1, cycles = 1)
    public static void inc_a() {
        add_register(CPU.AF.A, 1, false, false);
    }
}
