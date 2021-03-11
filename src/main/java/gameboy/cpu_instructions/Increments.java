package gameboy.cpu_instructions;

import gameboy.CPU;
import gameboy.Flags;

// We suppress this warning because this class and its methods are dynamically imported
// and therefore IntelliJ doesn't recognize their usage.
@SuppressWarnings("unused")
public class Increments implements CPUInstructions {
    @Opcode(value = 0x0C, length = 1)
    //TODO : Half carry & subtraction
    public static void inc_c(CPU cpu) {
        char current_value = cpu.BC.C.getValue();
        current_value += 1;
        cpu.BC.C.setValue((byte)current_value);
        if(current_value == 0){
            cpu.turnOnFlags(Flags.CARRY);
        }
    }
}
