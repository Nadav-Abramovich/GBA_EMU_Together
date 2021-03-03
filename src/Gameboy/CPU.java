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
        Stack.cpu_reference = this;
        Memory.cpu_reference = this;
    }


    public void tick() {
        char opcode = (char)(memory[PC] & 255);

        Runnable function = xors.SUPPORTED_ACTIONS.getOrDefault(opcode, null);
        if(function != null) {
            function.run();
        }
        function = Stack.SUPPORTED_ACTIONS.getOrDefault(opcode, null);
        if (function != null) {
            function.run();
            executed_opcode = true;
        }
    }
}
