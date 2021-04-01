package gameboy;

import gameboy.Screen.Screen;
import gameboy.Screen.ScreenLow;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Gameboy {
    public final Memory _memory = new Memory();
//    private final CPU CPU.
//    private final Screen screen;

//    private final Screen_low screen_low;

    public Gameboy() {
        CPU.init(_memory, this);
        Screen.init();
//        ScreenLow.init();


        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

        executorService.scheduleAtFixedRate(() -> {
            try {
                CPU.tick();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 0, 1, TimeUnit.NANOSECONDS);
    }
    public void tick() {
        Screen.loop();
//        ScreenLow.loop();
        // Busy wait on main thread as lwjgl needs to be run / updated from the mainthread...
        long start = System.nanoTime();
        while(start + 114 * 1 >= System.nanoTime());
    }
}
