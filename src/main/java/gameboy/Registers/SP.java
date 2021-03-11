package gameboy.Registers;

public class SP extends RegisterPair {
    public Register S = this.higher;
    public Register P = this.lower;
}
