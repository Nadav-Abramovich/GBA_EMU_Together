package Gameboy.CPUActions;

import Gameboy.CPU;

import java.util.HashMap;
import java.util.Map;

public class Memory extends CPUActions {
    private final CPU cpu_reference;

    private final Map<Character, Runnable> SUPPORTED_ACTIONS = new HashMap<>() {
        {
            put((char) 0x21, this::ld_hl_d16);
            put((char) 0x22, this::ld_hl_plus_a);
            put((char) 0x32, this::ld_hl_minus_a);
        }

        private void ld_hl_d16() {
            System.out.println("_ld_hl_d16");
            int lowerByte = cpu_reference.memory[cpu_reference.PC + 1] & 255;
            int upperByte = cpu_reference.memory[cpu_reference.PC + 2] & 255;
            cpu_reference.HL = (char) (upperByte << 8 | lowerByte);
            cpu_reference.PC += 3;
        }

        private void ld_hl_plus_a() {
            System.out.println("_ld_hl_plus_a");

            byte A = (byte) (cpu_reference.AF >> 8);

            cpu_reference.memory[cpu_reference.HL] = A;
            cpu_reference.HL++;
            cpu_reference.PC++;
        }

        private void ld_hl_minus_a() {
            System.out.println("_ld_hl_minus_a");

            byte A = (byte) (cpu_reference.AF >> 8);

            cpu_reference.memory[cpu_reference.HL] = A;
            cpu_reference.HL--;
            cpu_reference.PC++;
        }
    };

    // NOTE: This constructor is used by a dynamic factory and therefore intellij doesn't recognize its usage
    public Memory(CPU cpu) {
        cpu_reference = cpu;
    }

    public Map<Character, Runnable> get_supported_actions() { return SUPPORTED_ACTIONS; }
}
