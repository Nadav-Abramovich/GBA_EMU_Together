package gameboy.cpu_instructions;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Opcode {
    char value();
    int length();
    boolean should_update_pc() default true;
}
