package gameboy.cpu_instructions;

import gameboy.CPU;
import gameboy.Flags;

import static gameboy.HelperFunctions.add_register;

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
        cpu.memory.write(cpu.HL.getValue(), (byte) (cpu.AF.A.getValue()));
        cpu.HL.increment(1);
    }

    @Opcode(value = 0x2A, length = 1, cycles = 2)
    public static void ld_a_hl_plus(CPU cpu) {
        cpu.AF.A.setValue(cpu.memory.read_byte(cpu.HL.getValue()));
        cpu.HL.increment(1);
    }

    @Opcode(value = 0x32, length = 1, cycles = 2)
    public static void ld_hl_minus_a(CPU cpu) {
        cpu.memory.write(cpu.HL.getValue(), (byte) cpu.AF.A.getValue());
        cpu.HL.increment(-1);
    }

    @Opcode(value = 0x3A, length = 1, cycles = 2)
    public static void ld_a_hl_minus(CPU cpu) {
        cpu.AF.A.setValue(cpu.memory.read_byte(cpu.HL.getValue()));
        cpu.HL.increment(-1);
    }

    @Opcode(value = 0x7E, length = 1, cycles = 2)
    public static void ld_a_from_hl(CPU cpu) {
        cpu.AF.A.setValue(cpu.memory.read_byte(cpu.HL.getValue()));
    }

    @Opcode(value = 0x86, length = 1, cycles = 2)
    public static void add_a_from_hl(CPU cpu) {
        char value = (char) cpu.memory.read_byte(cpu.HL.getValue());
        add_register(cpu, cpu.AF.A, value, false, false);
    }

    @Opcode(value = 0x80, length = 1, cycles = 1)
    public static void add_a_b(CPU cpu) {
        add_register(cpu, cpu.AF.A, cpu.BC.B.getValue(), false, false);
    }

    @Opcode(value = 0x81, length = 1, cycles = 1)
    public static void add_a_c(CPU cpu) {
        add_register(cpu, cpu.AF.A, cpu.BC.C.getValue(), false, false);
    }

    @Opcode(value = 0x82, length = 1, cycles = 1)
    public static void add_a_d(CPU cpu) {
        add_register(cpu, cpu.AF.A, cpu.DE.E.getValue(), false, false);
    }

    @Opcode(value = 0x83, length = 1, cycles = 1)
    public static void add_a_e(CPU cpu) {
        add_register(cpu, cpu.AF.A, cpu.DE.E.getValue(), false, false);
    }

    @Opcode(value = 0x85, length = 1, cycles = 2)
    public static void add_a_l(CPU cpu) {
        add_register(cpu, cpu.AF.A, cpu.HL.L.getValue(), false, false);
    }

    @Opcode(value = 0x87, length = 1, cycles = 2)
    public static void add_a_a(CPU cpu) {
        add_register(cpu, cpu.AF.A, cpu.AF.A.getValue(), false, false);
    }

    @Opcode(value = 0x89, length = 1, cycles = 2)
    public static void adc_a_c(CPU cpu) {
        add_register(cpu, cpu.AF.A, cpu.BC.C.getValue(), true, false);
    }

    @Opcode(value = 0x8E, length = 1, cycles = 2)
    public static void adc_a_from_hl(CPU cpu) {
        byte HL_value = cpu.memory.read_byte(cpu.HL.getValue());
        add_register(cpu, cpu.AF.A, HL_value, true, false);
    }

    @Opcode(value = 0xC6, length = 2, cycles = 2)
    public static void add_a_d8(CPU cpu) {
        int d8_value = cpu.memory.read_byte(cpu.PC.getValue() + 1);
        add_register(cpu, cpu.AF.A, d8_value, false, false);
    }

    @Opcode(value = 0xD6, length = 2, cycles = 2)
    public static void sub_d8(CPU cpu) {
        int d8_value = cpu.memory.read_byte(cpu.PC.getValue() + 1);
        add_register(cpu, cpu.AF.A, d8_value, false, true);
    }
}
