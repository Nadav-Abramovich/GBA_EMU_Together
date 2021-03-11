package gameboy.cpu_instructions;

import gameboy.CPU;

// We suppress this warning because this class and its methods are dynamically imported
// and therefore IntelliJ doesn't recognize their usage.
@SuppressWarnings("unused")
public class Load implements CPUInstructions {
    @Opcode(value = 0x0E, length = 2)
    public static void ld_c_d8(CPU cpu) {
        cpu.BC.C.setValue(cpu.memory[cpu.PC.getValue() + 1]);
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

    @Opcode(value = 0xE2, length = 1)
    public static void ld_pointer_c_a(CPU cpu) {
        cpu.memory[0xFF00|cpu.BC.C.getValue()] = (byte)(cpu.AF.A.getValue());
    }
}
