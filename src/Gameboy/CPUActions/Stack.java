package Gameboy.CPUActions;

import Gameboy.CPU;

import java.util.HashMap;
import java.util.Map;

public class Stack extends CPUActions {
    private static CPU cpu_reference = null;

    public static Map<Character, Runnable> SUPPORTED_ACTIONS = new HashMap<>() {
        {
            put((char) 0x31, Stack::ld_sp_d16);
        }
    };

    public Map<Character, Runnable> get_supported_actions() {
        return SUPPORTED_ACTIONS;
    }

    public Stack(CPU cpu) {
        cpu_reference = cpu;
    }


    public static void ld_sp_d16() {
        System.out.println("_ld_sp_d16");
        int lowerByte = cpu_reference.memory[cpu_reference.PC + 1] & 255;
        int upperByte = cpu_reference.memory[cpu_reference.PC + 2] & 255;
        cpu_reference.SP = (char) (upperByte << 8 | lowerByte);
        cpu_reference.PC += 3;
    }
}
