package gameboy;

import gameboy.cpu_instructions.CPUInstructions;
import gameboy.cpu_instructions.Opcode;
import org.reflections.Reflections;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import static gameboy.HelperFunctions.push_to_stack_d16;

public class CPU {
    public static boolean PRINT_DEBUG_MESSAGES = false;
    public static final String CPU_INSTRUCTIONS_PACKAGE_PATH = "gameboy.cpu_instructions";
    public static final String EXECUTED_OPCODE_MSG_FORMAT = "[DEBUG] [0x%s] 0x%s: %s%n";
    public static final String FAILED_TO_EXECUTE_OPCODE_MSG_FORMAT = "[CRITICAL] Failed to execute opcode 0x%s%n";
    public static final String OPCODE_NOT_IMPLEMENTED_MSG_FORMAT = "[CRITICAL] Opcode 0x%s is not implemented%n";

    public static Memory memory;
    public static gameboy.Registers.AF AF = new gameboy.Registers.AF();
    public static gameboy.Registers.BC BC = new gameboy.Registers.BC();
    public static gameboy.Registers.DE DE = new gameboy.Registers.DE();
    public static gameboy.Registers.HL HL = new gameboy.Registers.HL();
    public static gameboy.Registers.SP SP = new gameboy.Registers.SP();
    public static gameboy.Registers.PC PC = new gameboy.Registers.PC();
    public static final Map<Character, Method> supported_actions = new HashMap<>();
    public static boolean IME = false; // Interrupt master enable
    private static PrintWriter writer;

    public static boolean HALTED = false;

    public static void setFlags(byte value) {
        AF.F.setValue((byte) (value & 255));
    }

    public static void turnOnFlags(byte value) {
        AF.F.setValue((byte) ((AF.F.getValue() | value) & 255));
    }

    public static void turnOffFlags(byte value) {
        AF.F.setValue((byte) (AF.F.getValue() & ((~value) & 255)));
    }

    public static int cycles = 0;
    public static int performed_cycles = 0;
    public static Gameboy game_boy;

