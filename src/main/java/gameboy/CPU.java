package gameboy;

import gameboy.cpu_instructions.CPUInstructions;
import gameboy.cpu_instructions.Opcode;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static gameboy.HelperFunctions.push_to_stack_d16;

public class CPU {
    public boolean PRINT_DEBUG_MESSAGES = false;
    public static final String CPU_INSTRUCTIONS_PACKAGE_PATH = "gameboy.cpu_instructions";
    public static final String EXECUTED_OPCODE_MSG_FORMAT = "[DEBUG] [0x%s] 0x%s: %s%n";
    public static final String FAILED_TO_EXECUTE_OPCODE_MSG_FORMAT = "[CRITICAL] Failed to execute opcode 0x%s%n";
    public static final String OPCODE_NOT_IMPLEMENTED_MSG_FORMAT = "[CRITICAL] Opcode 0x%s is not implemented%n";

    public final Memory memory;
    public gameboy.Registers.AF AF = new gameboy.Registers.AF();
    public gameboy.Registers.BC BC = new gameboy.Registers.BC();
    public gameboy.Registers.DE DE = new gameboy.Registers.DE();
    public gameboy.Registers.HL HL = new gameboy.Registers.HL();
    public gameboy.Registers.SP SP = new gameboy.Registers.SP();
    public gameboy.Registers.PC PC = new gameboy.Registers.PC();
    public final Map<Character, Method> supported_actions = new HashMap<>();
    public boolean IME = false; // Interrupt master enable


    public void setFlags(byte value) {
        AF.F.setValue((byte) (value & 255));
    }

    public void turnOnFlags(byte value) {
        AF.F.setValue((byte) ((AF.F.getValue() | value) & 255));
    }

    public void turnOffFlags(byte value) {
        AF.F.setValue((byte) (AF.F.getValue() & ((~value) & 255)));
    }

    public int cycles = 0;
    public int performed_cycles = 0;
    public final Gameboy game_boy;

    public CPU(Memory memory, Gameboy gameboy) {
        this.game_boy = gameboy;
        this.memory = memory;

        // Load all the opcode handling methods of classes implementing CPUInstructions and add them to our list
        Reflections reflections = new Reflections(CPU_INSTRUCTIONS_PACKAGE_PATH);
        Set<Class<? extends CPUInstructions>> classes = reflections.getSubTypesOf(CPUInstructions.class);
        for (Class<? extends CPUInstructions> aClass : classes) {
            Method[] functions = aClass.getDeclaredMethods();
            for (Method method : functions) {
                Opcode annotation = method.getAnnotation(Opcode.class);
                supported_actions.put(annotation.value(), method);
            }
        }
    }

    private char get_opcode() {
        if (PC.getValue() == 0x100) {
//            if(AF.getValue() != 0x01B0) {
//                System.out.printf("Bad value for AF 0x%s", Integer.toHexString(AF.getValue()));
////                System.exit(1);
//            }
//            if (memory.read_byte(0xFF11) != (byte)0xBF) {
//                System.out.printf("Bad value for [0x%s]:0x%s", Integer.toHexString(0xFF11).toUpperCase(), Integer.toHexString(Byte.toUnsignedInt(memory.read_byte(0xFF11))).toUpperCase());
////                System.exit(1);
//            }
//            System.out.println("Starting ROM");
            PRINT_DEBUG_MESSAGES = false;

            AF.setValue((char) 0x01b0);
            BC.setValue((char) 0x0013);
            DE.setValue((char) 0x00D8);
            HL.setValue((char) 0x014D);
            SP.setValue((char) 0xFFFE);
        }
        if (PC.getValue() >= 0x7FFF) {
            if (PC.getValue() < 0xffb6) {
                System.out.println("BAD");
            }
        }
        char opcode = (char) (memory.read_byte(PC.getValue(), true) & 255);

        // NOTE: This specific instruction (0xCB) means the next instruction joins with
        //       it and they should be treated as a single 2 byte long opcode.
        if (opcode == 0xCB || opcode == 0x10) {
            opcode <<= 8;
            char sub_opcode = (char) (memory.read_byte(PC.getValue() + 1) & 255);
            opcode |= sub_opcode;
        }

        return opcode;
    }


    private void execute_action(Method action, char opcode) {
        try {
            if (PRINT_DEBUG_MESSAGES) {
                System.out.printf(EXECUTED_OPCODE_MSG_FORMAT, Integer.toHexString(PC.getValue()).toUpperCase(), Integer.toHexString(opcode).toUpperCase(), action.getName());
            }

            action.invoke(null, this);
            Opcode opcode_metadata = action.getAnnotation(Opcode.class);
            performed_cycles += opcode_metadata.cycles();
            if (opcode_metadata.should_update_pc()) {
                this.PC.increment(opcode_metadata.length());
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            System.out.printf(FAILED_TO_EXECUTE_OPCODE_MSG_FORMAT, Integer.toHexString(opcode));
            System.exit(1);
        }
    }

    public void tick() {
        // make time tick?
        memory.write(0xFF04, (byte)((memory.read_byte(0xFF04)&255) + 1));
        char opcode = get_opcode();
//         KEYS?
//        memory.write(0xff80, (byte) 0x1);
//        memory.write(0xFF44, (byte)144);
        if (cycles >= performed_cycles) {
            Method action = supported_actions.getOrDefault(opcode, null);

            if (action != null) {
//                //TODO: replace this with working timing..
//                if (PC.getValue() == 0x6D) {
//                    AF.A.setValue((byte) 145);
//                }
//                if(PC.getValue() >= 0x100) {
//                    System.out.printf(EXECUTED_OPCODE_MSG_FORMAT, Integer.toHexString(PC.getValue()).toUpperCase(), Integer.toHexString(opcode).toUpperCase(), action.getName());
//                }
                execute_action(action, opcode);

                // TODO: This is not needed, only to speedup loading
//                if(PC.getValue() == 0x02ef) {
//                    System.out.println(AF.F.getValue());
//                    turnOffFlags(Flags.ZERO);
//                    PC.setValue((char) 0x03ae);
//                }
            } else {
                System.out.printf(OPCODE_NOT_IMPLEMENTED_MSG_FORMAT, Integer.toHexString(opcode));
                System.exit(1);
            }
            if (IME) {
//                System.out.println(memory.read_byte(0xFF40));

                int interrupt_pointer = 0xFFFF;
                byte current_interrupt_requests = memory.read_byte(interrupt_pointer);
                if ((current_interrupt_requests & 1) == 1) {
                    push_to_stack_d16(this, PC.getValue());
                    PC.setValue((char) 0x40);
                    IME = false;
                    memory.write(0xFF0F, (byte) 1);
                    current_interrupt_requests &= 254;
                    memory.write(interrupt_pointer, current_interrupt_requests);
                }

                else if ((current_interrupt_requests & 16) == 16) {
                    push_to_stack_d16(this, PC.getValue());
                    System.out.println("NOOWWW");
                    PC.setValue((char) 0x60);
                    IME = false;
                    memory.write(0xFF0F, (byte) 16);
                    current_interrupt_requests &= 255 - 16;
                    memory.write(interrupt_pointer, current_interrupt_requests);
                }


                else if (memory.read_byte(0xFF50) == 1) {
                    if (((char) (memory.read_byte(0xFF44) & 255) == 144)) {
                        push_to_stack_d16(this, PC.getValue());
                        PC.setValue((char) 0x40);
                        memory.write(0xFF0F, (byte) 1);
                        IME = false;
                    }
                }
            }
        }
//        if(IME) {
        // V-BLANK interrupt

        cycles++;
    }
}
