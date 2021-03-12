package gameboy.cpu_instructions;

import gameboy.CPU;
import gameboy.Flags;

// We suppress this warning because this class and its methods are dynamically imported
// and therefore IntelliJ doesn't recognize their usage.
@SuppressWarnings("unused")
public class Decrements implements CPUInstructions {
    @Opcode(value = 0x05, length = 1)
    public static void dec_b(CPU cpu) {
        char current_value = cpu.BC.B.getValue();
        if(current_value == 0) {
            cpu.turnOnFlags(Flags.HALF_CARRY);
        } else {
            cpu.turnOffFlags(Flags.HALF_CARRY);
        }

        current_value -= 1;
        cpu.BC.B.setValue((byte)current_value);
        if(current_value == 0){
            cpu.turnOnFlags(Flags.ZERO);
        } else {
            cpu.turnOffFlags(Flags.ZERO);
        }
        cpu.turnOffFlags(Flags.SUBTRACTION);
    }

    @Opcode(value = 0x0D, length = 1)
    public static void dec_c(CPU cpu) {
        char current_value = cpu.BC.C.getValue();
        if(current_value == 0) {
            cpu.turnOnFlags(Flags.HALF_CARRY);
        } else {
            cpu.turnOffFlags(Flags.HALF_CARRY);
        }

        current_value -= 1;
        cpu.BC.C.setValue((byte)current_value);
        if(current_value == 0){
            cpu.turnOnFlags(Flags.ZERO);
        } else {
            cpu.turnOffFlags(Flags.ZERO);
        }
        cpu.turnOffFlags(Flags.SUBTRACTION);
    }
    @Opcode(value = 0x15, length = 1)
    public static void dec_d(CPU cpu)    {
        char current_value = cpu.DE.D.getValue();
        if (current_value == 0) {
            cpu.turnOnFlags(Flags.HALF_CARRY);
        } else {
            cpu.turnOffFlags(Flags.HALF_CARRY);
        }
    }
    @Opcode(value = 0x1D, length = 1)
    public static void dec_e(CPU cpu) {
        char current_value = cpu.DE.E.getValue();
        if(current_value == 0) {
            cpu.turnOnFlags(Flags.HALF_CARRY);
        } else {
            cpu.turnOffFlags(Flags.HALF_CARRY);
        }

        current_value -= 1;
        cpu.DE.E.setValue((byte)current_value);
        if(current_value == 0){
            cpu.turnOnFlags(Flags.ZERO);
        } else {
            cpu.turnOffFlags(Flags.ZERO);
        }
        cpu.turnOffFlags(Flags.SUBTRACTION);
    }

    @Opcode(value = 0x3D, length = 1)
    public static void dec_a(CPU cpu) {
        char current_value = cpu.AF.A.getValue();
        if(current_value == 0) {
            cpu.turnOnFlags(Flags.HALF_CARRY);
        } else {
            cpu.turnOffFlags(Flags.HALF_CARRY);
        }

        current_value -= 1;
        cpu.AF.A.setValue((byte)current_value);
        if(current_value == 0){
            cpu.turnOnFlags(Flags.ZERO);
        } else {
            cpu.turnOffFlags(Flags.ZERO);
        }
        cpu.turnOffFlags(Flags.SUBTRACTION);
    }
}