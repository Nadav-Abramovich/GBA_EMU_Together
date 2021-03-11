package gameboy.Registers;

public class RegisterPair {
    protected final Register lower = new Register();
    protected final Register higher = new Register();

    public void setValue(char value) {
        this.lower.setValue((byte)(value & 255));
        this.higher.setValue((byte)(value >> 8));
    }

    public char getValue() {
        return (char)(lower.getValue() | (higher.getValue() << 8));
    }

    public void increment(int amount) {
        this.setValue((char)(this.getValue() + amount));
    }
}

