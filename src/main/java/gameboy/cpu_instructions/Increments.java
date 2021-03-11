package gameboy.cpu_instructions;

import gameboy.CPU;
import gameboy.Flags;

// We suppress this warning because this class and its methods are dynamically imported
// and therefore IntelliJ doesn't recognize their usage.
@SuppressWarnings("unused")
public class Increments implements CPUInstructions {
    @Opcode(value = 0x04, length = 1)
    //TODO : Half carry
    public static void inc_b(CPU cpu) {
        char current_value = cpu.BC.B.getValue();
        current_value += 1;
        cpu.BC.B.setValue((byte)current_value);
        if(current_value == 0){
            cpu.turnOnFlags(Flags.ZERO);
        }
        cpu.turnOffFlags(Flags.SUBTRACTION);
    }


    @Opcode(value = 0x0C, length = 1)
    //TODO : Half carry
    public static void inc_c(CPU cpu) {
        char current_value = cpu.BC.C.getValue();
        current_value += 1;
        cpu.BC.C.setValue((byte)current_value);
        if(current_value == 0){
            cpu.turnOnFlags(Flags.ZERO);
        }
        cpu.turnOffFlags(Flags.SUBTRACTION);
    }

    @Opcode(value = 0x13, length = 1)
    public static void inc_de(CPU cpu) {
        cpu.DE.increment(1);
    }

    @Opcode(value = 0x23, length = 1)
    public static void inc_hl(CPU cpu) {
        cpu.HL.increment(1);
    }

    @Opcode(value = 0x24, length = 1)
    //TODO : Half carry
    public static void inc_h(CPU cpu) {
        char current_value = cpu.HL.H.getValue();
        current_value += 1;
        cpu.HL.H.setValue((byte)current_value);
        if(current_value == 0){
            cpu.turnOnFlags(Flags.ZERO);
        }
        cpu.turnOffFlags(Flags.SUBTRACTION);
    }
}
