package gameboy.Registers;

public class PC extends RegisterPair {
    public Register P = this.higher;
    public Register C = this.lower;
}
