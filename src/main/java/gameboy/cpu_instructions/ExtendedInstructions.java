package gameboy.cpu_instructions;

import gameboy.CPU;
import gameboy.Flags;

// We suppress this warning because this class and its methods are dynamically called
// and therefore IntelliJ doesn't recognize their usage.
@SuppressWarnings("unused")
public class ExtendedInstructions implements CPUInstructions {
    // TODO: TEMP
    @Opcode(value = 0x0, length = 1)
    public static void nop(CPU cpu) {
        int t=1;
    }


    @Opcode(value = 0xCB11, length = 2)
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

    @Opcode(value = 0xCB7C, length = 2)
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
}