package gameboy.cpu_instructions;

import gameboy.CPU;
import gameboy.Flags;

// We suppress this warning because this class and its methods are dynamically called
// and therefore IntelliJ doesn't recognize their usage.
@SuppressWarnings("unused")
public class ExtendedInstructions implements CPUInstructions {
    @Opcode(value = 0x1000, length = 2, cycles = 1)
    public static void stop(CPU cpu) {
    }

    @Opcode(value = 0xCB00, length = 2, cycles = 2)
    public static void rlcb(CPU cpu) {
        byte new_lsb = 0;
        if ((cpu.BC.B.getValue() & 128) != 0) {
            cpu.turnOnFlags(Flags.CARRY);
            new_lsb = 1;
        } else {
            cpu.turnOffFlags(Flags.CARRY);
        }
        cpu.BC.B.setValue((byte) ((cpu.BC.B.getValue() << 1) | new_lsb));
        if (cpu.BC.B.getValue() == 0) {
            cpu.turnOnFlags(Flags.ZERO);
            cpu.turnOffFlags((byte) (Flags.SUBTRACTION | Flags.HALF_CARRY));
        } else {
            cpu.turnOffFlags((byte) (Flags.ZERO | Flags.SUBTRACTION | Flags.HALF_CARRY));
        }
    }

    @Opcode(value = 0xCB11, length = 2, cycles = 2)
    public static void rl_c(CPU cpu) {
        int prev_carry = cpu.AF.isCarryFlagOn() ? 1 : 0;

        if ((cpu.BC.C.getValue() & 128) != 0) {
            cpu.turnOnFlags(Flags.CARRY);
        } else {
            cpu.turnOffFlags(Flags.CARRY);
        }
        cpu.BC.C.setValue((byte) ((cpu.BC.C.getValue() << 1) | prev_carry));
        if (cpu.BC.C.getValue() == 0) {
            cpu.turnOnFlags(Flags.ZERO);
        } else {
            cpu.turnOffFlags(Flags.ZERO);
        }
        cpu.turnOffFlags((byte) (Flags.SUBTRACTION | Flags.HALF_CARRY));
    }

    @Opcode(value = 0xCB41, length = 2, cycles = 2)
    public static void bit_0_c(CPU cpu) {
        int C = (cpu.PC.C.getValue() & 1);

        if (C == 1) {
            cpu.turnOnFlags(Flags.ZERO);
        } else {
            cpu.turnOffFlags(Flags.ZERO);
        }
        cpu.turnOffFlags(Flags.SUBTRACTION);
        cpu.turnOnFlags(Flags.HALF_CARRY);
    }

    @Opcode(value = 0xCB47, length = 2, cycles = 2)
    public static void bit_0_a(CPU cpu) {
        int A = (cpu.AF.A.getValue() & 1);

        if (A == 1) {
            cpu.turnOnFlags(Flags.ZERO);
        } else {
            cpu.turnOffFlags(Flags.ZERO);
        }
        cpu.turnOffFlags(Flags.SUBTRACTION);
        cpu.turnOnFlags(Flags.HALF_CARRY);
    }

    @Opcode(value = 0xCB7C, length = 2, cycles = 2)
    public static void bit_7_h(CPU cpu) {
        int H = (cpu.HL.getValue() >> 15);

        if (H == 0) {
            cpu.turnOnFlags(Flags.ZERO);
        } else {
            cpu.turnOffFlags(Flags.ZERO);
        }
        cpu.turnOffFlags(Flags.SUBTRACTION);
        cpu.turnOnFlags(Flags.HALF_CARRY);
    }

    @Opcode(value = 0xCB87, length = 2, cycles = 2)
    public static void res_0_a(CPU cpu) {
        byte new_value = (byte) cpu.AF.A.getValue();
        cpu.AF.A.setValue((byte) (new_value & 0xFE));
    }

    @Opcode(value = 0xCBCF, length = 2, cycles = 2)
    public static void set_1_a(CPU cpu) {
        byte new_value = (byte) cpu.AF.A.getValue();
        cpu.AF.A.setValue((byte) (new_value | 0x2));
    }

    @Opcode(value = 0xCBFE, length = 2, cycles = 4)
    public static void set_7_into_hl(CPU cpu) {
        byte new_value = cpu.memory.read_byte(cpu.HL.getValue());
        cpu.memory.write(cpu.HL.getValue(), (byte) (new_value | (1 << 7)));
    }

    // TODO: Move this
    @Opcode(value = 0x27, length = 1, cycles = 1)
    public static void daa(CPU cpu) {
        if (cpu.AF.isSubtractionFlagOn()) {
            if (cpu.AF.isCarryFlagOn()) {
                cpu.AF.A.setValue((byte) (cpu.AF.A.getValue() - 0x60));
            }
            if (cpu.AF.isHalfCarryFlagOn()) {
                cpu.AF.A.setValue((byte) (cpu.AF.A.getValue() - 0x06));
            }
        } else {
            if (cpu.AF.isCarryFlagOn() || cpu.AF.A.getValue() > 0x99) {
                cpu.AF.A.setValue((byte) (cpu.AF.A.getValue() + 0x6));
            }
            if (cpu.AF.isHalfCarryFlagOn() || (cpu.AF.A.getValue() & 0x0F) > 0x09) {
                cpu.AF.A.setValue((byte) (cpu.AF.A.getValue() + 0x6));
            }
        }
        if (cpu.AF.A.getValue() == 0) {
            cpu.turnOnFlags(Flags.ZERO);
        } else {
            cpu.turnOffFlags(Flags.ZERO);
        }
        cpu.turnOffFlags(Flags.HALF_CARRY);
    }
    // TODO: implement this
    @Opcode(value = 0xCB27, length = 2, cycles = 8)
    public static void sla_a(CPU cpu) {
    }
}