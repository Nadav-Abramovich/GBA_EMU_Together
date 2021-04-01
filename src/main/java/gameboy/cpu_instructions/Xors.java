package gameboy.cpu_instructions;

import gameboy.CPU;
import gameboy.Flags;

// We suppress this warning because this class and its methods are dynamically called
// and therefore IntelliJ doesn't recognize their usage.
@SuppressWarnings("unused")
public class Xors implements CPUInstructions {
    @Opcode(value = 0xAF, length = 1, cycles = 1)
    public static void xor_a() {
        CPU.AF.A.setValue((byte) 0);
        CPU.setFlags(Flags.ZERO);
    }

    @Opcode(value = 0xA8, length = 1, cycles = 1)
    public static void xor_b() {
        CPU.AF.A.setValue((byte) (CPU.AF.A.getValue() ^ CPU.BC.B.getValue()));

        // Make the ZERO flag the only turned on flag
        if(CPU.AF.A.getValue() == 0) {
            CPU.setFlags(Flags.ZERO);
        } else {
            CPU.setFlags((byte) 0);
        }
    }

    @Opcode(value = 0xA9, length = 1, cycles = 1)
    public static void xor_c() {
        CPU.AF.A.setValue((byte) (CPU.AF.A.getValue() ^ CPU.BC.C.getValue()));

        // Make the ZERO flag the only turned on flag
        if(CPU.AF.A.getValue() == 0) {
            CPU.setFlags(Flags.ZERO);
        } else {
            CPU.setFlags((byte) 0);
        }
    }

    @Opcode(value = 0xAA, length = 1, cycles = 1)
    public static void xor_d() {
        CPU.AF.A.setValue((byte) (CPU.AF.A.getValue() ^ CPU.DE.D.getValue()));

        // Make the ZERO flag the only turned on flag
        if(CPU.AF.A.getValue() == 0) {
            CPU.setFlags(Flags.ZERO);
        } else {
            CPU.setFlags((byte) 0);
        }
    }

    @Opcode(value = 0xAB, length = 1, cycles = 1)
    public static void xor_e() {
        CPU.AF.A.setValue((byte) (CPU.AF.A.getValue() ^ CPU.DE.E.getValue()));

        // Make the ZERO flag the only turned on flag
        if(CPU.AF.A.getValue() == 0) {
            CPU.setFlags(Flags.ZERO);
        } else {
            CPU.setFlags((byte) 0);
        }
    }

    @Opcode(value = 0xAC, length = 1, cycles = 1)
    public static void xor_h() {
        CPU.AF.A.setValue((byte) (CPU.AF.A.getValue() ^ CPU.HL.H.getValue()));

        // Make the ZERO flag the only turned on flag
        if(CPU.AF.A.getValue() == 0) {
            CPU.setFlags(Flags.ZERO);
        } else {
            CPU.setFlags((byte) 0);
        }
    }

    @Opcode(value = 0xAD, length = 1, cycles = 1)
    public static void xor_l() {
        CPU.AF.A.setValue((byte) (CPU.AF.A.getValue() ^ CPU.HL.L.getValue()));

        // Make the ZERO flag the only turned on flag
        if(CPU.AF.A.getValue() == 0) {
            CPU.setFlags(Flags.ZERO);
        } else {
            CPU.setFlags((byte) 0);
        }
    }

    @Opcode(value = 0xAE, length = 1, cycles = 2)
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

    @Opcode(value = 0xEE, length = 2, cycles = 2)
    public static void xor_d8() {
        byte d8 = CPU.memory.read_byte(CPU.PC.getValue() + 1);
        CPU.AF.A.setValue((byte)((CPU.AF.A.getValue() ^ d8)));
        if(CPU.AF.A.getValue() == 0) {
            CPU.setFlags(Flags.ZERO);
        } else {
            CPU.setFlags((byte) 0);
        }
    }
}
