package Gameboy.CPUActions;

import Gameboy.CPU;

import java.util.Map;

public abstract class CPUActions {
    public static Map<Character, Runnable> SUPPORTED_ACTIONS;

    public abstract Map<Character, Runnable> get_supported_actions();

    public CPUActions(CPU cpu) {
    }
}
