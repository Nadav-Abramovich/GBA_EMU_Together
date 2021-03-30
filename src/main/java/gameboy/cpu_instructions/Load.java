package gameboy.cpu_instructions;

import gameboy.CPU;
import gameboy.Flags;

import java.util.Locale;

// We suppress this warning because this class and its methods are dynamically imported
// and therefore IntelliJ doesn't recognize their usage.
@SuppressWarnings("unused")
public class Load implements CPUInstructions {
    @Opcode(value = 0x02, length = 1, cycles = 2)
    public static void ld_into_bc_a() {
        CPU.memory.write(CPU.BC.getValue(), (byte) CPU.AF.A.getValue());
    }

    @Opcode(value = 0x08, length = 3, cycles = 5)
    public static void ld_into_a16_sp() {
        char lower = (char) (CPU.memory.read_byte(CPU.PC.getValue() + 1) & 255);
        char higher = (char) (CPU.memory.read_byte(CPU.PC.getValue() + 2) & 255);
        CPU.memory.write((higher << 8) | lower, (byte) CPU.SP.P.getValue());
        CPU.memory.write(((higher << 8) | lower) + 1, (byte) CPU.SP.S.getValue());
    }

    @Opcode(value = 0x0A, length = 1, cycles = 2)
    public static void ld_a_from_bc() {
        char a8 = (char) (CPU.memory.read_byte(CPU.BC.getValue()) & 255);
        CPU.AF.A.setValue((byte) a8);
    }

    @Opcode(value = 0x12, length = 1, cycles = 2)
    public static void ld_into_de_a() {
        CPU.memory.write(CPU.DE.getValue(), (byte) (CPU.AF.A.getValue()));
    }

    @Opcode(value = 0x06, length = 2, cycles = 2)
    public static void ld_b_d8() {
        CPU.BC.B.setValue(CPU.memory.read_byte(CPU.PC.getValue() + 1));
    }

    @Opcode(value = 0x0E, length = 2, cycles = 2)
    public static void ld_c_d8() {
        CPU.BC.C.setValue(CPU.memory.read_byte(CPU.PC.getValue() + 1));
    }

    @Opcode(value = 0x16, length = 2, cycles = 2)
    public static void ld_d_d8() {
        CPU.DE.D.setValue(CPU.memory.read_byte(CPU.PC.getValue() + 1));
    }

    @Opcode(value = 0x1A, length = 1, cycles = 2)
    public static void ld_a_from_de() {
        char a8 = (char) (CPU.memory.read_byte(CPU.DE.getValue()) & 255);
        CPU.AF.A.setValue((byte) a8);
    }

    @Opcode(value = 0x1E, length = 2, cycles = 2)
    public static void ld_e_d8() {
        CPU.DE.E.setValue(CPU.memory.read_byte(CPU.PC.getValue() + 1));
    }

    @Opcode(value = 0x26, length = 2, cycles = 2)
    public static void ld_h_d8() {
        CPU.HL.H.setValue(CPU.memory.read_byte(CPU.PC.getValue() + 1));
    }

    @Opcode(value = 0x2E, length = 2, cycles = 2)
    public static void ld_l_d8() {
        CPU.HL.L.setValue(CPU.memory.read_byte(CPU.PC.getValue() + 1));
    }

    @Opcode(value = 0x36, length = 2, cycles = 3)
    public static void ld_into_hl_d8() {
        byte d8 = CPU.memory.read_byte(CPU.PC.getValue() + 1);
        CPU.memory.write(CPU.HL.getValue(), d8);
    }

    @Opcode(value = 0x3E, length = 2, cycles = 2)
    public static void ld_a_d8() {
        CPU.AF.A.setValue(CPU.memory.read_byte(CPU.PC.getValue() + 1));
    }

    @Opcode(value = 0x40, length = 1, cycles = 1)
    public static void ld_b_b() {
        CPU.BC.B.setValue((byte) CPU.BC.B.getValue());
    }

    @Opcode(value = 0x41, length = 1, cycles = 1)
    public static void ld_b_c() {
        CPU.BC.B.setValue((byte) CPU.BC.C.getValue());
    }

    @Opcode(value = 0x42, length = 1, cycles = 1)
    public static void ld_b_d() {
        CPU.BC.B.setValue((byte) CPU.DE.D.getValue());
    }

    @Opcode(value = 0x43, length = 1, cycles = 1)
    public static void ld_b_e() {
        CPU.BC.B.setValue((byte) CPU.DE.E.getValue());
    }

