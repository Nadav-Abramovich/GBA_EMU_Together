package gameboy;

import gameboy.Registers.Register;
import gameboy.Registers.RegisterPair;

public class HelperFunctions {
    public static void push_to_stack_d16(char value) {
        CPU.memory.write(CPU.SP.getValue() - 1, (byte) ((value >> 8) & 255));
        CPU.memory.write(CPU.SP.getValue() - 2, (byte) (value & 255));
        CPU.SP.increment(-2);
    }

    public static char pop_from_stack_d16() {
        char lower = (char) (CPU.memory.read_byte(CPU.SP.getValue()) & 255);
        char higher = (char) (CPU.memory.read_byte(CPU.SP.getValue() + 1) & 255);
        CPU.SP.increment(2);
        return (char) (lower | (higher << 8));
    }

    // TODO: Fix carry/halfcarry
    public static void add_register(Register reg, int value, boolean carry, boolean subtract) {
        int carry_value = 0;
        if (CPU.AF.isCarryFlagOn()) {
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
                    CPU.turnOnFlags(Flags.CARRY);
                } else {
                    CPU.turnOffFlags(Flags.CARRY);
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
                    CPU.turnOnFlags(Flags.CARRY);
                } else {
                    CPU.turnOffFlags(Flags.CARRY);
                }
                reg.setValue((byte) (sum & 0xFF));
            }
            CPU.turnOffFlags(Flags.SUBTRACTION);
        } else {
            // 16 bit subtraction
            if (reg instanceof RegisterPair) {
                char sum = reg.getValue();
                sum -= value;

                if (value > reg.getValue()) {
                    CPU.turnOnFlags(Flags.CARRY);
                } else {
                    CPU.turnOffFlags(Flags.CARRY);
                }
                reg.setValue(sum);
            }
            // 8 bit subtraction
            else {
                byte sum = (byte) ((reg.getValue() - value) & 0xFF);
                if (value > reg.getValue()) {
                    CPU.turnOnFlags(Flags.CARRY);
                } else {
                    CPU.turnOffFlags(Flags.CARRY);
                }
                reg.setValue((byte) (sum & 0xFF));
            }
            CPU.turnOnFlags(Flags.SUBTRACTION);
        }
        if (!(reg instanceof RegisterPair)) {
            if (reg.getValue() == 0) {
                CPU.turnOnFlags(Flags.ZERO);
            } else {
                CPU.turnOffFlags(Flags.ZERO);
            }
        }
    }
}
