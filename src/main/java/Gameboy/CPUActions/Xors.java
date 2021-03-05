package Gameboy.CPUActions;

import Gameboy.CPU;

import java.util.Map;

// We suppress this warning because it is loaded dynamically
// and therefore Intellij doesn't recognize its usage.
@SuppressWarnings("unused")
public class Xors implements CPUActions {
    private final CPU cpu;

    private final Map<Character, Runnable> SUPPORTED_ACTIONS = Map.ofEntries(
        Map.entry((char) 0xAF, this::xorA),
        Map.entry((char) 0xA8, this::xorB),
        Map.entry((char) 0xA9, this::xorC),
        Map.entry((char) 0xAA, this::xorD),
        Map.entry((char) 0xAB, this::xorE),
        Map.entry((char) 0xAC, this::xorH),
        Map.entry((char) 0xAD, this::xorL)
    );

    // NOTE: This constructor is used by a dynamic factory and therefore intellij doesn't recognize its usage
    public Xors(CPU cpu) {
        this.cpu = cpu;
    }

    public Map<Character, Runnable> get_supported_actions() {
        return SUPPORTED_ACTIONS;
    }

    private void xorA() {
        System.out.println("XorA");
        cpu.AF = (char) 0;
        cpu.PC++;
        cpu.AF |= 128;
    }

    private void xorB() {
        System.out.println("XorB");
        cpu.BC = (char) (255 & cpu.BC);
        cpu.PC++;

        // Make the ZERO flag the only turned on flag
        cpu.xor_flags();
        cpu.turn_on_zero_flag();
    }


    private void xorC() {
        System.out.println("XorC");
        cpu.BC = (char) ((cpu.BC >> 8) << 8);
        cpu.PC++;

        // Make the ZERO flag the only turned on flag
        cpu.xor_flags();
        cpu.turn_on_zero_flag();
    }

    private void xorD() {
        System.out.println("XorD");
        cpu.DE = (char) (255 & cpu.DE);
        cpu.PC++;

        // Make the ZERO flag the only turned on flag
        cpu.xor_flags();
        cpu.turn_on_zero_flag();
    }

    private void xorE() {
        System.out.println("XorE");
        cpu.DE = (char) ((cpu.BC >> 8) << 8);
        cpu.PC++;

        // Make the ZERO flag the only turned on flag
        cpu.xor_flags();
        cpu.turn_on_zero_flag();
    }

    private void xorH() {
        System.out.println("XorH");
        cpu.HL = (char) (255 & cpu.HL);
        cpu.PC++;

        // Make the ZERO flag the only turned on flag
        cpu.xor_flags();
        cpu.turn_on_zero_flag();
    }

    private void xorL() {
        System.out.println("XorL");
        cpu.HL = (char) ((cpu.BC >> 8) << 8);
        cpu.PC++;

        // Make the ZERO flag the only turned on flag
        cpu.xor_flags();
        cpu.turn_on_zero_flag();
    }
}
