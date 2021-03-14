package gameboy;

public class HelperFunctions {
    public static void push_to_stack_d16(CPU cpu, char value) {
        cpu.memory.write(cpu.SP.getValue() - 1, (byte) ((value >> 8) & 255));
        cpu.memory.write(cpu.SP.getValue() - 2, (byte) (value & 255));
        cpu.SP.increment(-2);
    }

    public static char pop_from_stack_d16(CPU cpu) {
        char lower = (char) (cpu.memory.read_byte(cpu.SP.getValue()) & 255);
        char higher = (char) (cpu.memory.read_byte(cpu.SP.getValue() + 1) & 255);
        cpu.SP.increment(2);
        return (char) (lower | (higher << 8));
    }
}
