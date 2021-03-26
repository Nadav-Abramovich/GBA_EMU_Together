package gameboy.cpu_instructions;

import gameboy.CPU;
import gameboy.Flags;

import static gameboy.HelperFunctions.add_register;

// We suppress this warning because this class and its methods are dynamically imported
// and therefore IntelliJ doesn't recognize their usage.
@SuppressWarnings("unused")
public class Adds implements CPUInstructions {
    @Opcode(value = 0x09, length = 1, cycles = 2)
    public static void add_hl_bc() {
        add_register(CPU.HL, CPU.BC.getValue(), false, false);
    }

    @Opcode(value = 0x19, length = 1, cycles = 2)
    public static void add_hl_de() {
        add_register(CPU.HL, CPU.DE.getValue(), false, false);
    }

    @Opcode(value = 0x29, length = 1, cycles = 2)
    public static void add_hl_hl() {
        add_register(CPU.HL, CPU.HL.getValue(), false, false);
    }

    @Opcode(value = 0x39, length = 1, cycles = 2)
    public static void add_hl_sp() {
        add_register(CPU.HL, CPU.SP.getValue(), false, false);
    }

    @Opcode(value = 0xE8, length = 2, cycles = 4)
    public static void add_sp_s8() {
        add_register(CPU.SP, CPU.memory.read_byte(CPU.PC.getValue() + 1), false, false);
    }
}
