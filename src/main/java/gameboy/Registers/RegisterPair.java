package gameboy.Registers;

public class RegisterPair extends Register {
    protected final Register lower = new Register();
    protected final Register higher = new Register();

    @Override
    public void setValue(char value) {
        this.lower.setValue((char) (value & 255));
        this.higher.setValue((char) ((value >> 8) & 255));
    }

    @Override
    public char getValue() {
        return (char) (lower.getValue() | (higher.getValue() << 8));
    }

    public short getSignedValue() {
        return (short) (lower.getValue() | (higher.getValue() << 8));
    }

    public void increment(int amount) {
        this.setValue((char) (this.getValue() + amount));
    }
}

