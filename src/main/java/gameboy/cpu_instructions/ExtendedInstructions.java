package gameboy.cpu_instructions;

import gameboy.CPU;
import gameboy.Flags;

// We suppress this warning because this class and its methods are dynamically called
// and therefore IntelliJ doesn't recognize their usage.
@SuppressWarnings("unused")
public class ExtendedInstructions implements CPUInstructions {
    @Opcode(value = 0xCB00, length = 2, cycles = 2)
    public static void rlcb(CPU cpu) {
        byte new_lsb = 0;
        if((cpu.BC.B.getValue() & 128) != 0) {
            cpu.turnOnFlags(Flags.CARRY);
            new_lsb = 1;
        } else {
            cpu.turnOffFlags(Flags.CARRY);
        }
        cpu.BC.B.setValue((byte)((cpu.BC.B.getValue()<<1) | new_lsb));
        if(cpu.BC.B.getValue() == 0) {
            cpu.turnOnFlags(Flags.ZERO);
            cpu.turnOffFlags((byte) (Flags.SUBTRACTION | Flags.HALF_CARRY));
        }else {
            cpu.turnOffFlags((byte) (Flags.ZERO | Flags.SUBTRACTION | Flags.HALF_CARRY));
        }
    }

    @Opcode(value = 0xCB11, length = 2, cycles = 2)
    public static void rl_c(CPU cpu) {
        if((cpu.BC.C.getValue() & 128) != 0) {
            cpu.turnOnFlags(Flags.CARRY);
        } else {
            cpu.turnOffFlags(Flags.CARRY);
        }
        cpu.BC.C.setValue((byte)(cpu.BC.C.getValue() << 1));
        if(cpu.BC.C.getValue() == 0) {
            cpu.turnOnFlags(Flags.ZERO);
        } else {
            cpu.turnOffFlags(Flags.ZERO);
        }
        cpu.turnOffFlags((byte)(Flags.SUBTRACTION | Flags.HALF_CARRY));
    }

    @Opcode(value = 0xCB87, length = 2, cycles = 2)
    public static void res_0_a(CPU cpu) {
        byte new_value = (byte)cpu.AF.A.getValue();
        cpu.AF.A.setValue((byte)(new_value & 0xFE));
    }

    @Opcode(value = 0xCB7C, length = 2, cycles = 2)
    public static void bit_7h(CPU cpu) {
        int H = (cpu.HL.getValue() >> 15);

        if (H == 0) {
            cpu.turnOnFlags(Flags.ZERO);
        }
        else {
            cpu.turnOffFlags(Flags.ZERO);
        }
        cpu.turnOffFlags(Flags.SUBTRACTION);
        cpu.turnOnFlags(Flags.HALF_CARRY);
    }

    @Opcode(value = 0xCBCF, length = 2, cycles = 2)
    public static void set_1_a(CPU cpu) {
        byte new_value = (byte)cpu.AF.A.getValue();
        cpu.AF.A.setValue((byte)(new_value | 0x2));
    }
}