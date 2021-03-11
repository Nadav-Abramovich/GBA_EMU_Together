package gameboy.Registers;

public class SP extends RegisterPair {
    public Register P = this.higher;
    public Register C = this.lower;
}