    @Opcode(value = 0x44, length = 1, cycles = 1)
    public static void ld_b_h() {
        CPU.BC.B.setValue((byte) CPU.HL.H.getValue());
    }

    @Opcode(value = 0x45, length = 1, cycles = 1)
    public static void ld_b_l() {
        CPU.BC.B.setValue((byte) CPU.HL.L.getValue());
    }

    @Opcode(value = 0x46, length = 1, cycles = 2)
    public static void ld_b_from_hl() {
        char d8 = (char) (CPU.memory.read_byte(CPU.HL.getValue()) & 255);
        CPU.BC.B.setValue((byte) d8);
    }

    @Opcode(value = 0x47, length = 1, cycles = 1)
    public static void ld_b_a() {
        CPU.BC.B.setValue((byte) CPU.AF.A.getValue());
    }

    @Opcode(value = 0x48, length = 1, cycles = 1)
    public static void ld_c_b() {
        CPU.BC.C.setValue((byte) CPU.BC.B.getValue());
    }

    @Opcode(value = 0x49, length = 1, cycles = 1)
    public static void ld_c_c() {
        CPU.BC.C.setValue((byte) CPU.BC.C.getValue());
    }

    @Opcode(value = 0x4A, length = 1, cycles = 1)
    public static void ld_c_d() {
        CPU.BC.C.setValue((byte) CPU.DE.D.getValue());
    }

    @Opcode(value = 0x4B, length = 1, cycles = 1)
    public static void ld_c_e() {
        CPU.BC.C.setValue((byte) CPU.DE.E.getValue());
    }

    @Opcode(value = 0x4C, length = 1, cycles = 1)
    public static void ld_c_h() {
        CPU.BC.C.setValue((byte) CPU.HL.H.getValue());
    }

    @Opcode(value = 0x4D, length = 1, cycles = 1)
    public static void ld_c_l() {
        CPU.BC.C.setValue((byte) CPU.HL.L.getValue());
    }

    @Opcode(value = 0x4E, length = 1, cycles = 2)
    public static void ld_c_from_hl() {
        char d8 = (char) (CPU.memory.read_byte(CPU.HL.getValue()) & 255);
        CPU.BC.C.setValue((byte) d8);
    }

    @Opcode(value = 0x4F, length = 1, cycles = 1)
    public static void ld_c_a() {
        CPU.BC.C.setValue((byte) CPU.AF.A.getValue());
    }

    @Opcode(value = 0x50, length = 1, cycles = 1)
    public static void ld_d_b() {
        CPU.DE.D.setValue((byte) CPU.BC.B.getValue());
    }

    @Opcode(value = 0x51, length = 1, cycles = 1)
    public static void ld_d_c() {
        CPU.DE.D.setValue((byte) CPU.BC.C.getValue());
    }

    @Opcode(value = 0x52, length = 1, cycles = 1)
    public static void ld_d_d() {
        CPU.DE.D.setValue((byte) CPU.DE.D.getValue());
    }

    @Opcode(value = 0x53, length = 1, cycles = 1)
    public static void ld_d_e() {
        CPU.DE.D.setValue((byte) CPU.DE.E.getValue());
    }

    @Opcode(value = 0x54, length = 1, cycles = 1)
    public static void ld_d_h() {
        CPU.DE.D.setValue((byte) CPU.HL.H.getValue());
    }

    @Opcode(value = 0x55, length = 1, cycles = 1)
    public static void ld_d_l() {
        CPU.DE.D.setValue((byte) CPU.HL.L.getValue());
    }

    @Opcode(value = 0x56, length = 1, cycles = 2)
    public static void ld_d_from_hl() {
        char a8 = (char) (CPU.memory.read_byte(CPU.HL.getValue()) & 255);
        CPU.DE.D.setValue((byte) a8);
    }

    @Opcode(value = 0x57, length = 1, cycles = 1)
    public static void ld_d_a() {
        CPU.DE.D.setValue((byte) CPU.AF.A.getValue());
    }

    @Opcode(value = 0x58, length = 1, cycles = 1)
    public static void ld_e_b() {
        CPU.DE.E.setValue((byte) CPU.BC.B.getValue());
    }

    @Opcode(value = 0x59, length = 1, cycles = 1)
    public static void ld_e_c() {
        CPU.DE.E.setValue((byte) CPU.BC.C.getValue());
    }

    @Opcode(value = 0x5A, length = 1, cycles = 1)
    public static void ld_e_d() {
        CPU.DE.E.setValue((byte) CPU.DE.D.getValue());
    }

