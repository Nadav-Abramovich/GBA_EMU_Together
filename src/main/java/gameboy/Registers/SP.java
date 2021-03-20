package gameboy.Registers;

public class SP extends RegisterPair {
    public Register S = this.higher;
    public Register P = this.lower;


    @Override
    public void setValue(char value) {
        this.lower.setValue((char) (value & 255));
        this.higher.setValue((char) ((value >> 8)&255));
    }


    public void increment(int amount) {
        char new_value = (char) (this.getValue() + amount);
        this.setValue(new_value);
    }
}
