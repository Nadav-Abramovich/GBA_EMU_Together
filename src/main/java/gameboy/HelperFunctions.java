package gameboy;

import gameboy.Registers.Register;
import gameboy.Registers.RegisterPair;

public class HelperFunctions {
    public static void push_to_stack_d16(CPU cpu, char value) {
        cpu.memory.write(cpu.SP.getValue() - 1, (byte) ((value >> 8) & 255));
        cpu.memory.write(cpu.SP.getValue() - 2, (byte) (value & 255));
        cpu.SP.increment(-2);
    }

    public static char pop_from_stack_d16(CPU cpu) {
        char lower = (char) (cpu.memory.read_byte(cpu.SP.getValue()) & 255);
        char higher = (char) (cpu.memory.read_byte(cpu.SP.getValue() + 1) & 255);
        cpu.SP.increment(2);
        return (char) (lower | (higher << 8));
    }

    public static void add_register(CPU cpu, Register reg, int value, boolean carry, boolean subtract) {
        int carry_value = 0;
        if (cpu.AF.isCarryFlagOn()) {
            carry_value = 1;
        }

        if (!subtract) {
            // 16 bit addition
            if (reg instanceof RegisterPair) {
                long sum = reg.getValue();
                sum += value;
                if(carry) {
                    sum += carry_value;
                }

                if (sum > 0xFFFF) {
                    cpu.turnOnFlags(Flags.CARRY);
                } else {
                    cpu.turnOffFlags(Flags.CARRY);
                }
                reg.setValue((char) (sum & 0xFFFF));
            }
            // 8 bit addition
            else {
                char sum = (char) (reg.getValue() + (char) (value & 255));
                if(carry) {
                    sum += carry_value;
                }

                if (sum > 0xFF) {
                    cpu.turnOnFlags(Flags.CARRY);
                } else {
                    cpu.turnOffFlags(Flags.CARRY);
                }
                reg.setValue((byte) (sum & 0xFF));
            }
            cpu.turnOffFlags(Flags.SUBTRACTION);
        } else {
            // 16 bit subtraction
            if (reg instanceof RegisterPair) {
                char sum = reg.getValue();
                sum -= value;

                if (value > reg.getValue()) {
                    cpu.turnOnFlags(Flags.CARRY);
                } else {
                    cpu.turnOffFlags(Flags.CARRY);
                }
                reg.setValue(sum);
            }
            // 8 bit subtraction
            else {
                byte sum = (byte) ((reg.getValue() - value) & 0xFF);
                if (value > reg.getValue()) {
                    cpu.turnOnFlags(Flags.HALF_CARRY);
                } else {
                    cpu.turnOffFlags(Flags.HALF_CARRY);
                }
                reg.setValue((byte) (sum & 0xFF));
            }
            cpu.turnOnFlags(Flags.SUBTRACTION);
        }
        if (!(reg instanceof RegisterPair)) {
            if (reg.getValue() == 0) {
                cpu.turnOnFlags(Flags.ZERO);
            } else {
                cpu.turnOffFlags(Flags.ZERO);
            }
        }
    }
}
