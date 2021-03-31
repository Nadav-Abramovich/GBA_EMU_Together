package gameboy.cpu_instructions;

import gameboy.CPU;

import static gameboy.HelperFunctions.cmp_value;

// We suppress this warning because this class and its methods are dynamically imported
// and therefore IntelliJ doesn't recognize their usage.
@SuppressWarnings("unused")
public class Compares implements CPUInstructions {
    @Opcode(value = 0xB8, length = 1, cycles = 1)
    public static void cp_b() {
        cmp_value(CPU.BC.B.getValue());
    }

    @Opcode(value = 0xB9, length = 1, cycles = 1)
    public static void cp_c() {
        cmp_value(CPU.BC.C.getValue());
    }

    @Opcode(value = 0xBA, length = 1, cycles = 1)
    public static void cp_d() {
        cmp_value(CPU.DE.D.getValue());
    }

    @Opcode(value = 0xBB, length = 1, cycles = 1)
    public static void cp_e() {
        cmp_value(CPU.DE.E.getValue());
    }

    @Opcode(value = 0xBC, length = 1, cycles = 1)
    public static void cp_h() {
        cmp_value(CPU.HL.H.getValue());
    }

    @Opcode(value = 0xBD, length = 1, cycles = 1)
    public static void cp_l() {
        cmp_value(CPU.HL.L.getValue());
    }

    @Opcode(value = 0xBE, length = 1, cycles = 2)
    public static void cp_from_hl() {
        cmp_value((char) (CPU.memory.read_byte(CPU.HL.getValue()) & 255));
    }

    @Opcode(value = 0xBF, length = 1, cycles = 2)
    public static void cp_a() {
        cmp_value(CPU.AF.A.getValue());
    }


    @Opcode(value = 0xFE, length = 2, cycles = 2)
    public static void cp_d8() {
        char compare_value = (char) (CPU.memory.read_byte(CPU.PC.getValue() + 1) & 255);
        cmp_value(compare_value);
    }
}
