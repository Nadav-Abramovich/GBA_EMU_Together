package Gameboy.CPUActions;

import java.util.Map;

public abstract class CPUActions {
    private final Map<Character, Runnable> SUPPORTED_ACTIONS = null;

    public abstract Map<Character, Runnable> get_supported_actions();
}
