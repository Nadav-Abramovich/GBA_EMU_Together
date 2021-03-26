package gameboy.cpu_instructions;

import gameboy.CPU;
import gameboy.Flags;

// We suppress this warning because this class and its methods are dynamically imported
// and therefore IntelliJ doesn't recognize their usage.
@SuppressWarnings("unused")
public class Subtracts implements CPUInstructions {
    @Opcode(value = 0x90, length = 1, cycles = 1)
    public static void sub_b() {
        char new_value = (char) (CPU.AF.A.getValue() & 255);
        // TODO: Are these 2 flags correct?
        if (new_value < (CPU.BC.B.getValue() & 255)) {
            CPU.turnOnFlags((byte) (Flags.CARRY | Flags.HALF_CARRY));
        } else {
            CPU.turnOffFlags((byte) (Flags.CARRY | Flags.HALF_CARRY));
        }
        new_value -= CPU.BC.B.getValue() & 255;
        CPU.AF.A.setValue((byte) new_value);
        if ((new_value & 255) == 0) {
            CPU.turnOnFlags(Flags.ZERO);
        } else {
            CPU.turnOffFlags(Flags.ZERO);
        }

        CPU.turnOnFlags(Flags.SUBTRACTION);
    }

    @Opcode(value = 0x91, length = 1, cycles = 1)
    public static void sub_c() {
        char new_value = (char) (CPU.AF.A.getValue() & 255);
        // TODO: Are these 2 flags correct?
        if (new_value < (CPU.BC.C.getValue() & 255)) {
            CPU.turnOnFlags((byte) (Flags.CARRY | Flags.HALF_CARRY));
        } else {
            CPU.turnOffFlags((byte) (Flags.CARRY | Flags.HALF_CARRY));
        }
        new_value -= CPU.BC.C.getValue() & 255;
        CPU.AF.A.setValue((byte) new_value);
        if ((new_value & 255) == 0) {
            CPU.turnOnFlags(Flags.ZERO);
        } else {
            CPU.turnOffFlags(Flags.ZERO);
        }

        CPU.turnOnFlags(Flags.SUBTRACTION);
    }

    @Opcode(value = 0x92, length = 1, cycles = 1)
    public static void sub_d() {
        char new_value = (char) (CPU.AF.A.getValue() & 255);
        // TODO: Are these 2 flags correct?
        if (new_value < (CPU.DE.D.getValue() & 255)) {
            CPU.turnOnFlags((byte) (Flags.CARRY | Flags.HALF_CARRY));
        } else {
            CPU.turnOffFlags((byte) (Flags.CARRY | Flags.HALF_CARRY));
        }
        new_value -= CPU.DE.D.getValue() & 255;
        CPU.AF.A.setValue((byte) new_value);
        if ((new_value & 255) == 0) {
            CPU.turnOnFlags(Flags.ZERO);
        } else {
            CPU.turnOffFlags(Flags.ZERO);
        }

        CPU.turnOnFlags(Flags.SUBTRACTION);
    }

    @Opcode(value = 0x93, length = 1, cycles = 1)
    public static void sub_e() {
        char new_value = (char) (CPU.AF.A.getValue() & 255);
        // TODO: Are these 2 flags correct?
        if (new_value < (CPU.DE.E.getValue() & 255)) {
            CPU.turnOnFlags((byte) (Flags.CARRY | Flags.HALF_CARRY));
        } else {
            CPU.turnOffFlags((byte) (Flags.CARRY | Flags.HALF_CARRY));
        }
        new_value -= CPU.DE.E.getValue() & 255;
        CPU.AF.A.setValue((byte) new_value);
        if ((new_value & 255) == 0) {
            CPU.turnOnFlags(Flags.ZERO);
        } else {
            CPU.turnOffFlags(Flags.ZERO);
        }

        CPU.turnOnFlags(Flags.SUBTRACTION);
    }

    @Opcode(value = 0x94, length = 1, cycles = 1)
    public static void sub_h() {
        char new_value = (char) (CPU.AF.A.getValue() & 255);
        // TODO: Are these 2 flags correct?
        if (new_value < (CPU.HL.H.getValue() & 255)) {
            CPU.turnOnFlags((byte) (Flags.CARRY | Flags.HALF_CARRY));
        } else {
            CPU.turnOffFlags((byte) (Flags.CARRY | Flags.HALF_CARRY));
        }
        new_value -= CPU.HL.H.getValue() & 255;
        CPU.AF.A.setValue((byte) new_value);
        if ((new_value & 255) == 0) {
            CPU.turnOnFlags(Flags.ZERO);
        } else {
            CPU.turnOffFlags(Flags.ZERO);
        }

        CPU.turnOnFlags(Flags.SUBTRACTION);
    }

    @Opcode(value = 0x95, length = 1, cycles = 1)
    public static void sub_l() {
        char new_value = (char) (CPU.AF.A.getValue() & 255);
        // TODO: Are these 2 flags correct?
        if (new_value < (CPU.HL.L.getValue() & 255)) {
            CPU.turnOnFlags((byte) (Flags.CARRY | Flags.HALF_CARRY));
        } else {
            CPU.turnOffFlags((byte) (Flags.CARRY | Flags.HALF_CARRY));
        }
        new_value -= CPU.HL.L.getValue() & 255;
        CPU.AF.A.setValue((byte) new_value);
        if ((new_value & 255) == 0) {
            CPU.turnOnFlags(Flags.ZERO);
        } else {
            CPU.turnOffFlags(Flags.ZERO);
        }

        CPU.turnOnFlags(Flags.SUBTRACTION);
    }

    @Opcode(value = 0x96, length = 1, cycles = 1)
    public static void sub_from_hl() {
        char new_value = (char) (CPU.AF.A.getValue() & 255);
        byte target = CPU.memory.read_byte(CPU.HL.getValue());
        // TODO: Are these 2 flags correct?
        if (new_value < (target & 255)) {
            CPU.turnOnFlags((byte) (Flags.CARRY | Flags.HALF_CARRY));
        } else {
            CPU.turnOffFlags((byte) (Flags.CARRY | Flags.HALF_CARRY));
        }
        new_value -= target & 255;
        CPU.AF.A.setValue((byte) new_value);
        if ((new_value & 255) == 0) {
            CPU.turnOnFlags(Flags.ZERO);
        } else {
            CPU.turnOffFlags(Flags.ZERO);
        }

        CPU.turnOnFlags(Flags.SUBTRACTION);
    }

    @Opcode(value = 0x97, length = 1, cycles = 1)
    public static void sub_a() {
        char new_value = (char) (CPU.AF.A.getValue() & 255);
        byte target = (byte) CPU.AF.getValue();
        // TODO: Are these 2 flags correct?
        if (new_value < (target & 255)) {
            CPU.turnOnFlags((byte) (Flags.CARRY | Flags.HALF_CARRY));
        } else {
            CPU.turnOffFlags((byte) (Flags.CARRY | Flags.HALF_CARRY));
        }
        new_value -= target & 255;
        CPU.AF.A.setValue((byte) new_value);
        if ((new_value & 255) == 0) {
            CPU.turnOnFlags(Flags.ZERO);
        } else {
            CPU.turnOffFlags(Flags.ZERO);
        }

        CPU.turnOnFlags(Flags.SUBTRACTION);
    }
}