    @Opcode(value = 0x5B, length = 1, cycles = 1)
    public static void ld_e_e() {
        CPU.DE.E.setValue((byte) CPU.DE.E.getValue());
    }

    @Opcode(value = 0x5C, length = 1, cycles = 1)
    public static void ld_e_h() {
        CPU.DE.E.setValue((byte) CPU.HL.H.getValue());
    }

    @Opcode(value = 0x5D, length = 1, cycles = 1)
    public static void ld_e_l() {
        CPU.DE.E.setValue((byte) CPU.HL.L.getValue());
    }

    @Opcode(value = 0x5E, length = 1, cycles = 2)
    public static void ld_e_from_hl() {
        char a8 = (char) (CPU.memory.read_byte(CPU.HL.getValue()) & 255);
        CPU.DE.E.setValue((byte) a8);
    }

    @Opcode(value = 0x5F, length = 1, cycles = 1)
    public static void ld_e_a() {
        CPU.DE.E.setValue((byte) CPU.AF.A.getValue());
    }

    @Opcode(value = 0x60, length = 1, cycles = 1)
    public static void ld_h_b() {
        CPU.HL.H.setValue((byte) CPU.BC.B.getValue());
    }

    @Opcode(value = 0x61, length = 1, cycles = 1)
    public static void ld_h_c() {
        CPU.HL.H.setValue((byte) CPU.BC.C.getValue());
    }


    @Opcode(value = 0x62, length = 1, cycles = 1)
    public static void ld_h_d() {
        CPU.HL.H.setValue((byte) CPU.DE.D.getValue());
    }

    @Opcode(value = 0x63, length = 1, cycles = 1)
    public static void ld_h_e() {
        CPU.HL.H.setValue((byte) CPU.DE.E.getValue());
    }

    @Opcode(value = 0x64, length = 1, cycles = 1)
    public static void ld_h_h() {
        CPU.HL.H.setValue((byte) CPU.HL.H.getValue());
    }

    @Opcode(value = 0x65, length = 1, cycles = 1)
    public static void ld_h_l() {
        CPU.HL.H.setValue((byte) CPU.HL.L.getValue());
    }

    @Opcode(value = 0x66, length = 1, cycles = 2)
    public static void ld_h_from_hl() {
        char a8 = (char) (CPU.memory.read_byte(CPU.HL.getValue()) & 255);
        CPU.HL.H.setValue((byte) a8);
    }

    @Opcode(value = 0x67, length = 1, cycles = 1)
    public static void ld_h_a() {
        CPU.HL.H.setValue((byte) CPU.AF.A.getValue());
    }

    @Opcode(value = 0x68, length = 1, cycles = 1)
    public static void ld_l_b() {
        CPU.HL.L.setValue((byte) CPU.BC.B.getValue());
    }

    @Opcode(value = 0x69, length = 1, cycles = 1)
    public static void ld_l_c() {
        CPU.HL.L.setValue((byte) CPU.BC.C.getValue());
    }

    @Opcode(value = 0x6A, length = 1, cycles = 1)
    public static void ld_l_d() {
        CPU.HL.L.setValue((byte) CPU.DE.D.getValue());
    }

    @Opcode(value = 0x6B, length = 1, cycles = 1)
    public static void ld_l_e() {
        CPU.HL.L.setValue((byte) CPU.DE.E.getValue());
    }

    @Opcode(value = 0x6C, length = 1, cycles = 1)
    public static void ld_l_h() {
        CPU.HL.L.setValue((byte) CPU.HL.H.getValue());
    }

    @Opcode(value = 0x6D, length = 1, cycles = 1)
    public static void ld_l_l() {
        CPU.HL.L.setValue((byte) CPU.HL.L.getValue());
    }

    @Opcode(value = 0x6E, length = 1, cycles = 2)
    public static void ld_l_from_hl() {
        char a8 = (char) (CPU.memory.read_byte(CPU.HL.getValue()) & 255);
        CPU.HL.L.setValue((byte) a8);
    }

    @Opcode(value = 0x6f, length = 1, cycles = 1)
    public static void ld_l_a() {
        CPU.HL.L.setValue((byte) CPU.AF.A.getValue());
    }

    @Opcode(value = 0x70, length = 1, cycles = 2)
    public static void ld_into_hl_b() {
        CPU.memory.write(CPU.HL.getValue(), (byte) CPU.BC.B.getValue());
    }

    @Opcode(value = 0x71, length = 1, cycles = 2)
    public static void ld_into_hl_c() {
        CPU.memory.write(CPU.HL.getValue(), (byte) CPU.BC.C.getValue());
    }

