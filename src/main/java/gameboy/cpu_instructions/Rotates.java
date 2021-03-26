package gameboy.cpu_instructions;

import gameboy.CPU;
import gameboy.Flags;

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

    @Opcode(value = 0xCB19, length = 2, cycles = 2)
    public static void rr_c() {
        char new_msb = 0;
        if((CPU.AF.F.getValue() & Flags.CARRY) != 0) {
            new_msb = 1 << 7;
        }
        if ((CPU.BC.C.getValue() & 1) != 0) {
            CPU.turnOnFlags(Flags.CARRY);
        } else {
            CPU.turnOffFlags(Flags.CARRY);
        }
        CPU.BC.C.setValue((byte) ((CPU.BC.C.getValue() >> 1) | new_msb));
        if(CPU.BC.C.getValue() == 0) {
            CPU.turnOnFlags(Flags.ZERO);
        } else {
            CPU.turnOffFlags(Flags.ZERO);
        }
        CPU.turnOffFlags((byte) (Flags.SUBTRACTION | Flags.HALF_CARRY));
    }

    @Opcode(value = 0xCB1A, length = 2, cycles = 2)
    public static void rr_d() {
        char new_msb = 0;
        if((CPU.AF.F.getValue() & Flags.CARRY) != 0) {
            new_msb = 1 << 7;
        }
        if ((CPU.DE.D.getValue() & 1) != 0) {
            CPU.turnOnFlags(Flags.CARRY);
        } else {
            CPU.turnOffFlags(Flags.CARRY);
        }
        CPU.DE.D.setValue((byte) ((CPU.DE.D.getValue() >> 1) | new_msb));
        if(CPU.DE.D.getValue() == 0) {
            CPU.turnOnFlags(Flags.ZERO);
        } else {
            CPU.turnOffFlags(Flags.ZERO);
        }
        CPU.turnOffFlags((byte) (Flags.SUBTRACTION | Flags.HALF_CARRY));
    }

    @Opcode(value = 0xCB1B, length = 2, cycles = 2)
    public static void rr_e() {
        char new_msb = 0;
        if((CPU.AF.F.getValue() & Flags.CARRY) != 0) {
            new_msb = 1 << 7;
        }
        if ((CPU.DE.E.getValue() & 1) != 0) {
            CPU.turnOnFlags(Flags.CARRY);
        } else {
            CPU.turnOffFlags(Flags.CARRY);
        }
        CPU.DE.E.setValue((byte) ((CPU.DE.E.getValue() >> 1) | new_msb));
        if(CPU.DE.E.getValue() == 0) {
            CPU.turnOnFlags(Flags.ZERO);
        } else {
            CPU.turnOffFlags(Flags.ZERO);
        }
        CPU.turnOffFlags((byte) (Flags.SUBTRACTION | Flags.HALF_CARRY));
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
    @Opcode(value = 0xCB37, length = 2, cycles = 2)
    public static void swap_a() {
        CPU.AF.A.setValue((byte) ((CPU.AF.A.getValue() >> 4) | (CPU.AF.A.getValue() << 4)));
        if (CPU.AF.A.getValue() == 0) {
            CPU.setFlags(Flags.ZERO);
        } else {
            CPU.setFlags((byte) 0);
        }
    }

    @Opcode(value = 0xCB38, length = 2, cycles = 2)
    public static void srl_b() {
        char new_msb = 0;
        if ((CPU.BC.B.getValue() & 1) != 0) {
            CPU.turnOnFlags(Flags.CARRY);
        } else {
            CPU.turnOffFlags(Flags.CARRY);
        }
        CPU.BC.B.setValue((byte) (CPU.BC.B.getValue() >> 1));
        if(CPU.BC.B.getValue() == 0) {
            CPU.turnOnFlags(Flags.ZERO);
        } else {
            CPU.turnOffFlags(Flags.ZERO);
        }
        CPU.turnOffFlags((byte) (Flags.SUBTRACTION | Flags.HALF_CARRY));
    }

    @Opcode(value = 0xCB3F, length = 2, cycles = 2)
    public static void srl_a() {
        char new_msb = 0;
        if ((CPU.AF.A.getValue() & 1) != 0) {
            CPU.turnOnFlags(Flags.CARRY);
        } else {
            CPU.turnOffFlags(Flags.CARRY);
        }
        CPU.AF.A.setValue((byte) (CPU.AF.A.getValue() >> 1));
        if(CPU.AF.A.getValue() == 0) {
            CPU.turnOnFlags(Flags.ZERO);
        } else {
            CPU.turnOffFlags(Flags.ZERO);
        }
        CPU.turnOffFlags((byte) (Flags.SUBTRACTION | Flags.HALF_CARRY));
    }
}
