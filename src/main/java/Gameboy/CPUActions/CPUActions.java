package Gameboy.CPUActions;

import java.util.Map;

// We suppress this warning because it is loaded dynamically
// and therefore Intellij doesn't recognize its usage.
@SuppressWarnings("unused")
public interface CPUActions {
    Map<Character, Runnable> SUPPORTED_ACTIONS = null;

    Map<Character, Runnable> get_supported_actions();
}
