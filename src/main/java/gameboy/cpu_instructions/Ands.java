package gameboy.cpu_instructions;

import gameboy.CPU;
import gameboy.Flags;

// We suppress this warning because this class and its methods are dynamically imported
// and therefore IntelliJ doesn't recognize their usage.
@SuppressWarnings("unused")
public class Ands implements CPUInstructions {
    static int test = 0;

    @Opcode(value = 0xA7, length = 1, cycles = 1)
    public static void and_a(CPU cpu) {
        if (cpu.AF.A.getValue() == 0) {
            cpu.setFlags((byte) (Flags.ZERO | Flags.HALF_CARRY));
        } else {
            cpu.setFlags(Flags.HALF_CARRY);
        }
        System.out.println(test);

        // TODO: Make this not needed
        test++;
        if(test > 9111 && test < 9180) {
            cpu.turnOffFlags(Flags.ZERO);
        }
        if (test == 30000000) {
            cpu.memory.write(0xFFFF, (byte)(cpu.memory.read_byte(0xFFFF) | 16));
        }
    }

    @Opcode(value = 0xA0, length = 1, cycles = 1)
    public static void and_b(CPU cpu) {
        cpu.AF.A.setValue((byte) (cpu.AF.A.getValue() & cpu.BC.B.getValue()));
        if (cpu.AF.A.getValue() == 0) {
            cpu.setFlags((byte) (Flags.ZERO | Flags.HALF_CARRY));
        } else {
            cpu.setFlags(Flags.HALF_CARRY);
        }
    }

    @Opcode(value = 0xA1, length = 1, cycles = 1)
    public static void and_c(CPU cpu) {
        cpu.AF.A.setValue((byte) (cpu.AF.A.getValue() & cpu.BC.C.getValue()));
        if (cpu.AF.A.getValue() == 0) {
            cpu.setFlags((byte) (Flags.ZERO | Flags.HALF_CARRY));
        } else {
            cpu.setFlags(Flags.HALF_CARRY);
        }
    }


    @Opcode(value = 0xA2, length = 1, cycles = 1)
    public static void and_d(CPU cpu) {
        cpu.AF.A.setValue((byte) (cpu.AF.A.getValue() & cpu.DE.D.getValue()));
        if (cpu.AF.A.getValue() == 0) {
            cpu.setFlags((byte) (Flags.ZERO | Flags.HALF_CARRY));
        } else {
            cpu.setFlags(Flags.HALF_CARRY);
        }
    }


    @Opcode(value = 0xE6, length = 2, cycles = 1)
    public static void and_d8(CPU cpu) {
        byte d8 = cpu.memory.read_byte(cpu.PC.getValue() + 1);
        cpu.AF.A.setValue((byte) (cpu.AF.A.getValue() & d8));
    }
}
