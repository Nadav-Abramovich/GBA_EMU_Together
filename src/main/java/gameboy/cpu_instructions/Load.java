package gameboy.cpu_instructions;

import gameboy.CPU;

// We suppress this warning because this class and its methods are dynamically imported
// and therefore IntelliJ doesn't recognize their usage.
@SuppressWarnings("unused")
public class Load implements CPUInstructions {
    @Opcode(value = 0x06, length = 2)
    public static void ld_b_d8(CPU cpu) {
        cpu.BC.B.setValue(cpu.memory[cpu.PC.getValue() + 1]);
    }

    @Opcode(value = 0x0E, length = 2)
    public static void ld_c_d8(CPU cpu) {
        cpu.BC.C.setValue(cpu.memory[cpu.PC.getValue() + 1]);
    }

    @Opcode(value = 0x16, length = 2)
    public static void ld_d_d8(CPU cpu) {
        cpu.DE.D.setValue(cpu.memory[cpu.PC.getValue() + 1]);
    }

    @Opcode(value = 0x1E, length = 2)
    public static void ld_e_d8(CPU cpu) {
        cpu.DE.E.setValue(cpu.memory[cpu.PC.getValue() + 1]);
    }

    @Opcode(value = 0x2E, length = 2)
    public static void ld_l_d8(CPU cpu) {
        cpu.HL.L.setValue(cpu.memory[cpu.PC.getValue() + 1]);
    }

    @Opcode(value = 0x3E, length = 2)
    public static void ld_a_d8(CPU cpu) {
        cpu.AF.A.setValue(cpu.memory[cpu.PC.getValue() + 1]);
    }

    @Opcode(value = 0x4F, length = 1)
    public static void ld_c_a(CPU cpu) {
        cpu.BC.C.setValue((byte)cpu.AF.A.getValue());
    }

    @Opcode(value = 0x57, length = 1)
    public static void ld_d_a(CPU cpu) {
        cpu.DE.D.setValue((byte)cpu.AF.A.getValue());
    }

    @Opcode(value = 0x67, length = 1)
    public static void ld_h_a(CPU cpu) {
        cpu.HL.H.setValue((byte)cpu.AF.A.getValue());
    }

    @Opcode(value = 0x77, length = 1)
    public static void ld_into_hl_a(CPU cpu) {
        cpu.memory[cpu.HL.getValue()] = (byte)cpu.AF.A.getValue();
    }

    @Opcode(value = 0x78, length = 1)
    public static void ld_a_b(CPU cpu) {
        cpu.AF.A.setValue((byte)cpu.BC.B.getValue());
    }

    @Opcode(value = 0x7b, length = 1)
    public static void ld_a_e(CPU cpu) {
        cpu.AF.A.setValue((byte)cpu.DE.E.getValue());
    }

    @Opcode(value = 0x7c, length = 1)
    public static void ld_a_h(CPU cpu) {
        cpu.AF.A.setValue((byte)cpu.HL.H.getValue());
    }

    @Opcode(value = 0x7d, length = 1)
    public static void ld_a_l(CPU cpu) {
        cpu.AF.A.setValue((byte)cpu.HL.L.getValue());
    }

    @Opcode(value = 0xE2, length = 1)
    public static void ld_pointer_c_a(CPU cpu) {
        cpu.memory[0xFF00|cpu.BC.C.getValue()] = (byte)(cpu.AF.A.getValue());
    }


    @Opcode(value = 0xEA, length = 3)
    public static void ld_into_a16_a(CPU cpu) {
        char lower = (char)(cpu.memory[cpu.PC.getValue() + 1] & 255);
        char higher = (char)(cpu.memory[cpu.PC.getValue() + 2] & 255);
        cpu.memory[lower | (higher<<8)] = (byte)cpu.AF.A.getValue();
    }

    @Opcode(value = 0xF0, length = 2)
    public static void ld_a_from_a8(CPU cpu) {
        byte a8 =  cpu.memory[cpu.PC.getValue() + 1];
        cpu.AF.A.setValue(cpu.memory[0xFF00 | a8]);
    }
}
