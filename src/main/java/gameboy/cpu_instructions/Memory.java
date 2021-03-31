package gameboy.cpu_instructions;

import gameboy.CPU;
import gameboy.Flags;

import java.util.Locale;

import static gameboy.HelperFunctions.add_register;

// We suppress this warning because this class and its methods are dynamically called
// and therefore IntelliJ doesn't recognize their usage.
@SuppressWarnings("unused")
public class Memory implements CPUInstructions {
    @Opcode(value = 0x01, length = 3, cycles = 3)
    public static void ld_bc_d16() {
        byte lowerByte = CPU.memory.read_byte(CPU.PC.getValue() + 1);
        byte higherByte = CPU.memory.read_byte(CPU.PC.getValue() + 2);
        CPU.BC.C.setValue(lowerByte);
        CPU.BC.B.setValue(higherByte);
    }

    @Opcode(value = 0x11, length = 3, cycles = 3)
    public static void ld_de_d16() {
        byte lowerByte = CPU.memory.read_byte(CPU.PC.getValue() + 1);
        byte higherByte = CPU.memory.read_byte(CPU.PC.getValue() + 2);
        CPU.DE.E.setValue(lowerByte);
        CPU.DE.D.setValue(higherByte);
    }

    @Opcode(value = 0x1A, length = 1, cycles = 2)
    public static void ld_a_from_de() {
        CPU.AF.A.setValue(CPU.memory.read_byte(CPU.DE.getValue()));
    }

    @Opcode(value = 0x21, length = 3, cycles = 3)
    public static void ld_hl_d16() {
        byte lowerByte = CPU.memory.read_byte(CPU.PC.getValue() + 1);
        byte higherByte = CPU.memory.read_byte(CPU.PC.getValue() + 2);
        CPU.HL.L.setValue(lowerByte);
        CPU.HL.H.setValue(higherByte);
    }

    @Opcode(value = 0x22, length = 1, cycles = 2)
    public static void ld_hl_plus_a() {
        CPU.memory.write(CPU.HL.getValue(), (byte) (CPU.AF.A.getValue()));
        CPU.HL.increment(1);
    }

    @Opcode(value = 0x2A, length = 1, cycles = 2)
    public static void ld_a_hl_plus() {
        CPU.AF.A.setValue(CPU.memory.read_byte(CPU.HL.getValue()));
        CPU.HL.increment(1);
    }

    @Opcode(value = 0x32, length = 1, cycles = 2)
    public static void ld_hl_minus_a() {
        CPU.memory.write(CPU.HL.getValue(), (byte) CPU.AF.A.getValue());
        CPU.HL.increment(-1);
    }

    @Opcode(value = 0x3A, length = 1, cycles = 2)
    public static void ld_a_hl_minus() {
        CPU.AF.A.setValue(CPU.memory.read_byte(CPU.HL.getValue()));
        CPU.HL.increment(-1);
    }

    @Opcode(value = 0x7E, length = 1, cycles = 2)
    public static void ld_a_from_hl() {
        CPU.AF.A.setValue(CPU.memory.read_byte(CPU.HL.getValue()));
    }

    @Opcode(value = 0x86, length = 1, cycles = 2)
    public static void add_a_from_hl() {
        char value = (char) CPU.memory.read_byte(CPU.HL.getValue());
        add_register(CPU.AF.A, value, false, false, false);
    }

    @Opcode(value = 0x80, length = 1, cycles = 1)
    public static void add_a_b() {
        add_register(CPU.AF.A, CPU.BC.B.getValue(), false, false, false);
    }

    @Opcode(value = 0x81, length = 1, cycles = 1)
    public static void add_a_c() {
        add_register(CPU.AF.A, CPU.BC.C.getValue(), false, false, false);
    }

    @Opcode(value = 0x82, length = 1, cycles = 1)
    public static void add_a_d() {
        add_register(CPU.AF.A, CPU.DE.D.getValue(), false, false, false);
    }

    @Opcode(value = 0x83, length = 1, cycles = 1)
    public static void add_a_e() {
        add_register(CPU.AF.A, CPU.DE.E.getValue(), false, false, false);
    }

    @Opcode(value = 0x84, length = 1, cycles = 1)
    public static void add_a_h() {
        add_register(CPU.AF.A, CPU.HL.H.getValue(), false, false, false);
    }

    @Opcode(value = 0x85, length = 1, cycles = 2)
    public static void add_a_l() {
        add_register(CPU.AF.A, CPU.HL.L.getValue(), false, false, false);
    }

    @Opcode(value = 0x87, length = 1, cycles = 2)
    public static void add_a_a() {
        add_register(CPU.AF.A, CPU.AF.A.getValue(), false, false, false);
    }

    @Opcode(value = 0x88, length = 1, cycles = 2)
    public static void adc_a_b() {
        add_register(CPU.AF.A, CPU.BC.B.getValue(), true, false, false);
    }

    @Opcode(value = 0x89, length = 1, cycles = 2)
    public static void adc_a_c() {
        add_register(CPU.AF.A, CPU.BC.C.getValue(), true, false, false);
    }

    @Opcode(value = 0x8A, length = 1, cycles = 2)
    public static void adc_a_d() {
        add_register(CPU.AF.A, CPU.DE.D.getValue(), true, false, false);
    }

    @Opcode(value = 0x8B, length = 1, cycles = 2)
    public static void adc_a_e() {
        add_register(CPU.AF.A, CPU.DE.E.getValue(), true, false, false);
    }

    @Opcode(value = 0x8C, length = 1, cycles = 2)
    public static void adc_a_h() {
        add_register(CPU.AF.A, CPU.HL.H.getValue(), true, false, false);
    }

    @Opcode(value = 0x8D, length = 1, cycles = 2)
    public static void adc_a_l() {
        add_register(CPU.AF.A, CPU.HL.L.getValue(), true, false, false);
    }

    @Opcode(value = 0x8E, length = 1, cycles = 2)
    public static void adc_a_from_hl() {
        byte HL_value = CPU.memory.read_byte(CPU.HL.getValue());
        add_register(CPU.AF.A, HL_value, true, false, false);
    }

    @Opcode(value = 0x8F, length = 1, cycles = 2)
    public static void adc_a_a() {
        add_register(CPU.AF.A, CPU.AF.A.getValue(), true, false, false);
    }

    @Opcode(value = 0xC6, length = 2, cycles = 2)
    public static void add_a_d8() {
        add_register(CPU.AF.A, CPU.memory.read_byte(CPU.PC.getValue() + 1)&0xFF, false, false, false);
    }

    @Opcode(value = 0xCE, length = 2, cycles = 2)
    public static void adc_a_d8() {
        add_register(CPU.AF.A, CPU.memory.read_byte(CPU.PC.getValue() + 1) & 0xFF, true, false, false);
    }


    @Opcode(value = 0xD6, length = 2, cycles = 2)
    public static void sub_d8() {
        add_register(CPU.AF.A, CPU.memory.read_byte(CPU.PC.getValue() + 1) & 0xFF, false, true, false);
    }

    @Opcode(value = 0xF8, length = 2, cycles = 3)
    public static void ld_hl_sp_plus_s8() {
        CPU.HL.setValue(CPU.SP.getValue());
        add_register(CPU.HL, CPU.memory.read_byte(CPU.PC.getValue() + 1), false, false, false, true);
        CPU.turnOffFlags(Flags.ZERO);
    }
}
