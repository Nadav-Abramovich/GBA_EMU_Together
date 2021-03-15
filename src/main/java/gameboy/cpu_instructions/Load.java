package gameboy.cpu_instructions;

import gameboy.CPU;
import gameboy.Flags;

// We suppress this warning because this class and its methods are dynamically imported
// and therefore IntelliJ doesn't recognize their usage.
@SuppressWarnings("unused")
public class Load implements CPUInstructions {
    @Opcode(value = 0x02, length = 1, cycles = 2)
    public static void ld_into_bc_a(CPU cpu) {
        cpu.memory.write(cpu.BC.getValue(), (byte) cpu.AF.A.getValue());
    }

    @Opcode(value = 0x08, length = 3, cycles = 5)
    public static void ld_into_a16_sp(CPU cpu) {
        char lower = (char) (cpu.memory.read_byte(cpu.PC.getValue() + 1) & 255);
        char higher = (char) (cpu.memory.read_byte(cpu.PC.getValue() + 2) & 255);
        cpu.memory.write((higher << 8) | lower, (byte) cpu.SP.P.getValue());
        cpu.memory.write(((higher << 8) | lower) + 1, (byte) cpu.SP.S.getValue());
    }

    @Opcode(value = 0x0A, length = 1, cycles = 2)
    public static void ld_a_from_bc(CPU cpu) {
        char a8 = (char) (cpu.memory.read_byte(cpu.BC.getValue()) & 255);
        cpu.AF.A.setValue((byte) a8);
    }

    @Opcode(value = 0x12, length = 1, cycles = 2)
    public static void ld_into_de_a(CPU cpu) {
        cpu.memory.write(cpu.DE.getValue(), (byte) (cpu.AF.A.getValue()));
    }

    @Opcode(value = 0x06, length = 2, cycles = 2)
    public static void ld_b_d8(CPU cpu) {
        cpu.BC.B.setValue(cpu.memory.read_byte(cpu.PC.getValue() + 1));
    }

    @Opcode(value = 0x0E, length = 2, cycles = 2)
    public static void ld_c_d8(CPU cpu) {
        cpu.BC.C.setValue(cpu.memory.read_byte(cpu.PC.getValue() + 1));
    }

    @Opcode(value = 0x16, length = 2, cycles = 2)
    public static void ld_d_d8(CPU cpu) {
        cpu.DE.D.setValue(cpu.memory.read_byte(cpu.PC.getValue() + 1));
    }

    @Opcode(value = 0x1A, length = 1, cycles = 2)
    public static void ld_a_from_de(CPU cpu) {
        char a8 = (char) (cpu.memory.read_byte(cpu.DE.getValue()) & 255);
        cpu.AF.A.setValue((byte) a8);
    }

    @Opcode(value = 0x1E, length = 2, cycles = 2)
    public static void ld_e_d8(CPU cpu) {
        cpu.DE.E.setValue(cpu.memory.read_byte(cpu.PC.getValue() + 1));
    }

    @Opcode(value = 0x26, length = 2, cycles = 2)
    public static void ld_h_d8(CPU cpu) {
        cpu.HL.H.setValue(cpu.memory.read_byte(cpu.PC.getValue() + 1));
    }

    @Opcode(value = 0x2E, length = 2, cycles = 2)
    public static void ld_l_d8(CPU cpu) {
        cpu.HL.L.setValue(cpu.memory.read_byte(cpu.PC.getValue() + 1));
    }

    @Opcode(value = 0x36, length = 2, cycles = 3)
    public static void ld_into_hl_d8(CPU cpu) {
        byte d8 = cpu.memory.read_byte(cpu.PC.getValue() + 1);
        cpu.memory.write(cpu.HL.getValue(), d8);
    }

    @Opcode(value = 0x3E, length = 2, cycles = 2)
    public static void ld_a_d8(CPU cpu) {
        cpu.AF.A.setValue(cpu.memory.read_byte(cpu.PC.getValue() + 1));
    }

