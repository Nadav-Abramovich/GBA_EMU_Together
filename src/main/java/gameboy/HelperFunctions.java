package gameboy;

public class HelperFunctions {
    public static void push_to_stack_d16(CPU cpu, char value) {
        cpu.memory[cpu.SP.getValue() - 1] = (byte)(value>>8);
        cpu.memory[cpu.SP.getValue() - 2] = (byte)(value&255);
        cpu.SP.increment(-2);
    }

    public static char pop_from_stack_d16(CPU cpu) {
        byte lower = cpu.memory[cpu.SP.getValue()];
        byte higher = cpu.memory[cpu.SP.getValue() + 1];
        cpu.SP.increment(2);
        return (char)(lower | (higher << 8));
    }
}
