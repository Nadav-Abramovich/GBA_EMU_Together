package gameboy.cpu_instructions;

import gameboy.CPU;
import gameboy.Flags;

// We suppress this warning because this class and its methods are dynamically called
// and therefore IntelliJ doesn't recognize their usage.
@SuppressWarnings("unused")
public class ExtendedInstructions implements CPUInstructions {
    @Opcode(value = 0x1000, length = 2, cycles = 1)
    public static void stop() {
    }

    @Opcode(value = 0x76, length = 1, cycles = 1)
    public static void halt() {
        CPU.HALTED = true;
        CPU.IME = true;
    }

    @Opcode(value = 0xCB00, length = 2, cycles = 2)
    public static void rlcb() {
        byte new_lsb = 0;
        if ((CPU.BC.B.getValue() & 128) != 0) {
            CPU.turnOnFlags(Flags.CARRY);
            new_lsb = 1;
        } else {
            CPU.turnOffFlags(Flags.CARRY);
        }
        CPU.BC.B.setValue((byte) ((CPU.BC.B.getValue() << 1) | new_lsb));
        if (CPU.BC.B.getValue() == 0) {
            CPU.turnOnFlags(Flags.ZERO);
            CPU.turnOffFlags((byte) (Flags.SUBTRACTION | Flags.HALF_CARRY));
        } else {
            CPU.turnOffFlags((byte) (Flags.ZERO | Flags.SUBTRACTION | Flags.HALF_CARRY));
        }
    }

    @Opcode(value = 0xCB11, length = 2, cycles = 2)
    public static void rl_c() {
        int prev_carry = CPU.AF.isCarryFlagOn() ? 1 : 0;

        if ((CPU.BC.C.getValue() & 128) != 0) {
            CPU.turnOnFlags(Flags.CARRY);
        } else {
            CPU.turnOffFlags(Flags.CARRY);
        }
        CPU.BC.C.setValue((byte) ((CPU.BC.C.getValue() << 1) | prev_carry));
        if (CPU.BC.C.getValue() == 0) {
            CPU.turnOnFlags(Flags.ZERO);
        } else {
            CPU.turnOffFlags(Flags.ZERO);
        }
        CPU.turnOffFlags((byte) (Flags.SUBTRACTION | Flags.HALF_CARRY));
    }

    @Opcode(value = 0xCB40, length = 2, cycles = 2)
    public static void bit_0_b() {
        int C = (CPU.BC.B.getValue() & 1);

        if (C == 0) {
            CPU.turnOnFlags(Flags.ZERO);
        } else {
            CPU.turnOffFlags(Flags.ZERO);
        }
        CPU.turnOffFlags(Flags.SUBTRACTION);
        CPU.turnOnFlags(Flags.HALF_CARRY);
    }

    @Opcode(value = 0xCB41, length = 2, cycles = 2)
    public static void bit_0_c() {
        int C = (CPU.BC.C.getValue() & 1);

        if (C == 0) {
            CPU.turnOnFlags(Flags.ZERO);
        } else {
            CPU.turnOffFlags(Flags.ZERO);
        }
        CPU.turnOffFlags(Flags.SUBTRACTION);
        CPU.turnOnFlags(Flags.HALF_CARRY);
    }

    @Opcode(value = 0xCB42, length = 2, cycles = 2)
    public static void bit_0_d() {
        int C = (CPU.DE.D.getValue() & 1);

        if (C == 0) {
            CPU.turnOnFlags(Flags.ZERO);
        } else {
            CPU.turnOffFlags(Flags.ZERO);
        }
        CPU.turnOffFlags(Flags.SUBTRACTION);
        CPU.turnOnFlags(Flags.HALF_CARRY);
    }


    @Opcode(value = 0xCB47, length = 2, cycles = 2)
    public static void bit_0_a() {
        int A = (CPU.AF.A.getValue() & 1);

        if (A == 0) {
            CPU.turnOnFlags(Flags.ZERO);
        } else {
            CPU.turnOffFlags(Flags.ZERO);
        }
        CPU.turnOffFlags(Flags.SUBTRACTION);
        CPU.turnOnFlags(Flags.HALF_CARRY);
    }

    @Opcode(value = 0xCB48, length = 2, cycles = 2)
    public static void bit_1_b() {
        int A = (CPU.BC.B.getValue() >> 1) & 1;

        if (A == 0) {
            CPU.turnOnFlags(Flags.ZERO);
        } else {
            CPU.turnOffFlags(Flags.ZERO);
        }
        CPU.turnOffFlags(Flags.SUBTRACTION);
        CPU.turnOnFlags(Flags.HALF_CARRY);
    }

