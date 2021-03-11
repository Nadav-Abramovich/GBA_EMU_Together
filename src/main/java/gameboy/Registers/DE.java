package gameboy.Registers;

public class DE extends RegisterPair {
    public Register D = this.higher;
    public Register E = this.lower;
}
