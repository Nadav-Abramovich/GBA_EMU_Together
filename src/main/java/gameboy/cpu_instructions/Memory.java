package gameboy.cpu_instructions;

import gameboy.CPU;
import gameboy.Flags;

// We suppress this warning because this class and its methods are dynamically called
// and therefore IntelliJ doesn't recognize their usage.
@SuppressWarnings("unused")
public class Memory implements CPUInstructions {
    @Opcode(value = 0x01, length = 3, cycles = 3)
    public static void ld_bc_d16(CPU cpu) {
        byte lowerByte = cpu.memory.read_byte(cpu.PC.getValue() + 1);
        byte higherByte = cpu.memory.read_byte(cpu.PC.getValue() + 2);
        cpu.BC.C.setValue(lowerByte);
        cpu.BC.B.setValue(higherByte);
    }

    @Opcode(value = 0x11, length = 3, cycles = 3)
    public static void ld_de_d16(CPU cpu) {
        byte lowerByte = cpu.memory.read_byte(cpu.PC.getValue() + 1);
        byte higherByte = cpu.memory.read_byte(cpu.PC.getValue() + 2);
        cpu.DE.E.setValue(lowerByte);
        cpu.DE.D.setValue(higherByte);
    }

    @Opcode(value = 0x1A, length = 1, cycles = 2)
    public static void ld_a_from_de(CPU cpu) {
        cpu.AF.A.setValue(cpu.memory.read_byte(cpu.DE.getValue()));
    }

    @Opcode(value = 0x21, length = 3, cycles = 3)
    public static void ld_hl_d16(CPU cpu) {
        byte lowerByte = cpu.memory.read_byte(cpu.PC.getValue() + 1);
        byte higherByte = cpu.memory.read_byte(cpu.PC.getValue() + 2);
        cpu.HL.L.setValue(lowerByte);
        cpu.HL.H.setValue(higherByte);
    }

    @Opcode(value = 0x22, length = 1, cycles = 2)
    public static void ld_hl_plus_a(CPU cpu) {
        cpu.memory.write(cpu.HL.getValue(), (byte) (cpu.AF.A.getValue()));
        cpu.HL.increment(1);
    }

    @Opcode(value = 0x2A, length = 1, cycles = 2)
    public static void ld_a_hl_plus(CPU cpu) {
        cpu.AF.A.setValue(cpu.memory.read_byte(cpu.HL.getValue()));
        cpu.HL.increment(1);
    }

    @Opcode(value = 0x32, length = 1, cycles = 2)
    public static void ld_hl_minus_a(CPU cpu) {
        cpu.memory.write(cpu.HL.getValue(), (byte) cpu.AF.A.getValue());
        cpu.HL.increment(-1);
    }

    @Opcode(value = 0x3A, length = 1, cycles = 2)
    public static void ld_a_hl_minus(CPU cpu) {
        cpu.AF.A.setValue(cpu.memory.read_byte(cpu.HL.getValue()));
        cpu.HL.increment(-1);
    }

    @Opcode(value = 0x7E, length = 1, cycles = 2)
    public static void ld_a_from_hl(CPU cpu) {
        cpu.AF.A.setValue(cpu.memory.read_byte(cpu.HL.getValue()));
    }

    @Opcode(value = 0x86, length = 1, cycles = 2)
    public static void add_a_from_hl(CPU cpu) {
        char value = (char) (cpu.AF.A.getValue() + cpu.memory.read_byte(cpu.HL.getValue()));
        if (value >= 256) {
            cpu.turnOnFlags((byte) (Flags.CARRY | Flags.HALF_CARRY));
        } else {
            cpu.turnOffFlags((byte) (Flags.CARRY | Flags.HALF_CARRY));
        }

        cpu.AF.A.setValue((byte) (value & 255));
        if (cpu.AF.A.getValue() == 0) {
            cpu.turnOnFlags(Flags.ZERO);
        } else {
            cpu.turnOffFlags(Flags.ZERO);
        }

        cpu.turnOffFlags(Flags.SUBTRACTION);
    }

