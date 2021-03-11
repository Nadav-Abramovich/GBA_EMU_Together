package gameboy;

public class Flags {
    public static byte CARRY = 1 << 4;
    public static byte HALF_CARRY = 1 << 5;
    public static byte SUBTRACTION = 1 << 6;
    public static byte ZERO = (byte) (1 << 7);
}