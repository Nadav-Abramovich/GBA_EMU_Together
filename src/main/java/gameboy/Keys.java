package gameboy;

public enum Keys {
    RIGHT(0x01),
    LEFT(0x02),
    UP(0x04),
    DOWN(0x08),
    A(0x10),
    B(0x20),
    SELECT(0x40),
    START(0x80);

    public byte value;

    Keys(int key_value) {
        this.value = (byte)key_value;
    }
}