    @Opcode(value = 0x80, length = 1, cycles = 1)
    public static void add_a_b(CPU cpu) {
        if (cpu.AF.A.getValue() + cpu.BC.B.getValue() >= 256) {
            cpu.turnOnFlags((byte) (Flags.CARRY | Flags.HALF_CARRY));
        } else {
            cpu.turnOffFlags((byte) (Flags.CARRY | Flags.HALF_CARRY));
        }

        cpu.AF.A.setValue((byte) (cpu.AF.A.getValue() + cpu.BC.B.getValue()));
        if (cpu.AF.A.getValue() == 0) {
            cpu.turnOnFlags(Flags.ZERO);
        } else {
            cpu.turnOffFlags(Flags.ZERO);
        }

        cpu.turnOffFlags(Flags.SUBTRACTION);
    }

    @Opcode(value = 0x81, length = 1, cycles = 1)
    public static void add_a_c(CPU cpu) {
        if (cpu.AF.A.getValue() + cpu.BC.C.getValue() >= 256) {
            cpu.turnOnFlags((byte) (Flags.CARRY | Flags.HALF_CARRY));
        } else {
            cpu.turnOffFlags((byte) (Flags.CARRY | Flags.HALF_CARRY));
        }

        cpu.AF.A.setValue((byte) (cpu.AF.A.getValue() + cpu.BC.C.getValue()));
        if (cpu.AF.A.getValue() == 0) {
            cpu.turnOnFlags(Flags.ZERO);
        } else {
            cpu.turnOffFlags(Flags.ZERO);
        }

        cpu.turnOffFlags(Flags.SUBTRACTION);
    }

    @Opcode(value = 0x82, length = 1, cycles = 1)
    public static void add_a_d(CPU cpu) {
        if (cpu.AF.A.getValue() + cpu.DE.D.getValue() >= 256) {
            cpu.turnOnFlags((byte) (Flags.CARRY | Flags.HALF_CARRY));
        } else {
            cpu.turnOffFlags((byte) (Flags.CARRY | Flags.HALF_CARRY));
        }

        cpu.AF.A.setValue((byte) (cpu.AF.A.getValue() + cpu.DE.D.getValue()));
        if (cpu.AF.A.getValue() == 0) {
            cpu.turnOnFlags(Flags.ZERO);
        } else {
            cpu.turnOffFlags(Flags.ZERO);
        }

        cpu.turnOffFlags(Flags.SUBTRACTION);
    }

    @Opcode(value = 0x83, length = 1, cycles = 1)
    public static void add_a_e(CPU cpu) {
        if (cpu.AF.A.getValue() + cpu.DE.E.getValue() >= 256) {
            cpu.turnOnFlags((byte) (Flags.CARRY | Flags.HALF_CARRY));
        } else {
            cpu.turnOffFlags((byte) (Flags.CARRY | Flags.HALF_CARRY));
        }

        cpu.AF.A.setValue((byte) (cpu.AF.A.getValue() + cpu.DE.E.getValue()));
        if (cpu.AF.A.getValue() == 0) {
            cpu.turnOnFlags(Flags.ZERO);
        } else {
            cpu.turnOffFlags(Flags.ZERO);
        }

        cpu.turnOffFlags(Flags.SUBTRACTION);
    }

    @Opcode(value = 0x85, length = 1, cycles = 2)
    public static void add_a_l(CPU cpu) {
        if (cpu.AF.A.getValue() + cpu.HL.L.getValue() >= 256) {
            cpu.turnOnFlags((byte) (Flags.CARRY | Flags.HALF_CARRY));
        } else {
            cpu.turnOffFlags((byte) (Flags.CARRY | Flags.HALF_CARRY));
        }

        cpu.AF.A.setValue((byte) (cpu.AF.A.getValue() + cpu.HL.L.getValue()));
        if (cpu.AF.A.getValue() == 0) {
            cpu.turnOnFlags(Flags.ZERO);
        } else {
            cpu.turnOffFlags(Flags.ZERO);
        }

        cpu.turnOffFlags(Flags.SUBTRACTION);
    }

    @Opcode(value = 0x87, length = 1, cycles = 2)
    public static void add_a_a(CPU cpu) {
        if (cpu.AF.A.getValue() + cpu.AF.A.getValue() >= 256) {
            cpu.turnOnFlags((byte) (Flags.CARRY | Flags.HALF_CARRY));
        } else {
            cpu.turnOffFlags((byte) (Flags.CARRY | Flags.HALF_CARRY));
        }

        cpu.AF.A.setValue((byte) (cpu.AF.A.getValue() + cpu.AF.A.getValue()));
        if (cpu.AF.A.getValue() == 0) {
            cpu.turnOnFlags(Flags.ZERO);
        } else {
            cpu.turnOffFlags(Flags.ZERO);
        }

        cpu.turnOffFlags(Flags.SUBTRACTION);
    }

