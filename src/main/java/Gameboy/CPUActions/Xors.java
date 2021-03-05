package Gameboy.CPUActions;

import Gameboy.CPU;

import java.util.HashMap;
import java.util.Map;

public class Xors extends CPUActions {
    private final CPU cpu_reference;

    private final Map<Character, Runnable> SUPPORTED_ACTIONS = new HashMap<>() {
        {
            put((char) 0xAF, this::xorA);
            put((char) 0xA8, this::xorB);
            put((char) 0xA9, this::xorC);
            put((char) 0xAA, this::xorD);
            put((char) 0xAB, this::xorE);
            put((char) 0xAC, this::xorH);
            put((char) 0xAD, this::xorL);
        }

        private void xorA() {
            System.out.println("XorA");
            cpu_reference.AF = (char) 0;
            cpu_reference.PC++;
            cpu_reference.AF |= 128;
        }

        private void xorB() {
            System.out.println("XorB");
            cpu_reference.BC = (char) (255 & cpu_reference.BC);
            cpu_reference.PC++;

            // Make the ZERO flag the only turned on flag
            cpu_reference.xor_flags();
            cpu_reference.turn_on_zero_flag();
        }


        private void xorC() {
            System.out.println("XorC");
            cpu_reference.BC = (char) ((cpu_reference.BC >> 8) << 8);
            cpu_reference.PC++;

            // Make the ZERO flag the only turned on flag
            cpu_reference.xor_flags();
            cpu_reference.turn_on_zero_flag();
        }

        private void xorD() {
            System.out.println("XorD");
            cpu_reference.DE = (char) (255 & cpu_reference.DE);
            cpu_reference.PC++;

            // Make the ZERO flag the only turned on flag
            cpu_reference.xor_flags();
            cpu_reference.turn_on_zero_flag();
        }

        private void xorE() {
            System.out.println("XorE");
            cpu_reference.DE = (char) ((cpu_reference.BC >> 8) << 8);
            cpu_reference.PC++;

            // Make the ZERO flag the only turned on flag
            cpu_reference.xor_flags();
            cpu_reference.turn_on_zero_flag();
        }

        private void xorH() {
            System.out.println("XorH");
            cpu_reference.HL = (char) (255 & cpu_reference.HL);
            cpu_reference.PC++;

            // Make the ZERO flag the only turned on flag
            cpu_reference.xor_flags();
            cpu_reference.turn_on_zero_flag();
        }

        private void xorL() {
            System.out.println("XorL");
            cpu_reference.HL = (char) ((cpu_reference.BC >> 8) << 8);
            cpu_reference.PC++;

            // Make the ZERO flag the only turned on flag
            cpu_reference.xor_flags();
            cpu_reference.turn_on_zero_flag();
        }
    };

    // NOTE: This constructor is used by a dynamic factory and therefore intellij doesn't recognize its usage
    public Xors(CPU cpu) {
        cpu_reference = cpu;
    }

    public Map<Character, Runnable> get_supported_actions() { return SUPPORTED_ACTIONS; }
}
