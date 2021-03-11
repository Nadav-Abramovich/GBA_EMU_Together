package gameboy.Registers;

public class BC extends RegisterPair {
    public Register B = this.higher;
    public Register C = this.lower;
}
