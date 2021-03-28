package gameboy.cpu_instructions;

import gameboy.CPU;
import gameboy.Flags;

import static gameboy.HelperFunctions.add_register;

// We suppress this warning because this class and its methods are dynamically imported
// and therefore IntelliJ doesn't recognize their usage.
@SuppressWarnings("unused")
public class Subtracts implements CPUInstructions {
    @Opcode(value = 0x90, length = 1, cycles = 1)
    public static void sub_b() {
        add_register(CPU.AF.A, CPU.BC.B.getValue(), false, true);
    }

    @Opcode(value = 0x91, length = 1, cycles = 1)
    public static void sub_c() {
        add_register(CPU.AF.A, CPU.BC.C.getValue(), false, true);
    }

    @Opcode(value = 0x92, length = 1, cycles = 1)
    public static void sub_d() {
        add_register(CPU.AF.A, CPU.DE.D.getValue(), false, true);
    }

    @Opcode(value = 0x93, length = 1, cycles = 1)
    public static void sub_e() {
        add_register(CPU.AF.A, CPU.DE.E.getValue(), false, true);
    }

    @Opcode(value = 0x94, length = 1, cycles = 1)
    public static void sub_h() {
        add_register(CPU.AF.A, CPU.HL.H.getValue(), false, true);
    }

    @Opcode(value = 0x95, length = 1, cycles = 1)
    public static void sub_l() {
        add_register(CPU.AF.A, CPU.HL.L.getValue(), false, true);
    }

    @Opcode(value = 0x96, length = 1, cycles = 1)
    public static void sub_from_hl() {
        byte target = CPU.memory.read_byte(CPU.HL.getValue());
        add_register(CPU.AF.A, target, false, true);
    }

    @Opcode(value = 0x97, length = 1, cycles = 1)
    public static void sub_a() {
        add_register(CPU.AF.A, CPU.AF.A.getValue(), false, true);
    }

    @Opcode(value = 0x98, length = 1, cycles = 1)
    public static void sbc_a_b() {
        add_register(CPU.AF.A, CPU.BC.B.getValue(), true, true);
    }

    @Opcode(value = 0x99, length = 1, cycles = 1)
    public static void sbc_a_c() {
        add_register(CPU.AF.A, CPU.BC.C.getValue(), true, true);
    }

    @Opcode(value = 0xDE, length = 2, cycles = 1)
    public static void sbc_a_d8() {
        add_register(CPU.AF.A, CPU.memory.read_byte(CPU.PC.getValue() + 1), true, true);
    }
}
