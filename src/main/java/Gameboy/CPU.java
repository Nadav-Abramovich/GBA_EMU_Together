package Gameboy;

import Gameboy.CPUActions.CPUActions;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.Set;

public class CPU {
    public byte[] memory;
    public char AF = 0;
    public char BC = 0;
    public char DE = 0;
    public char HL = 0;
    public char SP = 0;
    public char PC = 0;
    public LinkedList<CPUActions> supported_actions = new LinkedList<>();

    public CPU(byte[] memory) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        this.memory = memory;

        // Load all inheriting classes of CPUActions and add them to our list :)
        Reflections reflections = new Reflections("Gameboy.CPUActions");
        Set<Class<? extends CPUActions>> classes = reflections.getSubTypesOf(CPUActions.class);
        for (Class<? extends CPUActions> aClass : classes) {
            supported_actions.add(aClass.getDeclaredConstructor(CPU.class).newInstance(this));
        }
    }

    public void xor_flags() {
        AF = (char) ((AF >> 8) << 8);
    }

    public void turn_on_zero_flag() {
        AF |= 128;
    }

    public void tick() {
        char opcode = (char) (memory[PC] & 255);
        boolean executed_opcode = false;
        for (CPUActions action : supported_actions) {
            Runnable function = action.get_supported_actions().getOrDefault(opcode, null);
            if (function != null) {
                function.run();
                executed_opcode = true;
                break;
            }
        }
        if (!executed_opcode) {
            System.out.println("Failed to execute " + (int) opcode);
            int t = 1 / 0;
        }
    }
}
