package Gameboy;

import Gameboy.CPUActions.CPUActions;
import Gameboy.CPUActions.Memory;
import Gameboy.CPUActions.Stack;
import Gameboy.CPUActions.xors;

public class CPU {
    public byte[] memory;
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

    public CPUActions[] supported_actions = new CPUActions[]{
            new xors(this),
            new Stack(this),
            new Memory(this)
    };

    public CPU(byte[] memory) {
        this.memory = memory;
    }


    public void tick() {
        char opcode = (char) (memory[PC] & 255);
        boolean executed_opcode = false;
        for (CPUActions action : supported_actions) {
            Runnable function = action.get_supported_actions().getOrDefault(opcode, null);
            if (function != null) {
                function.run();
                executed_opcode = true;
                break;
            }
        }
        if (!executed_opcode) {
            System.out.println("Failed to execute " + (int) opcode);
            int t = 1 / 0;
        }
    }
}
