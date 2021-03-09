package gameboy.cpu_instructions;

import gameboy.CPU;


// We suppress this warning because this class and its methods are dynamically imported
// and therefore IntelliJ doesn't recognize their usage.
@SuppressWarnings("unused")
public class Increments implements CPUInstructions {
    @Opcode(value = 0x0C, length = 1)
    //TODO : Half carry & subtraction
    public static void inc_c(CPU cpu) {
        byte C = (byte) (cpu.BC & 255);
        C += 1;
        byte B = (byte) ((cpu.BC >> 8) << 8);
        cpu.BC = (char) (B | C);
        if(C == 0){
            cpu.AF |= 1 <<7;
        }
    }
}