    @Opcode(value = 0xCB4F, length = 2, cycles = 2)
    public static void bit_1_a() {
        int A = ((CPU.AF.A.getValue() >> 1) & 1);

        if (A == 0) {
            CPU.turnOnFlags(Flags.ZERO);
        } else {
            CPU.turnOffFlags(Flags.ZERO);
        }
        CPU.turnOffFlags(Flags.SUBTRACTION);
        CPU.turnOnFlags(Flags.HALF_CARRY);
    }

    @Opcode(value = 0xCB50, length = 2, cycles = 2)
    public static void bit_2_b() {
        int B = (CPU.BC.B.getValue() >> 2) & 1;

        if (B == 0) {
            CPU.turnOnFlags(Flags.ZERO);
        } else {
            CPU.turnOffFlags(Flags.ZERO);
        }
        CPU.turnOffFlags(Flags.SUBTRACTION);
        CPU.turnOnFlags(Flags.HALF_CARRY);
    }

    @Opcode(value = 0xCB57, length = 2, cycles = 2)
    public static void bit_2_a() {
        int B = (CPU.AF.A.getValue() >> 2) & 1;

        if (B == 0) {
            CPU.turnOnFlags(Flags.ZERO);
        } else {
            CPU.turnOffFlags(Flags.ZERO);
        }
        CPU.turnOffFlags(Flags.SUBTRACTION);
        CPU.turnOnFlags(Flags.HALF_CARRY);
    }

    @Opcode(value = 0xCB58, length = 2, cycles = 2)
    public static void bit_3_b() {
        int B = (CPU.BC.B.getValue() >> 3) & 1;

        if (B == 0) {
            CPU.turnOnFlags(Flags.ZERO);
        } else {
            CPU.turnOffFlags(Flags.ZERO);
        }
        CPU.turnOffFlags(Flags.SUBTRACTION);
        CPU.turnOnFlags(Flags.HALF_CARRY);
    }

    @Opcode(value = 0xCB5F, length = 2, cycles = 2)
    public static void bit_3_a() {
        int H = (CPU.AF.A.getValue() >> 3) & 1;

        if (H == 0) {
            CPU.turnOnFlags(Flags.ZERO);
        } else {
            CPU.turnOffFlags(Flags.ZERO);
        }
        CPU.turnOffFlags(Flags.SUBTRACTION);
        CPU.turnOnFlags(Flags.HALF_CARRY);
    }

    @Opcode(value = 0xCB60, length = 2, cycles = 2)
    public static void bit_4_b() {
        int A = (CPU.BC.B.getValue() >> 4) & 1;

        if (A == 0) {
            CPU.turnOnFlags(Flags.ZERO);
        } else {
            CPU.turnOffFlags(Flags.ZERO);
        }
        CPU.turnOffFlags(Flags.SUBTRACTION);
        CPU.turnOnFlags(Flags.HALF_CARRY);
    }

    @Opcode(value = 0xCB61, length = 2, cycles = 2)
    public static void bit_4_c() {
        int A = (CPU.BC.C.getValue() >> 4) & 1;

        if (A == 0) {
            CPU.turnOnFlags(Flags.ZERO);
        } else {
            CPU.turnOffFlags(Flags.ZERO);
        }
        CPU.turnOffFlags(Flags.SUBTRACTION);
        CPU.turnOnFlags(Flags.HALF_CARRY);
    }

    @Opcode(value = 0xCB68, length = 2, cycles = 2)
    public static void bit_5_b() {
        int A = (CPU.BC.B.getValue() >> 5) & 1;

        if (A == 0) {
            CPU.turnOnFlags(Flags.ZERO);
        } else {
            CPU.turnOffFlags(Flags.ZERO);
        }
        CPU.turnOffFlags(Flags.SUBTRACTION);
        CPU.turnOnFlags(Flags.HALF_CARRY);
    }

    @Opcode(value = 0xCB69, length = 2, cycles = 2)
    public static void bit_5_c() {
        int A = (CPU.BC.C.getValue() >> 5) & 1;

        if (A == 0) {
            CPU.turnOnFlags(Flags.ZERO);
        } else {
            CPU.turnOffFlags(Flags.ZERO);
        }
        CPU.turnOffFlags(Flags.SUBTRACTION);
        CPU.turnOnFlags(Flags.HALF_CARRY);
    }