    @Opcode(value = 0x40, length = 1, cycles = 1)
    public static void ld_b_b(CPU cpu) {
        cpu.BC.B.setValue((byte) cpu.BC.B.getValue());
    }

    @Opcode(value = 0x44, length = 1, cycles = 1)
    public static void ld_b_a(CPU cpu) {
        cpu.BC.B.setValue((byte) cpu.HL.H.getValue());
    }

    @Opcode(value = 0x46, length = 1, cycles = 2)
    public static void ld_b_from_hl(CPU cpu) {
        char d8 = (char) (cpu.memory.read_byte(cpu.HL.getValue()) & 255);
        cpu.BC.B.setValue((byte) d8);
    }

    @Opcode(value = 0x47, length = 1, cycles = 1)
    public static void ld_b_h(CPU cpu) {
        cpu.BC.B.setValue((byte) cpu.AF.A.getValue());
    }

    @Opcode(value = 0x4A, length = 1, cycles = 1)
    public static void ld_c_d(CPU cpu) {
        cpu.BC.C.setValue((byte) cpu.DE.D.getValue());
    }

    @Opcode(value = 0x4B, length = 1, cycles = 1)
    public static void ld_c_e(CPU cpu) {
        cpu.BC.C.setValue((byte) cpu.DE.E.getValue());
    }

    @Opcode(value = 0x4D, length = 1, cycles = 1)
    public static void ld_c_l(CPU cpu) {
        cpu.BC.C.setValue((byte) cpu.HL.L.getValue());
    }

    @Opcode(value = 0x4E, length = 1, cycles = 2)
    public static void ld_c_from_hl(CPU cpu) {
        char d8 = (char) (cpu.memory.read_byte(cpu.HL.getValue()) & 255);
        cpu.BC.C.setValue((byte) d8);
    }

    @Opcode(value = 0x4F, length = 1, cycles = 1)
    public static void ld_c_a(CPU cpu) {
        cpu.BC.C.setValue((byte) cpu.AF.A.getValue());
    }

    @Opcode(value = 0x50, length = 1, cycles = 1)
    public static void ld_d_b(CPU cpu) {
        cpu.DE.D.setValue((byte) cpu.BC.B.getValue());
    }

    @Opcode(value = 0x52, length = 1, cycles = 1)
    public static void ld_d_d(CPU cpu) {
        cpu.DE.D.setValue((byte) cpu.DE.D.getValue());
    }

    @Opcode(value = 0x53, length = 1, cycles = 1)
    public static void ld_d_e(CPU cpu) {
        cpu.DE.D.setValue((byte) cpu.DE.E.getValue());
    }

    @Opcode(value = 0x54, length = 1, cycles = 1)
    public static void ld_d_h(CPU cpu) {
        cpu.DE.D.setValue((byte) cpu.HL.H.getValue());
    }

    @Opcode(value = 0x55, length = 1, cycles = 1)
    public static void ld_d_l(CPU cpu) {
        cpu.DE.D.setValue((byte) cpu.HL.L.getValue());
    }

    @Opcode(value = 0x56, length = 1, cycles = 2)
    public static void ld_d_from_hl(CPU cpu) {
        char a8 = (char) (cpu.memory.read_byte(cpu.HL.getValue()) & 255);
        cpu.DE.D.setValue((byte) a8);
    }

    @Opcode(value = 0x57, length = 1, cycles = 1)
    public static void ld_d_a(CPU cpu) {
        cpu.DE.D.setValue((byte) cpu.AF.A.getValue());
    }

    @Opcode(value = 0x58, length = 1, cycles = 1)
    public static void ld_e_b(CPU cpu) {
        cpu.DE.E.setValue((byte) cpu.BC.B.getValue());
    }

    @Opcode(value = 0x59, length = 1, cycles = 1)
    public static void ld_e_c(CPU cpu) {
        cpu.DE.E.setValue((byte) cpu.BC.C.getValue());
    }

    @Opcode(value = 0x5A, length = 1, cycles = 1)
    public static void ld_e_d(CPU cpu) {
        cpu.DE.E.setValue((byte) cpu.DE.D.getValue());
    }

