package gameboy.cpu_instructions;

import gameboy.CPU;
import gameboy.Flags;

// We suppress this warning because this class and its methods are dynamically imported
// and therefore IntelliJ doesn't recognize their usage.
@SuppressWarnings("unused")
public class Increments implements CPUInstructions {
    @Opcode(value = 0x03, length = 1, cycles = 1)
    public static void inc_bc(CPU cpu) {
        cpu.BC.increment(1);
    }

    @Opcode(value = 0x04, length = 1, cycles = 1)
    public static void inc_b(CPU cpu) {
        char current_value = cpu.BC.B.getValue();
        current_value += 1;
        cpu.BC.B.setValue((byte) current_value);
        if ((current_value & 255) == 0) {
            cpu.turnOnFlags((byte) (Flags.ZERO | Flags.HALF_CARRY));
        } else {
            cpu.turnOffFlags((byte) (Flags.ZERO | Flags.HALF_CARRY));
        }
        cpu.turnOffFlags(Flags.SUBTRACTION);
    }


    @Opcode(value = 0x0C, length = 1, cycles = 1)
    public static void inc_c(CPU cpu) {
        char current_value = cpu.BC.C.getValue();
        current_value += 1;
        cpu.BC.C.setValue((byte) current_value);
        if ((current_value & 255) == 0) {
            cpu.turnOnFlags((byte) (Flags.ZERO | Flags.HALF_CARRY));
        } else {
            cpu.turnOffFlags((byte) (Flags.ZERO | Flags.HALF_CARRY));
        }
        cpu.turnOffFlags(Flags.SUBTRACTION);
    }

    @Opcode(value = 0x13, length = 1, cycles = 1)
    public static void inc_de(CPU cpu) {
        cpu.DE.increment(1);
    }

    @Opcode(value = 0x1C, length = 1, cycles = 1)
    public static void inc_e(CPU cpu) {
        char current_value = cpu.DE.E.getValue();
        current_value += 1;
        cpu.DE.E.setValue((byte) current_value);
        if ((current_value & 255) == 0) {
            cpu.turnOnFlags((byte) (Flags.ZERO | Flags.HALF_CARRY));
        } else {
            cpu.turnOffFlags((byte) (Flags.ZERO | Flags.HALF_CARRY));
        }
        cpu.turnOffFlags(Flags.SUBTRACTION);
    }


    @Opcode(value = 0x23, length = 1, cycles = 1)
    public static void inc_hl(CPU cpu) {
        cpu.HL.increment(1);
    }

    @Opcode(value = 0x24, length = 1, cycles = 1)
    public static void inc_h(CPU cpu) {
        char current_value = cpu.HL.H.getValue();
        current_value += 1;
        cpu.HL.H.setValue((byte) current_value);
        if ((current_value & 255) == 0) {
            cpu.turnOnFlags((byte) (Flags.ZERO | Flags.HALF_CARRY));
        } else {
            cpu.turnOffFlags((byte) (Flags.ZERO | Flags.HALF_CARRY));
        }
        cpu.turnOffFlags(Flags.SUBTRACTION);
    }

    @Opcode(value = 0x2C, length = 1, cycles = 1)
    public static void inc_l(CPU cpu) {
        char current_value = cpu.HL.L.getValue();
        current_value += 1;
        cpu.HL.L.setValue((byte) current_value);
        if ((current_value & 255) == 0) {
            cpu.turnOnFlags((byte) (Flags.ZERO | Flags.HALF_CARRY));
        } else {
            cpu.turnOffFlags((byte) (Flags.ZERO | Flags.HALF_CARRY));
        }
        cpu.turnOffFlags(Flags.SUBTRACTION);
    }

    @Opcode(value = 0x34, length = 1, cycles = 3)
    public static void inc_from_hl(CPU cpu) {
        char current_Value = (char) (cpu.memory.read_byte(cpu.HL.getValue()) & 255);
        if (current_Value == 255) {
            cpu.turnOnFlags((byte) (Flags.HALF_CARRY | Flags.ZERO));
        } else {
            cpu.turnOffFlags((byte) (Flags.HALF_CARRY | Flags.ZERO));
        }
        current_Value += 1;
        cpu.memory.write(cpu.HL.getValue(), (byte) (current_Value & 255));
        cpu.turnOffFlags(Flags.SUBTRACTION);
    }

    @Opcode(value = 0x3C, length = 1, cycles = 1)
    public static void inc_a(CPU cpu) {
        char current_value = cpu.AF.A.getValue();
        current_value += 1;
        cpu.AF.A.setValue((byte) current_value);
        if ((current_value & 255) == 0) {
            cpu.turnOnFlags((byte) (Flags.ZERO | Flags.HALF_CARRY));
        } else {
            cpu.turnOffFlags((byte) (Flags.ZERO | Flags.HALF_CARRY));
        }
        cpu.turnOffFlags(Flags.SUBTRACTION);
    }

}
