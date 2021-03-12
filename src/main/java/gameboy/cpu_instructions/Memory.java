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
        cpu.memory.write(cpu.HL.getValue(), (byte)(cpu.AF.A.getValue()));
        cpu.HL.increment(1);
    }

    @Opcode(value = 0x2A, length = 1, cycles = 2)
    public static void ld_a_hl_plus(CPU cpu) {
        cpu.AF.A.setValue(cpu.memory.read_byte(cpu.HL.getValue()));
        cpu.HL.increment(1);
    }

    @Opcode(value = 0x32, length = 1, cycles = 2)
    public static void ld_hl_minus_a(CPU cpu) {
        cpu.memory.write(cpu.HL.getValue(), (byte)cpu.AF.A.getValue());
        cpu.HL.increment(-1);
    }

    @Opcode(value = 0x7E, length = 1, cycles = 2)
    public static void ld_a_from_hl(CPU cpu) {
        cpu.AF.A.setValue(cpu.memory.read_byte(cpu.HL.getValue()));
    }

    @Opcode(value = 0x86, length = 1, cycles = 2)
    public static void add_a_from_hl(CPU cpu) {
        if(cpu.AF.A.getValue() + cpu.memory.read_byte(cpu.HL.getValue()) >= 256) {
            cpu.turnOnFlags((byte)(Flags.CARRY | Flags.HALF_CARRY));
        } else {
            cpu.turnOffFlags((byte)(Flags.CARRY | Flags.HALF_CARRY));
        }
        if(cpu.AF.A.getValue() + cpu.memory.read_byte(cpu.HL.getValue()) < 0) {
            cpu.turnOnFlags((byte)(Flags.CARRY | Flags.HALF_CARRY));
        } else {
            cpu.turnOffFlags((byte)(Flags.CARRY | Flags.HALF_CARRY));
        }

        cpu.AF.A.setValue((byte)(cpu.AF.A.getValue() + cpu.memory.read_byte(cpu.HL.getValue())));
        if(cpu.AF.A.getValue() == 0) {
            cpu.turnOnFlags(Flags.ZERO);
        } else {
            cpu.turnOffFlags(Flags.ZERO);
        }

        cpu.turnOffFlags(Flags.SUBTRACTION);
    }

    @Opcode(value = 0x82, length = 1, cycles = 2)
    public static void add_a_d(CPU cpu) {
        if(cpu.AF.A.getValue() + cpu.DE.D.getValue() >= 256) {
            cpu.turnOnFlags((byte)(Flags.CARRY | Flags.HALF_CARRY));
        } else {
            cpu.turnOffFlags((byte)(Flags.CARRY | Flags.HALF_CARRY));
        }

        cpu.AF.A.setValue((byte)(cpu.AF.A.getValue() + cpu.DE.D.getValue()));
        if(cpu.AF.A.getValue() == 0) {
            cpu.turnOnFlags(Flags.ZERO);
        } else {
            cpu.turnOffFlags(Flags.ZERO);
        }

        cpu.turnOffFlags(Flags.SUBTRACTION);
    }

    @Opcode(value = 0x87, length = 1, cycles = 2)
    public static void add_a_a(CPU cpu) {
        if(cpu.AF.A.getValue() + cpu.AF.A.getValue() >= 256) {
            cpu.turnOnFlags((byte)(Flags.CARRY | Flags.HALF_CARRY));
        } else {
            cpu.turnOffFlags((byte)(Flags.CARRY | Flags.HALF_CARRY));
        }

        cpu.AF.A.setValue((byte)(cpu.AF.A.getValue() + cpu.AF.A.getValue()));
        if(cpu.AF.A.getValue() == 0) {
            cpu.turnOnFlags(Flags.ZERO);
        } else {
            cpu.turnOffFlags(Flags.ZERO);
        }

        cpu.turnOffFlags(Flags.SUBTRACTION);
    }
}