    @Opcode(value = 0x5B, length = 1, cycles = 1)
    public static void ld_e_e(CPU cpu) {
        cpu.DE.E.setValue((byte) cpu.DE.E.getValue());
    }

    @Opcode(value = 0x5C, length = 1, cycles = 1)
    public static void ld_e_h(CPU cpu) {
        cpu.DE.E.setValue((byte) cpu.HL.H.getValue());
    }

    @Opcode(value = 0x5D, length = 1, cycles = 1)
    public static void ld_e_l(CPU cpu) {
        cpu.DE.E.setValue((byte) cpu.HL.L.getValue());
    }

    @Opcode(value = 0x5E, length = 1, cycles = 2)
    public static void ld_e_from_hl(CPU cpu) {
        char a8 = (char) (cpu.memory.read_byte(cpu.HL.getValue()) & 255);
        cpu.DE.E.setValue((byte) a8);
    }

    @Opcode(value = 0x5F, length = 1, cycles = 1)
    public static void ld_e_a(CPU cpu) {
        cpu.DE.E.setValue((byte) cpu.AF.A.getValue());
    }

    @Opcode(value = 0x60, length = 1, cycles = 1)
    public static void ld_h_b(CPU cpu) {
        cpu.HL.H.setValue((byte) cpu.BC.B.getValue());
    }

    @Opcode(value = 0x62, length = 1, cycles = 1)
    public static void ld_h_d(CPU cpu) {
        cpu.HL.H.setValue((byte) cpu.DE.D.getValue());
    }

    @Opcode(value = 0x66, length = 1, cycles = 2)
    public static void ld_h_from_hl(CPU cpu) {
        char a8 = (char) (cpu.memory.read_byte(cpu.HL.getValue()) & 255);
        cpu.HL.H.setValue((byte) a8);
    }

    @Opcode(value = 0x67, length = 1, cycles = 1)
    public static void ld_h_a(CPU cpu) {
        cpu.HL.H.setValue((byte) cpu.AF.A.getValue());
    }

    @Opcode(value = 0x6B, length = 1, cycles = 1)
    public static void ld_l_e(CPU cpu) {
        cpu.HL.L.setValue((byte) cpu.DE.E.getValue());
    }

    @Opcode(value = 0x69, length = 1, cycles = 1)
    public static void ld_l_c(CPU cpu) {
        cpu.HL.L.setValue((byte) cpu.BC.C.getValue());
    }

    @Opcode(value = 0x6C, length = 1, cycles = 1)
    public static void ld_l_h(CPU cpu) {
        cpu.HL.L.setValue((byte) cpu.HL.H.getValue());
    }

    @Opcode(value = 0x6f, length = 1, cycles = 1)
    public static void ld_l_a(CPU cpu) {
        cpu.HL.L.setValue((byte) cpu.AF.A.getValue());
    }

    @Opcode(value = 0x71, length = 1, cycles = 2)
    public static void ld_into_hl_c(CPU cpu) {
        cpu.memory.write(cpu.HL.getValue(), (byte) cpu.BC.C.getValue());
    }

    @Opcode(value = 0x72, length = 1, cycles = 2)
    public static void ld_into_hl_d(CPU cpu) {
        cpu.memory.write(cpu.HL.getValue(), (byte) cpu.DE.D.getValue());
    }

    @Opcode(value = 0x73, length = 1, cycles = 2)
    public static void ld_into_hl_e(CPU cpu) {
        cpu.memory.write(cpu.HL.getValue(), (byte) cpu.DE.E.getValue());
    }

    @Opcode(value = 0x74, length = 1, cycles = 2)
    public static void ld_into_hl_h(CPU cpu) {
        cpu.memory.write(cpu.HL.getValue(), (byte) cpu.HL.H.getValue());
    }

    @Opcode(value = 0x77, length = 1, cycles = 2)
    public static void ld_into_hl_a(CPU cpu) {
        cpu.memory.write(cpu.HL.getValue(), (byte) cpu.AF.A.getValue());
    }

