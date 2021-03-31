package gameboy.Registers;

import gameboy.Flags;

public class AF extends RegisterPair {
    public Register A = this.higher;
    public Register F = this.lower;

    // TODO: Consider moving this to CPU
    public boolean isZeroFlagOn() {
        return (this.F.getValue() & Flags.ZERO) != 0;
    }

    public boolean isSubtractionFlagOn() {
        return (this.F.getValue() & Flags.SUBTRACTION) != 0;
    }

    public boolean isHalfCarryFlagOn() {
        return (this.F.getValue() & Flags.HALF_CARRY) != 0;
    }

    public boolean isCarryFlagOn() {
        return (this.F.getValue() & Flags.CARRY) != 0;
    }

    @Override
    public void setValue(char value) {
        value&=0xFFF0;
        this.lower.setValue((char) (value & 255));
        this.higher.setValue((char) ((value >> 8) & 255));
    }
}