    @Opcode(value = 0x72, length = 1, cycles = 2)
    public static void ld_into_hl_d() {
        CPU.memory.write(CPU.HL.getValue(), (byte) CPU.DE.D.getValue());
    }

    @Opcode(value = 0x73, length = 1, cycles = 2)
    public static void ld_into_hl_e() {
        CPU.memory.write(CPU.HL.getValue(), (byte) CPU.DE.E.getValue());
    }

    @Opcode(value = 0x74, length = 1, cycles = 2)
    public static void ld_into_hl_h() {
        CPU.memory.write(CPU.HL.getValue(), (byte) CPU.HL.H.getValue());
    }

    @Opcode(value = 0x75, length = 1, cycles = 2)
    public static void ld_into_hl_l() {
        CPU.memory.write(CPU.HL.getValue(), (byte) CPU.HL.L.getValue());
    }

    @Opcode(value = 0x77, length = 1, cycles = 2)
    public static void ld_into_hl_a() {
        CPU.memory.write(CPU.HL.getValue(), (byte) CPU.AF.A.getValue());
    }

    @Opcode(value = 0x78, length = 1, cycles = 1)
    public static void ld_a_b() {
        CPU.AF.A.setValue((byte) CPU.BC.B.getValue());
    }

    @Opcode(value = 0x79, length = 1, cycles = 1)
    public static void ld_a_c() {
        CPU.AF.A.setValue((byte) CPU.BC.C.getValue());
    }

    @Opcode(value = 0x7a, length = 1, cycles = 1)
    public static void ld_a_d() {
        CPU.AF.A.setValue((byte) CPU.DE.D.getValue());
    }

    @Opcode(value = 0x7b, length = 1, cycles = 1)
    public static void ld_a_e() {
        CPU.AF.A.setValue((byte) CPU.DE.E.getValue());
    }

    @Opcode(value = 0x7c, length = 1, cycles = 1)
    public static void ld_a_h() {
        CPU.AF.A.setValue((byte) CPU.HL.H.getValue());
    }

    @Opcode(value = 0x7d, length = 1, cycles = 1)
    public static void ld_a_l() {
        CPU.AF.A.setValue((byte) CPU.HL.L.getValue());
    }

    @Opcode(value = 0x7F, length = 1, cycles = 1)
    public static void ld_a_a() {
        CPU.AF.A.setValue((byte) CPU.AF.A.getValue());
    }

    @Opcode(value = 0xE0, length = 2, cycles = 3)
    public static void ld_into_a8_a() {
        int position = CPU.memory.read_byte(CPU.PC.getValue() + 1) & 255;
        CPU.memory.write(0xFF00 | position, (byte) CPU.AF.A.getValue());
    }

    @Opcode(value = 0xE2, length = 1, cycles = 2)
    public static void ld_into_c_a() {
        CPU.memory.write(0xFF00 | CPU.BC.C.getValue(), (byte) (CPU.AF.A.getValue()));
    }

    @Opcode(value = 0xEA, length = 3, cycles = 4)
    public static void ld_into_a16_a() {
        char lower = (char) (CPU.memory.read_byte(CPU.PC.getValue() + 1) & 255);
        char higher = (char) (CPU.memory.read_byte(CPU.PC.getValue() + 2) & 255);
        CPU.memory.write((higher << 8) | lower, (byte) CPU.AF.A.getValue());
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

    @Opcode(value = 0xF0, length = 2, cycles = 3)
    public static void ld_a_from_a8() {
        char a8 = (char) (CPU.memory.read_byte(CPU.PC.getValue() + 1) & 255);
        CPU.AF.A.setValue(CPU.memory.read_byte(0xFF00 | a8));
    }

    @Opcode(value = 0xF6, length = 2, cycles = 2)
    public static void or_d8() {
        byte d8 = CPU.memory.read_byte(CPU.PC.getValue() + 1);
        CPU.AF.A.setValue((byte)((CPU.AF.A.getValue() | d8)&255));
        if(CPU.AF.A.getValue() == 0) {
            CPU.setFlags(Flags.ZERO);
        } else {
            CPU.setFlags((byte) 0);
        }
    }

    @Opcode(value = 0xFA, length = 3, cycles = 4)
    public static void ld_a_from_a16() {
        char lower = (char) (CPU.memory.read_byte(CPU.PC.getValue() + 1) & 255);
        char higher = (char) (CPU.memory.read_byte(CPU.PC.getValue() + 2) & 255);
        CPU.AF.A.setValue(CPU.memory.read_byte((char) (lower | (higher << 8))));
    }
}
