package Gameboy.CPUActions;

import Gameboy.CPU;

import java.util.Map;

public class Stack implements CPUActions {
    private final CPU cpu;

    private final Map<Character, Runnable> SUPPORTED_ACTIONS = Map.ofEntries(
            Map.entry((char) 0x31, this::ld_sp_d16)
    );

    // NOTE: This constructor is used by a dynamic factory and therefore intellij doesn't recognize its usage
    public Stack(CPU cpu) {
        this.cpu = cpu;
    }

    public Map<Character, Runnable> get_supported_actions() {
        return SUPPORTED_ACTIONS;
    }

    private void ld_sp_d16() {
        System.out.println("_ld_sp_d16");
        int lowerByte = cpu.memory[cpu.PC + 1] & 255;
        int upperByte = cpu.memory[cpu.PC + 2] & 255;
        cpu.SP = (char) (upperByte << 8 | lowerByte);
        cpu.PC += 3;
    }
}