    public static void init(Memory memory, Gameboy gameboy) {
        game_boy = gameboy;
        CPU.memory = memory;

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
        try {
            writer = new PrintWriter("D:\\temp\\the-file-name.txt", StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static char get_opcode() {
//        while (PC.getValue() > 0x7FFF && PC.getValue() < 0xC000) {
//            System.out.println("");
//            memory.bank_number++;
//            PC.setValue((char)(PC.getValue() - 0x4000));
//        }
        if (PC.getValue() == 0x100) {
//            if(AF.getValue() != 0x01B0) {
//                System.out.printf("Bad value for AF 0x%s", Integer.toHexString(AF.getValue()));
////                System.exit(1);
//            }
//            if (memory.read_byte(0xFF11) != (byte)0xBF) {
//                System.out.printf("Bad value for [0x%s]:0x%s", Integer.toHexString(0xFF11).toUpperCase(), Integer.toHexString(Byte.toUnsignedInt(memory.read_byte(0xFF11))).toUpperCase());
////                System.exit(1);
//            }
            System.out.println("Starting ROM");
            PRINT_DEBUG_MESSAGES = true;

//            AF.setValue((char) 0x01b0);
//            BC.setValue((char) 0x0013);
//            DE.setValue((char) 0x00D8);
//            HL.setValue((char) 0x014D);
//            SP.setValue((char) 0xFFFE);
        }
        if (PC.getValue() >= 0x7FFF) {
            if (PC.getValue() < 0xffb6) {
//                System.out.println("BAD");
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


    private static void execute_action(Method action, char opcode) {
        try {
            if(PRINT_DEBUG_MESSAGES && CPU.PC.getValue() != 0xC7D2) {
                writer.print(String.format(EXECUTED_OPCODE_MSG_FORMAT, Integer.toHexString(PC.getValue()).toUpperCase(), Integer.toHexString(opcode).toUpperCase(), action.getName()));
//                System.out.printf(EXECUTED_OPCODE_MSG_FORMAT, Integer.toHexString(PC.getValue()).toUpperCase(), Integer.toHexString(opcode).toUpperCase(), action.getName());
            }
//            System.out.println(String.format(EXECUTED_OPCODE_MSG_FORMAT, Integer.toHexString(PC.getValue()).toUpperCase(), Integer.toHexString(opcode).toUpperCase(), action.getName()));
//            System.out.println(Integer.toHexString(CPU.DE.getValue()).toUpperCase());

//            System.out.println(Integer.toHexString(PC.getValue()).toUpperCase());
            action.invoke(null);
            Opcode opcode_metadata = action.getAnnotation(Opcode.class);
            performed_cycles += opcode_metadata.cycles();
            if (opcode_metadata.should_update_pc()) {
                PC.increment(opcode_metadata.length());
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            System.out.printf(FAILED_TO_EXECUTE_OPCODE_MSG_FORMAT, Integer.toHexString(opcode));
            System.exit(1);
        }
    }

    public static void tick() {
        // make time tick?
        memory.write(0xFF04, (byte) ((memory.read_byte(0xFF04) & 255) + 1));
        char opcode = get_opcode();
        if (cycles >= performed_cycles) {
            Method action = supported_actions.getOrDefault(opcode, null);

            if(!HALTED) {
                if (action != null) {
                    execute_action(action, opcode);
                } else {
                    System.out.printf(OPCODE_NOT_IMPLEMENTED_MSG_FORMAT, Integer.toHexString(opcode));
                    System.exit(1);
                }
            }
            if (IME && PC.getValue() > 0x100) {
                int interrupt_request_pointer = 0xFF0F;
                int interrupt_enable_pointer = 0xFFFF;

                byte enabled_interrupts = memory.read_byte(interrupt_enable_pointer);
                byte requested_interrupts = memory.read_byte(interrupt_request_pointer);
                byte current_interrupt_requests = (byte)(enabled_interrupts & requested_interrupts);
                if ((current_interrupt_requests & 1) != 0) {
                    push_to_stack_d16(PC.getValue());
                    PC.setValue((char) 0x40);
                    IME = false;
                    memory.write(0xFF0F, (byte) 1);
                    requested_interrupts &= 254;
                    memory.write(interrupt_request_pointer, requested_interrupts);
                    HALTED = false;
                }

                // serial transfer complete
                else if ((current_interrupt_requests & 8) != 0) {
                    push_to_stack_d16(PC.getValue());
                    PC.setValue((char) 0x58);
                    memory.write(0xFF01, (byte) 0xFF);
                    IME = false;
                    memory.write(0xFF0F, (byte) 8);
                    requested_interrupts &= (255 - 8);
                    memory.write(interrupt_request_pointer, requested_interrupts);
                    memory.write(0xFF01, (byte) 0xFF);
                    HALTED = false;
                }
                else if ((current_interrupt_requests & 16) != 0) {
                    push_to_stack_d16(PC.getValue());
                    PC.setValue((char) 0x60);
                    IME = false;
                    memory.write(0xFF0F, (byte) 16);
                    requested_interrupts &= (255 - 16);
                    memory.write(interrupt_request_pointer, requested_interrupts);
                    HALTED = false;
                } else if (memory.read_byte(0xFF50) == 1) {
                    if (((char) (memory.read_byte(0xFF44) & 255) == 144)) {
                        push_to_stack_d16(PC.getValue());
                        PC.setValue((char) 0x40);
                        memory.write(0xFF0F, (byte) 1);
                        IME = false;
                        HALTED = false;
                    }
                }
            }
        }
//        CPU.memory.write(0xFF41, (byte) ((byte)( CPU.memory.read_byte(0xFF41) + 1)%4));
        cycles++;
    }
}
