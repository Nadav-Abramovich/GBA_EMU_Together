package gameboy;

import gameboy.cpu_instructions.CPUInstructions;
import gameboy.cpu_instructions.Opcode;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CPU {
    public final byte[] memory;
    public char AF = 0;
    public char BC = 0;
    public char DE = 0;
    public char HL = 0;
    public char SP = 0;
    public char PC = 0;
    public final Map<Character, Method> supported_actions = new HashMap<>();

    public CPU(byte[] memory) {
        this.memory = memory;

        // Load all the opcode handling methods of classes implementing CPUInstructions and add them to our list
        Reflections reflections = new Reflections("gameboy.cpu_actions");
        Set<Class<? extends CPUInstructions>> classes = reflections.getSubTypesOf(CPUInstructions.class);
        for (Class<? extends CPUInstructions> aClass : classes) {
            Method[] functions = aClass.getDeclaredMethods();
            for (Method method : functions) {
                Opcode annotation = method.getAnnotation(Opcode.class);
                supported_actions.put(annotation.opcode(), method);
            }
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
        Method action = supported_actions.getOrDefault(opcode, null);
        if (action != null) {
            try {
                System.out.println("[Debug] 0x" + Integer.toHexString(opcode).toUpperCase() + ": " + action.getName());
                action.invoke(null, this);
            } catch (IllegalAccessException | InvocationTargetException e) {
                System.out.println("Failed to execute " + (int) opcode);
                System.exit(1);
            }
        } else {
            System.out.println("Failed to execute " + (int) opcode);
            System.exit(1);
        }
    }
}
