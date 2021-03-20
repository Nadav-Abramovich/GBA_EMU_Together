package gameboy.cpu_instructions;

import gameboy.CPU;
import gameboy.Flags;

import static gameboy.HelperFunctions.add_register;

// We suppress this warning because this class and its methods are dynamically imported
// and therefore IntelliJ doesn't recognize their usage.
@SuppressWarnings("unused")
public class Adds implements CPUInstructions {
    @Opcode(value = 0x09, length = 1, cycles = 2)
    public static void add_hl_bc(CPU cpu) {
        add_register(cpu, cpu.HL, cpu.BC.getValue(), false, false);
    }

    @Opcode(value = 0x19, length = 1, cycles = 2)
    public static void add_hl_de(CPU cpu) {
        add_register(cpu, cpu.HL, cpu.DE.getValue(), false, false);
    }
}