    @Opcode(value = 0xCB6F, length = 2, cycles = 2)
    public static void bit_5_a() {
        int A = (CPU.AF.A.getValue() >> 5) & 1;

        if (A == 0) {
            CPU.turnOnFlags(Flags.ZERO);
        } else {
            CPU.turnOffFlags(Flags.ZERO);
        }
        CPU.turnOffFlags(Flags.SUBTRACTION);
        CPU.turnOnFlags(Flags.HALF_CARRY);
    }

    @Opcode(value = 0xCB70, length = 2, cycles = 2)
    public static void bit_6_b() {
        int A = (CPU.BC.B.getValue() >> 6) & 1;

        if (A == 0) {
            CPU.turnOnFlags(Flags.ZERO);
        } else {
            CPU.turnOffFlags(Flags.ZERO);
        }
        CPU.turnOffFlags(Flags.SUBTRACTION);
        CPU.turnOnFlags(Flags.HALF_CARRY);
    }

    @Opcode(value = 0xCB71, length = 2, cycles = 2)
    public static void bit_6_c() {
        int A = (CPU.BC.C.getValue() >> 6) & 1;

        if (A == 0) {
            CPU.turnOnFlags(Flags.ZERO);
        } else {
            CPU.turnOffFlags(Flags.ZERO);
        }
        CPU.turnOffFlags(Flags.SUBTRACTION);
        CPU.turnOnFlags(Flags.HALF_CARRY);
    }

    @Opcode(value = 0xCB77, length = 2, cycles = 2)
    public static void bit_6_a() {
        int A = (CPU.AF.A.getValue() >> 6) & 1;

        if (A == 0) {
            CPU.turnOnFlags(Flags.ZERO);
        } else {
            CPU.turnOffFlags(Flags.ZERO);
        }
        CPU.turnOffFlags(Flags.SUBTRACTION);
        CPU.turnOnFlags(Flags.HALF_CARRY);
    }

    @Opcode(value = 0xCB78, length = 2, cycles = 2)
    public static void bit_7_b() {
        int A = (CPU.BC.B.getValue() >> 7) & 1;

        if (A == 0) {
            CPU.turnOnFlags(Flags.ZERO);
        } else {
            CPU.turnOffFlags(Flags.ZERO);
        }
        CPU.turnOffFlags(Flags.SUBTRACTION);
        CPU.turnOnFlags(Flags.HALF_CARRY);
    }

    @Opcode(value = 0xCB79, length = 2, cycles = 2)
    public static void bit_7_c() {
        int A = (CPU.BC.C.getValue() >> 7) & 1;

        if (A == 0) {
            CPU.turnOnFlags(Flags.ZERO);
        } else {
            CPU.turnOffFlags(Flags.ZERO);
        }
        CPU.turnOffFlags(Flags.SUBTRACTION);
        CPU.turnOnFlags(Flags.HALF_CARRY);
    }


    @Opcode(value = 0xCB7C, length = 2, cycles = 2)
    public static void bit_7_h() {
        int H = (CPU.HL.H.getValue() >> 7) & 1;

        if (H == 0) {
            CPU.turnOnFlags(Flags.ZERO);
        } else {
            CPU.turnOffFlags(Flags.ZERO);
        }
        CPU.turnOffFlags(Flags.SUBTRACTION);
        CPU.turnOnFlags(Flags.HALF_CARRY);
    }

    @Opcode(value = 0xCB7E, length = 2, cycles = 2)
    public static void bit_7_hl() {
        int bit_7 = (CPU.memory.read_byte(CPU.HL.getValue()) >> 7) & 1;

        if (bit_7 == 0) {
            CPU.turnOnFlags(Flags.ZERO);
        } else {
            CPU.turnOffFlags(Flags.ZERO);
        }
        CPU.turnOffFlags(Flags.SUBTRACTION);
        CPU.turnOnFlags(Flags.HALF_CARRY);
    }

    @Opcode(value = 0xCB7F, length = 2, cycles = 2)
    public static void bit_7_a() {
        int H = (CPU.AF.A.getValue() >> 7) & 1;

        if (H == 0) {
            CPU.turnOnFlags(Flags.ZERO);
        } else {
            CPU.turnOffFlags(Flags.ZERO);
        }
        CPU.turnOffFlags(Flags.SUBTRACTION);
        CPU.turnOnFlags(Flags.HALF_CARRY);
    }

    @Opcode(value = 0xCB86, length = 2, cycles = 4)
    public static void res_0_from_hl() {
        byte new_value = CPU.memory.read_byte(CPU.HL.getValue());
        CPU.memory.write(CPU.HL.getValue(), (byte) (new_value & 0xFE));
    }

    @Opcode(value = 0xCB87, length = 2, cycles = 2)
    public static void res_0_a() {
        byte new_value = (byte) CPU.AF.A.getValue();
        CPU.AF.A.setValue((byte) (new_value & 0xFE));
    }

