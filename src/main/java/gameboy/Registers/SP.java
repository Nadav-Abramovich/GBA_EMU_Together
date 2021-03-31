package gameboy.Registers;

public class SP extends RegisterPair {
    public Register S = this.higher;
    public Register P = this.lower;

    public void increment(int amount) {
        char new_value = (char) (this.getValue() + amount);
        this.setValue(new_value);
    }
}
