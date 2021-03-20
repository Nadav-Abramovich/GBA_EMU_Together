package gameboy.Registers;

public class Register {
    private byte value;

    public char getValue() {
        return (char) (value & 255);
    }

    public void setValue(char newValue) {
        this.value = (byte) (newValue & 255);
    }

    public void setValue(byte newValue) {
        this.value = newValue;
    }
}