    @Opcode(value = 0xCB8F, length = 2, cycles = 2)
    public static void res_1_a() {
        byte new_value = (byte) CPU.AF.A.getValue();
        CPU.AF.A.setValue((byte) (new_value & 0xFD));
    }

    @Opcode(value = 0xCB97, length = 2, cycles = 2)
    public static void res_2_a() {
        byte new_value = (byte) CPU.AF.A.getValue();
        CPU.AF.A.setValue((byte) (new_value & 0xFB));
    }

    @Opcode(value = 0xCBAF, length = 2, cycles = 2)
    public static void res_5_a() {
        byte new_value = (byte) CPU.AF.A.getValue();
        CPU.AF.A.setValue((byte) (new_value & (0xFF-16)));
    }

    @Opcode(value = 0xCBBE, length = 2, cycles = 4)
    public static void res_7_from_hl() {
        byte new_value = CPU.memory.read_byte(CPU.HL.getValue());
        CPU.memory.write(CPU.HL.getValue(), (byte) (new_value & 0x7F));
    }


    @Opcode(value = 0xCBCF, length = 2, cycles = 2)
    public static void set_1_a() {
        byte new_value = (byte) CPU.AF.A.getValue();
        CPU.AF.A.setValue((byte) (new_value | 0x2));
    }

    @Opcode(value = 0xCBD8, length = 2, cycles = 2)
    public static void set_3_b() {
        byte new_value = (byte) CPU.BC.B.getValue();
        CPU.BC.B.setValue((byte) (new_value | 0x8));
    }

    @Opcode(value = 0xCBDE, length = 2, cycles = 4)
    public static void set_3_into_hl() {
        byte new_value = CPU.memory.read_byte(CPU.HL.getValue());
        CPU.memory.write(CPU.HL.getValue(), (byte) (new_value | (1 << 3)));
    }

    @Opcode(value = 0xCBF8, length = 2, cycles = 2)
    public static void set_7_b() {
        byte new_value = (byte) CPU.BC.B.getValue();
        CPU.BC.B.setValue((byte) (new_value | (1<<7)));
    }

    @Opcode(value = 0xCBFE, length = 2, cycles = 4)
    public static void set_7_into_hl() {
        byte new_value = CPU.memory.read_byte(CPU.HL.getValue());
        CPU.memory.write(CPU.HL.getValue(), (byte) (new_value | (1 << 7)));
    }

    // TODO: Move this
    @Opcode(value = 0x27, length = 1, cycles = 1)
    public static void daa() {
        if (CPU.AF.isSubtractionFlagOn()) {
            if (CPU.AF.isCarryFlagOn()) {
                CPU.AF.A.setValue((byte) (CPU.AF.A.getValue() - 0x60));
            }
            if (CPU.AF.isHalfCarryFlagOn()) {
                CPU.AF.A.setValue((byte) (CPU.AF.A.getValue() - 0x06));
            }
        } else {
            if (CPU.AF.isCarryFlagOn() || CPU.AF.A.getValue() > 0x99) {
                CPU.AF.A.setValue((byte) (CPU.AF.A.getValue() + 0x60));
                CPU.turnOnFlags(Flags.CARRY);
            }
            if (CPU.AF.isHalfCarryFlagOn() || (CPU.AF.A.getValue() & 0x0F) > 0x09) {
                CPU.AF.A.setValue((byte) (CPU.AF.A.getValue() + 0x06));
            }
        }
        if (CPU.AF.A.getValue() == 0) {
            CPU.turnOnFlags(Flags.ZERO);
        } else {
            CPU.turnOffFlags(Flags.ZERO);
        }
        CPU.turnOffFlags(Flags.HALF_CARRY);
    }
    // TODO: implement this
    @Opcode(value = 0xCB27, length = 2, cycles = 2)
    public static void sla_a() {
        byte new_lsb = 0;

        if ((CPU.AF.A.getValue() & 128) != 0) {
            CPU.turnOnFlags(Flags.CARRY);
        } else {
            CPU.turnOffFlags(Flags.CARRY);
        }
        // todo: CARRY AND VERIFY CARRY OF RLC
        CPU.AF.A.setValue((byte) (((CPU.AF.A.getValue() << 1)) & 255));
        if(CPU.AF.A.getValue() == 0) {
            CPU.turnOnFlags(Flags.ZERO);
        } else {
            CPU.turnOffFlags(Flags.ZERO);
        }
        CPU.turnOffFlags((byte) (Flags.SUBTRACTION | Flags.HALF_CARRY));
    }
}