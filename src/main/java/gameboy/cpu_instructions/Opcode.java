package gameboy.cpu_instructions;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Opcode {
    char opcode();
    int length();
}
