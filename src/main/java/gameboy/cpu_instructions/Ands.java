package gameboy.cpu_instructions;

import gameboy.CPU;
import gameboy.Flags;

import static gameboy.HelperFunctions.and_val;

// We suppress this warning because this class and its methods are dynamically imported
// and therefore IntelliJ doesn't recognize their usage.
@SuppressWarnings("unused")
public class Ands implements CPUInstructions {
    @Opcode(value = 0xA7, length = 1, cycles = 1)
    public static void and_a() {
        and_val((byte) CPU.AF.A.getValue());
    }

    @Opcode(value = 0xA0, length = 1, cycles = 1)
    public static void and_b() {
        and_val((byte) CPU.BC.B.getValue());
    }

    @Opcode(value = 0xA1, length = 1, cycles = 1)
    public static void and_c() {
        and_val((byte) CPU.BC.C.getValue());
    }


    @Opcode(value = 0xA2, length = 1, cycles = 1)
    public static void and_d() {
        and_val((byte) CPU.DE.D.getValue());
    }

    @Opcode(value = 0xA3, length = 1, cycles = 1)
    public static void and_e() {
        and_val((byte) CPU.DE.E.getValue());
    }

    @Opcode(value = 0xA4, length = 1, cycles = 1)
    public static void and_h() {
        and_val((byte) CPU.HL.H.getValue());
    }

    @Opcode(value = 0xA5, length = 1, cycles = 1)
    public static void and_l() {
        and_val((byte) CPU.HL.L.getValue());
    }

    @Opcode(value = 0xA6, length = 1, cycles = 2)
    public static void and_from_hl() {
        byte d8 = CPU.memory.read_byte(CPU.HL.getValue());
        and_val(d8);
    }

    @Opcode(value = 0xE6, length = 2, cycles = 2)
    public static void and_d8() {
        byte d8 = CPU.memory.read_byte(CPU.PC.getValue() + 1);
        and_val(d8);
    }
}
