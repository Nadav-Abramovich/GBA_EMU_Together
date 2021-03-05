package Gameboy.CPUActions;

import Gameboy.CPU;

import java.util.Map;

// We suppress this warning because it is loaded dynamically
// and therefore Intellij doesn't recognize its usage.
@SuppressWarnings("unused")
public class Memory implements CPUActions {
    private final CPU cpu;

    private final Map<Character, Runnable> SUPPORTED_ACTIONS = Map.ofEntries(
            Map.entry((char) 0x21, this::ld_hl_d16),
            Map.entry((char) 0x22, this::ld_hl_plus_a),
            Map.entry((char) 0x32, this::ld_hl_minus_a)
    );

    public Memory(CPU cpu) {
        this.cpu = cpu;
    }

    public Map<Character, Runnable> get_supported_actions() {
        return SUPPORTED_ACTIONS;
    }

    private void ld_hl_d16() {
        System.out.println("_ld_hl_d16");
        int lowerByte = cpu.memory[cpu.PC + 1] & 255;
        int upperByte = cpu.memory[cpu.PC + 2] & 255;
        cpu.HL = (char) (upperByte << 8 | lowerByte);
        cpu.PC += 3;
    }

    private void ld_hl_plus_a() {
        System.out.println("_ld_hl_plus_a");

        byte A = (byte) (cpu.AF >> 8);

        cpu.memory[cpu.HL] = A;
        cpu.HL++;
        cpu.PC++;
    }

    private void ld_hl_minus_a() {
        System.out.println("_ld_hl_minus_a");

        byte A = (byte) (cpu.AF >> 8);

        cpu.memory[cpu.HL] = A;
        cpu.HL--;
        cpu.PC++;
    }

}
