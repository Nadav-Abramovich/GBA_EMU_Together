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

    public static void and_val(byte data) {
        CPU.AF.A.setValue((byte) (CPU.AF.A.getValue() & data));
        if (CPU.AF.A.getValue() == 0) {
            CPU.setFlags((byte) (Flags.ZERO | Flags.HALF_CARRY));
        } else {
            CPU.setFlags(Flags.HALF_CARRY);
        }
    }

    public static byte rlc_val(byte data) {
        byte new_lsb = 0;
        if ((data & 128) != 0) {
            CPU.turnOnFlags(Flags.CARRY);
            new_lsb = 1;
        } else {
            CPU.turnOffFlags(Flags.CARRY);
        }
        data = (byte) ((data << 1) | new_lsb);

        if (data == 0) {
            CPU.turnOnFlags(Flags.ZERO);
            CPU.turnOffFlags((byte) (Flags.SUBTRACTION | Flags.HALF_CARRY));
        } else {
            CPU.turnOffFlags((byte) (Flags.ZERO | Flags.SUBTRACTION | Flags.HALF_CARRY));
        }
        return data;
    }

    public static void rlc_reg(Register reg) {
        reg.setValue(rlc_val((byte) reg.getValue()));
    }


    public static void rrc_reg(Register reg) {
        reg.setValue(rrc_val((byte) reg.getValue()));
    }

    public static byte rrc_val(byte data) {
        char new_msb = 0;
        if ((data & 1) != 0) {
            CPU.turnOnFlags(Flags.CARRY);
            new_msb = 128;
        } else {
            CPU.turnOffFlags(Flags.CARRY);
        }
        byte new_data = (byte) (((data >> 1)&0x7F) | new_msb);
        if(new_data == 0) {
            CPU.turnOnFlags(Flags.ZERO);
        } else {
            CPU.turnOffFlags(Flags.ZERO);
        }
        CPU.turnOffFlags((byte) (Flags.SUBTRACTION | Flags.HALF_CARRY));
        return new_data;
    }

    public static byte rl_val(byte data) {
        int prev_carry = CPU.AF.isCarryFlagOn() ? 1 : 0;

        if ((data & 128) != 0) {
            CPU.turnOnFlags(Flags.CARRY);
        } else {
            CPU.turnOffFlags(Flags.CARRY);
        }
        data = (byte) ((data << 1) | prev_carry);
        if (data == 0) {
            CPU.turnOnFlags(Flags.ZERO);
        } else {
            CPU.turnOffFlags(Flags.ZERO);
        }
        CPU.turnOffFlags((byte) (Flags.SUBTRACTION | Flags.HALF_CARRY));
        return data;
    }

    public static void rl_reg(Register reg) {
        reg.setValue(rl_val((byte) reg.getValue()));
    }

    public static char rr_val(char data) {
        byte new_msb = 0;
        if(CPU.AF.isCarryFlagOn()) {
            new_msb = (byte) (1 << 7);
        }
        if ((data & 1) != 0) {
            CPU.turnOnFlags(Flags.CARRY);
        } else {
            CPU.turnOffFlags(Flags.CARRY);
        }
        char new_val = (char) (((data >> 1)&0x7F) | new_msb);
        if(new_val == 0) {
            CPU.turnOnFlags(Flags.ZERO);
        } else {
            CPU.turnOffFlags(Flags.ZERO);
        }
        CPU.turnOffFlags((byte) (Flags.SUBTRACTION | Flags.HALF_CARRY));
        return new_val;
    }

    public static void rr_reg(Register reg) {
        reg.setValue(rr_val(reg.getValue()));
    }

    public static char sla_val(char data) {
        if ((data & 128) != 0) {
            CPU.turnOnFlags(Flags.CARRY);
        } else {
            CPU.turnOffFlags(Flags.CARRY);
        }

        char new_data = (char) (((data << 1)) & 255);
        if(new_data == 0) {
            CPU.turnOnFlags(Flags.ZERO);
        } else {
            CPU.turnOffFlags(Flags.ZERO);
        }
        CPU.turnOffFlags((byte) (Flags.SUBTRACTION | Flags.HALF_CARRY));
        return new_data;
    }

    public static void sla_reg(Register reg) {
        reg.setValue(sla_val(reg.getValue()));
    }

    public static char sra_val(char data) {
        byte new_msb = (byte) (data&0x80);

        if ((data & 1) != 0) {
            CPU.turnOnFlags(Flags.CARRY);
        } else {
            CPU.turnOffFlags(Flags.CARRY);
        }
        char new_data = (char) ((byte) (((data >> 1)) & 0x7F) | new_msb);
        if(new_data == 0) {
            CPU.turnOnFlags(Flags.ZERO);
        } else {
            CPU.turnOffFlags(Flags.ZERO);
        }
        CPU.turnOffFlags((byte) (Flags.SUBTRACTION | Flags.HALF_CARRY));
        return new_data;
    }

    public static void sra_reg(Register reg) {
        reg.setValue(sra_val(reg.getValue()));
    }

    public static char srl_val(char data) {
        if ((data & 1) != 0) {
            CPU.turnOnFlags(Flags.CARRY);
        } else {
            CPU.turnOffFlags(Flags.CARRY);
        }
        char new_data = (char) ((char) (data >> 1) & 0x7F);
        if (new_data == 0) {
            CPU.turnOnFlags(Flags.ZERO);
        } else {
            CPU.turnOffFlags(Flags.ZERO);
        }
        CPU.turnOffFlags((byte) (Flags.SUBTRACTION | Flags.HALF_CARRY));
        return new_data;
    }

    public static void srl_reg(Register reg) {
        reg.setValue(srl_val(reg.getValue()));
    }
}
