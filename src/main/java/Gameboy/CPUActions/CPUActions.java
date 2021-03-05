package Gameboy.CPUActions;

import java.util.Map;

public interface CPUActions {
    final Map<Character, Runnable> SUPPORTED_ACTIONS = null;

    public abstract Map<Character, Runnable> get_supported_actions();
}