    @Opcode(value = 0x89, length = 1, cycles = 2)
    public static void adc_a_c(CPU cpu) {
        int carry_value = 0;
        if (cpu.AF.isCarryFlagOn()) {
            carry_value = 1;
        }

        if ((cpu.AF.A.getValue() + cpu.BC.C.getValue() + carry_value) >= 256) {
            cpu.turnOnFlags((byte) (Flags.CARRY | Flags.HALF_CARRY));
        } else {
            cpu.turnOffFlags((byte) (Flags.CARRY | Flags.HALF_CARRY));
        }
        if (((cpu.AF.A.getValue() + cpu.BC.C.getValue()) & 256) != 0) {
            cpu.turnOnFlags((byte) (Flags.CARRY | Flags.HALF_CARRY));
        } else {
            cpu.turnOffFlags((byte) (Flags.CARRY | Flags.HALF_CARRY));
        }

        cpu.AF.A.setValue((byte) (cpu.AF.A.getValue() + cpu.BC.C.getValue() + carry_value));
        if (cpu.AF.A.getValue() == 0) {
            cpu.turnOnFlags(Flags.ZERO);
        } else {
            cpu.turnOffFlags(Flags.ZERO);
        }

        cpu.turnOffFlags(Flags.SUBTRACTION);
    }

    @Opcode(value = 0x8E, length = 1, cycles = 2)
    public static void adc_a_from_hl(CPU cpu) {
        int carry_value = 0;
        if (cpu.AF.isCarryFlagOn()) {
            carry_value = 1;
        }

        if ((cpu.AF.A.getValue() + cpu.memory.read_byte(cpu.HL.getValue()) + carry_value) >= 256) {
            cpu.turnOnFlags((byte) (Flags.CARRY | Flags.HALF_CARRY));
        } else {
            cpu.turnOffFlags((byte) (Flags.CARRY | Flags.HALF_CARRY));
        }
        if (cpu.AF.A.getValue() + cpu.memory.read_byte(cpu.HL.getValue()) < 0) {
            cpu.turnOnFlags((byte) (Flags.CARRY | Flags.HALF_CARRY));
        } else {
            cpu.turnOffFlags((byte) (Flags.CARRY | Flags.HALF_CARRY));
        }

        cpu.AF.A.setValue((byte) (cpu.AF.A.getValue() + cpu.memory.read_byte(cpu.HL.getValue()) + carry_value));
        if (cpu.AF.A.getValue() == 0) {
            cpu.turnOnFlags(Flags.ZERO);
        } else {
            cpu.turnOffFlags(Flags.ZERO);
        }

        cpu.turnOffFlags(Flags.SUBTRACTION);
    }

    @Opcode(value = 0xC6, length = 2, cycles = 2)
    public static void add_a_d8(CPU cpu) {
        int value = cpu.memory.read_byte(cpu.PC.getValue() + 1);
        if (cpu.AF.A.getValue() + value >= 256) {
            cpu.turnOnFlags((byte) (Flags.CARRY | Flags.HALF_CARRY));
        } else {
            cpu.turnOffFlags((byte) (Flags.CARRY | Flags.HALF_CARRY));
        }

        cpu.AF.A.setValue((byte) (cpu.AF.A.getValue() + value));
        if (cpu.AF.A.getValue() == 0) {
            cpu.turnOnFlags(Flags.ZERO);
        } else {
            cpu.turnOffFlags(Flags.ZERO);
        }

        cpu.turnOffFlags(Flags.SUBTRACTION);
    }

    @Opcode(value = 0xD6, length = 2, cycles = 2)
    public static void sub_d8(CPU cpu) {
        int value = cpu.memory.read_byte(cpu.PC.getValue() + 1);
        if (cpu.AF.A.getValue() - value >= 256) {
            cpu.turnOnFlags((byte) (Flags.CARRY | Flags.HALF_CARRY));
        } else {
            cpu.turnOffFlags((byte) (Flags.CARRY | Flags.HALF_CARRY));
        }

        cpu.AF.A.setValue((byte) (cpu.AF.A.getValue() - value));
        if (cpu.AF.A.getValue() == 0) {
            cpu.turnOnFlags(Flags.ZERO);
        } else {
            cpu.turnOffFlags(Flags.ZERO);
        }

        cpu.turnOnFlags(Flags.SUBTRACTION);
    }
}
