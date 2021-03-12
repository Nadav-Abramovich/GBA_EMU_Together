package gameboy.cpu_instructions;

import gameboy.CPU;
import gameboy.Flags;

// We suppress this warning because this class and its methods are dynamically imported
// and therefore IntelliJ doesn't recognize their usage.
@SuppressWarnings("unused")
public class Rotates implements CPUInstructions {
    @Opcode(value = 0x07, length = 1, cycles = 1)
    public static void rlca(CPU cpu) {
        byte new_lsb = 0;
        if((cpu.AF.A.getValue() & 128) != 0) {
            cpu.turnOnFlags(Flags.CARRY);
            new_lsb = 1;
        } else {
            cpu.turnOffFlags(Flags.CARRY);
        }
        cpu.AF.A.setValue((byte)((cpu.AF.A.getValue()<<1) | new_lsb));
        cpu.turnOffFlags((byte)(Flags.ZERO | Flags.SUBTRACTION | Flags.HALF_CARRY));
    }

    @Opcode(value = 0x0F, length = 1, cycles = 1)
    public static void rrca(CPU cpu) {
        char new_msb = 0;
        if((cpu.AF.A.getValue() & 1) != 0) {
            cpu.turnOnFlags(Flags.CARRY);
            new_msb = 128;
        } else {
            cpu.turnOffFlags(Flags.CARRY);
        }
        cpu.AF.A.setValue((byte)((cpu.AF.A.getValue()>>1) | new_msb));
        cpu.turnOffFlags((byte)(Flags.ZERO | Flags.SUBTRACTION | Flags.HALF_CARRY));
    }

    @Opcode(value = 0x17, length = 1, cycles = 1)
    public static void rla(CPU cpu) {
        byte new_lsb = 0;
        if(cpu.AF.isCarryFlagOn()) {
            new_lsb = 1;
        }

        if((cpu.AF.A.getValue() & 128) != 0) {
            cpu.turnOnFlags(Flags.CARRY);
        } else {
            cpu.turnOffFlags(Flags.CARRY);
        }
        cpu.AF.A.setValue((byte)((cpu.AF.A.getValue()<<1) | new_lsb));
        cpu.turnOffFlags((byte)(Flags.ZERO | Flags.SUBTRACTION | Flags.HALF_CARRY));
    }

    @Opcode(value = 0x1F, length = 1, cycles = 1)
    public static void rra(CPU cpu) {
        char new_msb = 0;
        if(cpu.AF.isCarryFlagOn()) {
            new_msb = 128;
        }

        if((cpu.AF.A.getValue() & 1) != 0) {
            cpu.turnOnFlags(Flags.CARRY);
        } else {
            cpu.turnOffFlags(Flags.CARRY);
        }
        cpu.AF.A.setValue((byte)((cpu.AF.A.getValue()>>1) | new_msb));
        cpu.turnOffFlags((byte)(Flags.ZERO | Flags.SUBTRACTION | Flags.HALF_CARRY));
    }

    // TODO: Find a better place
    @Opcode(value = 0x2F, length = 1, cycles = 1)
    public static void cpl(CPU cpu) {
        cpu.AF.A.setValue((byte)(~(byte)cpu.AF.A.getValue()));
        if((cpu.AF.A.getValue() & 1) == 1) {
            cpu.setFlags(Flags.CARRY);
        } else {
            cpu.setFlags((byte)0);
        }
    }

    // TODO: Find a better place
    @Opcode(value = 0xCB37, length = 2, cycles = 2)
    public static void swap_a(CPU cpu) {
        cpu.AF.A.setValue((byte)(cpu.AF.getValue()>>4 | cpu.AF.getValue() << 4));
    }
}
