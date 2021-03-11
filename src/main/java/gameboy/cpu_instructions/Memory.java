package gameboy.cpu_instructions;

import gameboy.CPU;
import gameboy.Flags;

// We suppress this warning because this class and its methods are dynamically called
// and therefore IntelliJ doesn't recognize their usage.
@SuppressWarnings("unused")
public class Memory implements CPUInstructions {
    @Opcode(value = 0x01, length = 3)
    public static void ld_bc_d16(CPU cpu) {
        byte lowerByte = cpu.memory[cpu.PC.getValue() + 1];
        byte higherByte = cpu.memory[cpu.PC.getValue() + 2];
        cpu.BC.C.setValue(lowerByte);
        cpu.BC.B.setValue(higherByte);
    }


    @Opcode(value = 0x11, length = 3)
    public static void ld_de_d16(CPU cpu) {
        byte lowerByte = cpu.memory[cpu.PC.getValue() + 1];
        byte higherByte = cpu.memory[cpu.PC.getValue() + 2];
        cpu.DE.E.setValue(lowerByte);
        cpu.DE.D.setValue(higherByte);
    }

    @Opcode(value = 0x1A, length = 1)
    public static void ld_a_from_de(CPU cpu) {
        cpu.AF.A.setValue(cpu.memory[cpu.DE.getValue()]);
    }

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

    @Opcode(value = 0x31, length = 3)
    public static void ld_sp_d16(CPU cpu) {
        byte lowerByte = cpu.memory[cpu.PC.getValue() + 1];
        byte higherByte = cpu.memory[cpu.PC.getValue() + 2];
        cpu.SP.P.setValue(lowerByte);
        cpu.SP.S.setValue(higherByte);
    }

    @Opcode(value = 0x32, length = 1)
    public static void ld_hl_minus_a(CPU cpu) {
        cpu.memory[cpu.HL.getValue()] = ((byte)cpu.AF.A.getValue());
        cpu.HL.setValue((char) (cpu.HL.getValue() - 1));
    }

    // TODO: Underflow...
    @Opcode(value = 0x86, length = 1)
    public static void add_a_from_hl(CPU cpu) {
        cpu.AF.A.setValue((byte)(cpu.AF.A.getValue() + cpu.memory[cpu.HL.getValue()]));
        if(cpu.AF.A.getValue() == 0) {
            cpu.turnOnFlags(Flags.ZERO);
        } else {
            cpu.turnOffFlags(Flags.ZERO);
        }

        cpu.turnOffFlags(Flags.SUBTRACTION);
    }

    @Opcode(value = 0xE0, length = 2)
    public static void ld_into_a8_a(CPU cpu) {
        byte target_address = cpu.memory[cpu.PC.getValue() + 1];
        cpu.memory[0xFF00 | target_address] = (byte)cpu.AF.A.getValue();
    }
}
