package gameboy.cpu_instructions;

import gameboy.CPU;
import gameboy.Flags;

import static gameboy.HelperFunctions.srl_reg;
import static gameboy.HelperFunctions.srl_val;

// We suppress this warning because this class and its methods are dynamically imported
// and therefore IntelliJ doesn't recognize their usage.
@SuppressWarnings("unused")
public class Rotates implements CPUInstructions {
    @Opcode(value = 0x07, length = 1, cycles = 1)
    public static void rlca() {
        byte new_lsb = 0;
        if ((CPU.AF.A.getValue() & 128) != 0) {
            CPU.turnOnFlags(Flags.CARRY);
            new_lsb = 1;
        } else {
            CPU.turnOffFlags(Flags.CARRY);
        }
        CPU.AF.A.setValue((byte) ((CPU.AF.A.getValue() << 1) | new_lsb));
        CPU.turnOffFlags((byte) (Flags.ZERO | Flags.SUBTRACTION | Flags.HALF_CARRY));
    }

    @Opcode(value = 0x0F, length = 1, cycles = 1)
    public static void rrca() {
        char new_msb = 0;
        if ((CPU.AF.A.getValue() & 1) != 0) {
            CPU.turnOnFlags(Flags.CARRY);
            new_msb = 128;
        } else {
            CPU.turnOffFlags(Flags.CARRY);
        }
        CPU.AF.A.setValue((byte) ((CPU.AF.A.getValue() >> 1) | new_msb));
        CPU.turnOffFlags((byte) (Flags.ZERO | Flags.SUBTRACTION | Flags.HALF_CARRY));
    }

    @Opcode(value = 0x17, length = 1, cycles = 1)
    public static void rla() {
        byte new_lsb = 0;
        if (CPU.AF.isCarryFlagOn()) {
            new_lsb = 1;
        }

        if ((CPU.AF.A.getValue() & 128) != 0) {
            CPU.turnOnFlags(Flags.CARRY);
        } else {
            CPU.turnOffFlags(Flags.CARRY);
        }
        CPU.AF.A.setValue((byte) (((CPU.AF.A.getValue() << 1) | new_lsb) & 255));
        CPU.turnOffFlags((byte) (Flags.ZERO | Flags.SUBTRACTION | Flags.HALF_CARRY));
    }

    @Opcode(value = 0x1F, length = 1, cycles = 1)
    public static void rra() {
        char new_msb = 0;
        if (CPU.AF.isCarryFlagOn()) {
            new_msb = 128;
        }

        if ((CPU.AF.A.getValue() & 1) != 0) {
            CPU.turnOnFlags(Flags.CARRY);
        } else {
            CPU.turnOffFlags(Flags.CARRY);
        }
        CPU.AF.A.setValue((byte) ((CPU.AF.A.getValue() >> 1) | new_msb));
        CPU.turnOffFlags((byte) (Flags.ZERO | Flags.SUBTRACTION | Flags.HALF_CARRY));
    }

    // TODO: Find a better place
    @Opcode(value = 0x2F, length = 1, cycles = 1)
    public static void cpl() {
        CPU.AF.A.setValue((byte) (~(byte) CPU.AF.A.getValue()));
        CPU.turnOnFlags((byte) (Flags.HALF_CARRY | Flags.SUBTRACTION));
    }

    // TODO: Find a better place
    @Opcode(value = 0x3F, length = 1, cycles = 1)
    public static void ccf() {
        if (CPU.AF.isCarryFlagOn()) {
            CPU.turnOffFlags(Flags.CARRY);
        } else {
            CPU.turnOnFlags(Flags.CARRY);
        }
        CPU.turnOffFlags((byte) (Flags.HALF_CARRY | Flags.SUBTRACTION));
    }

    // TODO: Find a better place
    @Opcode(value = 0xCB33, length = 2, cycles = 2)
    public static void swap_e() {
        CPU.DE.E.setValue((byte) ((CPU.DE.E.getValue() >> 4) | (CPU.DE.E.getValue() << 4)));
        if (CPU.DE.E.getValue() == 0) {
            CPU.setFlags(Flags.ZERO);
        } else {
            CPU.setFlags((byte) 0);
        }
    }

    // TODO: Find a better place
    @Opcode(value = 0xCB36, length = 2, cycles = 2)
    public static void swap_from_hl() {
        byte new_value = CPU.memory.read_byte(CPU.HL.getValue());
        new_value = (byte) (((new_value << 4)&0xF0) | ((new_value >> 4) & 0x0F));
        CPU.memory.write(CPU.HL.getValue(), new_value);
        if (CPU.memory.read_byte(CPU.HL.getValue()) == 0) {
            CPU.setFlags(Flags.ZERO);
        } else {
            CPU.setFlags((byte) 0);
        }
    }

    // TODO: Find a better place
    @Opcode(value = 0xCB37, length = 2, cycles = 2)
    public static void swap_a() {
        CPU.AF.A.setValue((byte) ((CPU.AF.A.getValue() >> 4) | (CPU.AF.A.getValue() << 4)));
        if (CPU.AF.A.getValue() == 0) {
            CPU.setFlags(Flags.ZERO);
        } else {
            CPU.setFlags((byte) 0);
        }
    }

    // region SRL
    @Opcode(value = 0xCB38, length = 2, cycles = 2)
    public static void srl_b() {
        srl_reg(CPU.BC.B);
    }

    @Opcode(value = 0xCB39, length = 2, cycles = 2)
    public static void srl_c() {
        srl_reg(CPU.BC.B);
    }

    @Opcode(value = 0xCB3A, length = 2, cycles = 2)
    public static void srl_d() {
        srl_reg(CPU.DE.D);
    }

    @Opcode(value = 0xCB3B, length = 2, cycles = 2)
    public static void srl_e() {
        srl_reg(CPU.DE.E);
    }

    @Opcode(value = 0xCB3C, length = 2, cycles = 2)
    public static void srl_h() {
        srl_reg(CPU.HL.H);
    }

    @Opcode(value = 0xCB3D, length = 2, cycles = 2)
    public static void srl_l() {
        srl_reg(CPU.HL.L);
    }

    @Opcode(value = 0xCB3E, length = 2, cycles = 2)
    public static void srl_hl() {
        CPU.memory.write(CPU.HL.getValue(), (byte) srl_val((char) CPU.memory.read_byte(CPU.HL.getValue())));
    }

    @Opcode(value = 0xCB3F, length = 2, cycles = 2)
    public static void srl_a() {
        srl_reg(CPU.AF.A);
    }
    // endregion
}
