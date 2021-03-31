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
        byte lower = (byte) (CPU.memory.read_byte(CPU.SP.getValue()) & 255);
        char higher = (char) (CPU.memory.read_byte((CPU.SP.getValue() + 1)&0xFFFF) & 255);
        CPU.SP.increment(2);
        return (char) (((higher << 8)&0xFF00) | (lower&0xFF));
    }

    public static void add_register(Register reg, int value, boolean carry, boolean subtract, boolean is_inc_dec) {
        add_register(reg, value, carry, subtract, is_inc_dec, false);
    }

        // TODO: Fix halfcarry
    public static void add_register(Register reg, int value, boolean carry, boolean subtract, boolean is_inc_dec, boolean override_d8) {
         int carry_value = 0;
        if (CPU.AF.isCarryFlagOn() && carry) {
            carry_value = 1;
        }
        if (!subtract) {
            // 16 bit addition
            if (reg instanceof RegisterPair && !override_d8) {
                long sum = reg.getValue();
                if((reg.getValue() & 0xFFF) + (value & 0xFFF) > 0xFFF){
                    CPU.turnOnFlags(Flags.HALF_CARRY);
                } else {
                    CPU.turnOffFlags(Flags.HALF_CARRY);
                }

                sum += value;
                sum += carry_value;
                if (sum > 0xFFFF || (reg.getValue() > 0 && sum < 0)) {
                    CPU.turnOnFlags(Flags.CARRY);
                } else {
                    CPU.turnOffFlags(Flags.CARRY);
                }
                reg.setValue((char)(sum&0xFFFF));
            }
            // 8 bit addition
            else {
                if( ((reg.getValue() & 0xF) + (value & 0xF) + carry_value) > 0xF){
                    CPU.turnOnFlags(Flags.HALF_CARRY);
                } else {
                    CPU.turnOffFlags(Flags.HALF_CARRY);
                }
                short sum = (short) (reg.getValue() + value);
                sum += carry_value;
                if(!is_inc_dec) {
                    if ((reg.getValue() & 0xFF) + (value & 0xFF) + carry_value > 0xFF) {
                        CPU.turnOnFlags(Flags.CARRY);
                    } else {
                        CPU.turnOffFlags(Flags.CARRY);
                    }
                }
                if(reg instanceof RegisterPair) {
                    reg.setValue((char) (sum & 0xFFFF));
                } else {
                    reg.setValue((byte) (sum & 0xFF));
                }
            }
            CPU.turnOffFlags(Flags.SUBTRACTION);
        } else {
            // 16 bit subtraction
            if (reg instanceof RegisterPair) {
                System.exit(0);
            }
            // 8 bit subtraction
            else {
                byte sum = (byte) ((reg.getValue() - value - carry_value) & 0xFF);
                if((reg.getValue() & 0xF) - (value & 0xF) - carry_value < 0)
                {
                    CPU.turnOnFlags(Flags.HALF_CARRY);
                } else {
                    CPU.turnOffFlags(Flags.HALF_CARRY);
                }
                if(!is_inc_dec) {
                    if (reg.getValue() - value - carry_value < 0) {
                        CPU.turnOnFlags(Flags.CARRY);
                    } else {
                        CPU.turnOffFlags(Flags.CARRY);
                    }
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
        if(override_d8) {
            CPU.turnOffFlags(Flags.ZERO);
        }
    }

    public static void cmp_value(char value) {
        short t = (short) (CPU.AF.A.getValue() - value);
        CPU.setFlags(Flags.SUBTRACTION);
        if((t&0xFF) == 0) {
            CPU.turnOnFlags(Flags.ZERO);
        }
        if((CPU.AF.A.getValue() & 0xF) - (value & 0xF) < 0) {
            CPU.turnOnFlags(Flags.HALF_CARRY);
        }
        if(t < 0) {
            CPU.turnOnFlags(Flags.CARRY);
        }
    }

    public static void bit_reg(byte data, int index) {
        boolean bit = (data & (1 << index)) != 0;

        if (bit) {
            CPU.turnOffFlags(Flags.ZERO);
        } else {
            CPU.turnOnFlags(Flags.ZERO);
        }
        CPU.turnOffFlags(Flags.SUBTRACTION);
        CPU.turnOnFlags(Flags.HALF_CARRY);
    }

    public static void bit_reg(Register reg, int index) {
        bit_reg((byte) reg.getValue(), index);
    }

    public static byte res_reg(byte data, int index) {
        return (byte) (data & (0xFF - (1<<index)));
    }

    public static void res_reg(Register reg, int index) {
        reg.setValue((byte) (reg.getValue() & (0xFF - (1<<index))));
    }

    public static byte set_reg(byte data, int index) {
        return (byte) (data | (1<<index));
    }

    public static void set_reg(Register reg, int index) {
        reg.setValue((byte) (reg.getValue() | (1<<index)));
    }
}
