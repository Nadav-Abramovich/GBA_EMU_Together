package gameboy.cpu_instructions;

import gameboy.CPU;

// We suppress this warning because this class and its methods are dynamically called
// and therefore IntelliJ doesn't recognize their usage.
@SuppressWarnings("unused")
public class ExtendedInstructions implements CPUInstructions {
    @Opcode(value = 0xCB7C, length = 2)
    public static void bit7H(CPU cpu) {
        int seventhBit;
        int H = (cpu.HL >> 15);
        if (H == 0) {
            H = 1;
        } else {
            H = 0;
        }

        seventhBit = (char) (H << 7);
        if (H == 1) {
            cpu.AF |= seventhBit;
        } else {
            // TODO: Maybe this doesnt work?
            // Looks fine to me
            cpu.AF &= (char) (~H);
        }
        cpu.AF &= (char)(~(1<<6));
        cpu.AF |= 1<<5;
    }
}