package Gameboy;
import Gameboy.CPUActions.xors;


public class CPU {
    byte[] memory;
    public char AF = 0;
    public char BC = 0;
    public char DE = 0;
    public char HL = 0;
    public char SP = 0;
    public char PC = 0;


    public void xor_flags() {
        AF = (char) ((AF >> 8) << 8);
    }

    public void turn_on_zero_flag() {
        AF |= 128;
    }

    public CPU(byte[] memory) {
        this.memory = memory;

        // For all inheritors of CPUActions
        xors.cpu_reference = this;
    }

    private void _ld_sp_d16() {
        byte lowerByte = memory[PC + 1];
        byte upperByte = memory[PC + 2];
        SP = (char) (upperByte << 8 | lowerByte);
        PC += 3;
    }

    public void tick() {
        char opcode = (char)(memory[PC] & 255);

        Runnable function = xors.SUPPORTED_ACTIONS.getOrDefault(opcode, null);
        if(function != null) {
            function.run();
        }

        else {
            switch (opcode) {
                case (0x31):
                    System.out.println("_ld_sp_d16");
                    _ld_sp_d16();
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + (int) (memory[PC] & 255));
            }
        }
    }
}
