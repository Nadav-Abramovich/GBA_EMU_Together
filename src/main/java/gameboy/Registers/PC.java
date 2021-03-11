package gameboy.Registers;

public class PC extends RegisterPair {
    public Register P = this.higher;
    public Register C = this.lower;

    public void increment(int amount) {
        this.setValue((char)(this.getValue() + amount));
    }
}
