package Gameboy.CPUActions;

import Gameboy.CPU;

import java.util.HashMap;
import java.util.Map;

public class xors extends CPUActions {
    public static CPU cpu_reference = null;

    public static Map<Character, Runnable> SUPPORTED_ACTIONS = new HashMap<>() {
        {
            put((char) 0xAF, xors::xorA);
            put((char) 0xA8, xors::xorB);
            put((char) 0xA9, xors::xorC);
            put((char) 0xAA, xors::xorD);
            put((char) 0xAB, xors::xorE);
            put((char) 0xAC, xors::xorH);
            put((char) 0xAD, xors::xorL);
        }
    };


    public static void xorA() {
        System.out.println("XorA");
        cpu_reference.AF = (char) 0;
        cpu_reference.PC++;
        cpu_reference.AF |= 128;
    }

    public static void xorB() {
        System.out.println("XorB");
        cpu_reference.BC = (char) (255 & cpu_reference.BC);
        cpu_reference.PC++;

        // Make the ZERO flag the only turned on flag
        cpu_reference.xor_flags();
        cpu_reference.turn_on_zero_flag();
    }


    public static void xorC() {
        System.out.println("XorC");
        cpu_reference.BC = (char) ((cpu_reference.BC >> 8) << 8);
        cpu_reference.PC++;

        // Make the ZERO flag the only turned on flag
        cpu_reference.xor_flags();
        cpu_reference.turn_on_zero_flag();
    }

    public static void xorD() {
        System.out.println("XorD");
        cpu_reference.DE = (char) (255 & cpu_reference.DE);
        cpu_reference.PC++;

        // Make the ZERO flag the only turned on flag
        cpu_reference.xor_flags();
        cpu_reference.turn_on_zero_flag();
    }

    public static void xorE() {
        System.out.println("XorE");
        cpu_reference.DE = (char) ((cpu_reference.BC >> 8) << 8);
        cpu_reference.PC++;

        // Make the ZERO flag the only turned on flag
        cpu_reference.xor_flags();
        cpu_reference.turn_on_zero_flag();
    }

    public static void xorH() {
        System.out.println("XorH");
        cpu_reference.HL = (char) (255 & cpu_reference.HL);
        cpu_reference.PC++;

        // Make the ZERO flag the only turned on flag
        cpu_reference.xor_flags();
        cpu_reference.turn_on_zero_flag();
    }

    public static void xorL() {
        System.out.println("XorL");
        cpu_reference.HL = (char) ((cpu_reference.BC >> 8) << 8);
        cpu_reference.PC++;

        // Make the ZERO flag the only turned on flag
        cpu_reference.xor_flags();
        cpu_reference.turn_on_zero_flag();
    }
}