    @Opcode(value = 0x78, length = 1, cycles = 1)
    public static void ld_a_b(CPU cpu) {
        cpu.AF.A.setValue((byte) cpu.BC.B.getValue());
    }

    @Opcode(value = 0x79, length = 1, cycles = 1)
    public static void ld_a_c(CPU cpu) {
        cpu.AF.A.setValue((byte) cpu.BC.C.getValue());
    }

    @Opcode(value = 0x7a, length = 1, cycles = 1)
    public static void ld_a_d(CPU cpu) {
        cpu.AF.A.setValue((byte) cpu.DE.D.getValue());
    }

    @Opcode(value = 0x7b, length = 1, cycles = 1)
    public static void ld_a_e(CPU cpu) {
        cpu.AF.A.setValue((byte) cpu.DE.E.getValue());
    }

    @Opcode(value = 0x7c, length = 1, cycles = 1)
    public static void ld_a_h(CPU cpu) {
        cpu.AF.A.setValue((byte) cpu.HL.H.getValue());
    }

    @Opcode(value = 0x7d, length = 1, cycles = 1)
    public static void ld_a_l(CPU cpu) {
        cpu.AF.A.setValue((byte) cpu.HL.L.getValue());
    }

    @Opcode(value = 0xE0, length = 2, cycles = 3)
    public static void ld_into_a8_a(CPU cpu) {
        int position = cpu.memory.read_byte(cpu.PC.getValue() + 1) & 255;
        cpu.memory.write(0xFF00 | position, (byte) cpu.AF.A.getValue());
    }

    @Opcode(value = 0xE2, length = 1, cycles = 2)
    public static void ld_into_c_a(CPU cpu) {
        cpu.memory.write(0xFF00 | cpu.BC.C.getValue(), (byte) (cpu.AF.A.getValue()));
    }

    @Opcode(value = 0xEA, length = 3, cycles = 4)
    public static void ld_into_a16_a(CPU cpu) {
        char lower = (char) (cpu.memory.read_byte(cpu.PC.getValue() + 1) & 255);
        char higher = (char) (cpu.memory.read_byte(cpu.PC.getValue() + 2) & 255);
        cpu.memory.write((higher << 8) | lower, (byte) cpu.AF.A.getValue());
    }

    @Opcode(value = 0xEE, length = 2, cycles = 2)
    public static void xor_d8(CPU cpu) {
        byte d8 = cpu.memory.read_byte(cpu.PC.getValue() + 1);
        cpu.AF.A.setValue((byte)((cpu.AF.A.getValue() ^ d8)&255));
        if(cpu.AF.getValue() == 0) {
            cpu.setFlags(Flags.ZERO);
        } else {
            cpu.setFlags((byte) 0);
        }
    }

    @Opcode(value = 0xF0, length = 2, cycles = 3)
    public static void ld_a_from_a8(CPU cpu) {
        char a8 = (char) (cpu.memory.read_byte(cpu.PC.getValue() + 1) & 255);
        cpu.AF.A.setValue(cpu.memory.read_byte(0xFF00 | a8));
    }

    @Opcode(value = 0xF6, length = 2, cycles = 2)
    public static void or_d8(CPU cpu) {
        byte d8 = cpu.memory.read_byte(cpu.PC.getValue() + 1);
        cpu.AF.A.setValue((byte)((cpu.AF.A.getValue() | d8)&255));
        if(cpu.AF.getValue() == 0) {
            cpu.setFlags(Flags.ZERO);
        } else {
            cpu.setFlags((byte) 0);
        }
    }

    @Opcode(value = 0xFA, length = 3, cycles = 4)
    public static void ld_a_from_a16(CPU cpu) {
        char lower = (char) (cpu.memory.read_byte(cpu.PC.getValue() + 1) & 255);
        char higher = (char) (cpu.memory.read_byte(cpu.PC.getValue() + 2) & 255);
        cpu.AF.A.setValue(cpu.memory.read_byte((char) (lower | (higher << 8))));
    }
}
