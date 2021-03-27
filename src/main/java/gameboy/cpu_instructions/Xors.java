package gameboy.cpu_instructions;

import gameboy.CPU;
import gameboy.Flags;

// We suppress this warning because this class and its methods are dynamically called
// and therefore IntelliJ doesn't recognize their usage.
@SuppressWarnings("unused")
public class Xors implements CPUInstructions {
    @Opcode(value = 0xAF, length = 1, cycles = 1)
    public static void xorA() {
        CPU.AF.A.setValue((byte) 0);
        CPU.setFlags(Flags.ZERO);
    }

    @Opcode(value = 0xA8, length = 1, cycles = 1)
    public static void xorB() {
        CPU.AF.A.setValue((byte) (CPU.AF.A.getValue() ^ CPU.BC.B.getValue()));

        // Make the ZERO flag the only turned on flag
        if(CPU.AF.A.getValue() == 0) {
            CPU.setFlags(Flags.ZERO);
        } else {
            CPU.setFlags((byte) 0);
        }
    }

    @Opcode(value = 0xA9, length = 1, cycles = 1)
    public static void xorC() {
        CPU.AF.A.setValue((byte) (CPU.AF.A.getValue() ^ CPU.BC.C.getValue()));

        // Make the ZERO flag the only turned on flag
        if(CPU.AF.A.getValue() == 0) {
            CPU.setFlags(Flags.ZERO);
        } else {
            CPU.setFlags((byte) 0);
        }
    }

    @Opcode(value = 0xAA, length = 1, cycles = 1)
    public static void xorD() {
        CPU.AF.A.setValue((byte) (CPU.AF.A.getValue() ^ CPU.DE.D.getValue()));

        // Make the ZERO flag the only turned on flag
        if(CPU.AF.A.getValue() == 0) {
            CPU.setFlags(Flags.ZERO);
        } else {
            CPU.setFlags((byte) 0);
        }
    }

    @Opcode(value = 0xAB, length = 1, cycles = 1)
    public static void xorE() {
        CPU.AF.A.setValue((byte) (CPU.AF.A.getValue() ^ CPU.DE.E.getValue()));

        // Make the ZERO flag the only turned on flag
        if(CPU.AF.A.getValue() == 0) {
            CPU.setFlags(Flags.ZERO);
        } else {
            CPU.setFlags((byte) 0);
        }
    }

    @Opcode(value = 0xAC, length = 1, cycles = 1)
    public static void xorH() {
        CPU.AF.A.setValue((byte) (CPU.AF.A.getValue() ^ CPU.HL.H.getValue()));

        // Make the ZERO flag the only turned on flag
        if(CPU.AF.A.getValue() == 0) {
            CPU.setFlags(Flags.ZERO);
        } else {
            CPU.setFlags((byte) 0);
        }
    }

    @Opcode(value = 0xAD, length = 1, cycles = 1)
    public static void xorL() {
        CPU.AF.A.setValue((byte) (CPU.AF.A.getValue() ^ CPU.HL.L.getValue()));

        // Make the ZERO flag the only turned on flag
        if(CPU.AF.A.getValue() == 0) {
            CPU.setFlags(Flags.ZERO);
        } else {
            CPU.setFlags((byte) 0);
        }
    }

    @Opcode(value = 0xAE, length = 1, cycles = 1)
    public static void xor_from_hl() {
        byte value = CPU.memory.read_byte(CPU.HL.getValue());
        CPU.AF.A.setValue((byte)(CPU.AF.A.getValue() ^ value));
        // Make the ZERO flag the only turned on flag
        if(CPU.AF.A.getValue() == 0){
            CPU.setFlags(Flags.ZERO);
        } else {
            CPU.setFlags((byte) 0);
        }
    }
}
