package gameboy.cpu_instructions;

import gameboy.CPU;

// We suppress this warning because this class and its methods are dynamically called
// and therefore IntelliJ doesn't recognize their usage.
@SuppressWarnings("unused")
public class Memory implements CPUInstructions {
    @Opcode(value = 0x21, length = 3)
    public static void ld_hl_d16(CPU cpu) {
        byte lowerByte = cpu.memory[cpu.PC.getValue() + 1];
        byte higherByte = cpu.memory[cpu.PC.getValue() + 2];
        cpu.HL.L.setValue(lowerByte);
        cpu.HL.H.setValue(higherByte);
    }

    @Opcode(value = 0x22, length = 1)
    public static void ld_hl_plus_a(CPU cpu) {
        cpu.memory[cpu.HL.getValue()] = (byte)(cpu.AF.A.getValue());
        cpu.HL.setValue((char) (cpu.HL.getValue() + 1));
    }

    @Opcode(value = 0x32, length = 1)
    public static void ld_hl_minus_a(CPU cpu) {
        cpu.memory[cpu.HL.getValue()] = ((byte)cpu.AF.A.getValue());
        cpu.HL.setValue((char) (cpu.HL.getValue() - 1));
    }
}
