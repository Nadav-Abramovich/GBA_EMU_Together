package gameboy.Screen;

import gameboy.CPU;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryStack;

import java.awt.*;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Screen {
    private final CPU cpu;
    // The window handle
    private final long window;

    public Screen(CPU cpu) {
        this.cpu = cpu;

        // Setup an error callback. The default implementation
        // will print the error message in System.err.
//        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");

        // Configure GLFW
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

        // Create the window
        window = glfwCreateWindow(160, 144, "GameBoy!", NULL, NULL);
        if (window == NULL)
            throw new RuntimeException("Failed to create the GLFW window");

        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
                glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
        });

        // Get the thread stack and push a new frame
        try (MemoryStack stack = stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(window, pWidth, pHeight);

            // Get the resolution of the primary monitor
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            // Center the window
            glfwSetWindowPos(
                    window,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        } // the stack frame is popped automatically

        // Make the OpenGL context current
        glfwMakeContextCurrent(window);
        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(window);

        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();
        glDisable(GL_BLEND);
        // Set the clear color
        glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
    }

    public void run() {
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");

//        init();
//        loop();

        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    private char get_ly() {
        return (char) (this.cpu.memory.read_byte(0xFF44) & 255);
    }

    private void xor_ly() {
        this.cpu.memory.write(0xFF44, (byte) 0);
    }

    private void inc_ly() {
        this.cpu.memory.write(0xFF44, (byte) ((this.cpu.memory.read_byte(0xFF44) & 255) + 1));
    }

    public void loop() {
        glfwMakeContextCurrent(window);

        if (((this.cpu.memory.read_byte(0xFF40) >> 7) & 1) == 1) {
            if (get_ly() == 144) {
                draw_screen();
                // Poll for window events. The key callback above will only be
                // invoked during this call.
                glfwPollEvents();
            }
            inc_ly();
            if (get_ly() == 156) {
                xor_ly();
            }
        }
    }

    byte[] screen = new byte[(256 * 256) * 4];
    ByteBuffer screen_buffer = ByteBuffer.allocateDirect(screen.length);

    private void putPixel(int x, int y, int line, int col, int color) {
        int width = 32 * 8;
        int real_x = x * 8 - col + 8;
        byte vertical_y = cpu.memory.read_byte(0xFF42);

        int real_y = width - 30 - (y * 8 + line) + vertical_y - 100 + 16;
        if (real_y > 0 && real_x > 0) {
            screen[(real_x + real_y * width) * 4] = (byte) ((color >> 16) & 255);
            screen[(real_x + real_y * width) * 4 + 1] = (byte) ((color >> 8) & 255);
            screen[(real_x + real_y * width) * 4 + 2] = (byte) (color & 255);
            screen[(real_x + real_y * width) * 4 + 3] = (byte) ((color >> 24) & 255);

        }
    }


    //TODO: LCD OAM DMA & cleanup
    private void draw_screen() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
        cpu.memory.write(0xFF44, (byte) (cpu.memory.read_byte(0xFF44) + 1));

        screen_buffer = ByteBuffer.allocateDirect(screen.length);
        screen = new byte[(256 * 256) * 4];
        for (int x = 0; x < 256; x++) {
            for (int y = 0; y < 256; y++) {
                screen[(y * 256 + x) * 4] = (byte) ((Color.WHITE.getRGB() >> 16) & 255);
                screen[(y * 256 + x) * 4 + 1] = (byte) ((Color.WHITE.getRGB() >> 8) & 255);
                screen[(y * 256 + x) * 4 + 2] = (byte) (Color.WHITE.getRGB() & 255);
                screen[(y * 256 + x) * 4 + 3] = (byte) ((Color.WHITE.getRGB() >> 24) & 255);
            }
        }
        screen_buffer = ByteBuffer.allocateDirect(screen.length);

        // Background
        for (int y = 0; y < 32; y++) {
            for (int x = 0; x < 32; x++) {
                int val = cpu.memory.read_byte(0x9800 + y * 32 + x) & 255;
                if (val != 0) {
                    for (int spriteY = 0; spriteY < 8; spriteY++) {
                        for (int spriteX = 0; spriteX < 8; spriteX++) {
                            int sprite_pointer = 0x8000 + 0x10 * val;

                            for (int line = 0; line < 8; line++) {
                                for (int col = 0; col < 8; col++) {
                                    int LOWER = (cpu.memory.read_byte(sprite_pointer + line * 2) & 255) & (1 << col);
                                    int HIGHER = (cpu.memory.read_byte(sprite_pointer + line * 2 + 1) & 255) & (1 << col);
                                    int COLOR = ((HIGHER << 1) | (LOWER)) >> col;
                                    int color;
                                    if (COLOR == 0) {
                                        color = Color.WHITE.getRGB();
                                    } else if (COLOR == 1) {
                                        color = Color.LIGHT_GRAY.getRGB();
                                    } else if (COLOR == 2) {
                                        color = Color.DARK_GRAY.getRGB();
                                    } else {
                                        color = Color.BLACK.getRGB();
                                    }
//                                    int color = switch (COLOR) {
//                                        case 0 -> Color.WHITE.getRGB();
//                                        case 1 -> Color.BLACK.getRGB();
//                                        case 2 -> Color.GREEN.getRGB();
//                                        case 3 -> Color.RED.getRGB();
//                                        default -> Color.BLUE.getRGB();
//                                    };
                                    putPixel(x, y, line, col, color);
                                }
                            }
                        }
                    }
                }
            }
        }


        // Window
        for (int y = 0; y < 32; y++) {
            for (int x = 0; x < 32; x++) {
                byte val = cpu.memory.read_byte(0x9C00 + y * 32 + x);
                if (val != 0) {
                    for (int spriteY = 0; spriteY < 8; spriteY++) {
                        for (int spriteX = 0; spriteX < 8; spriteX++) {
                            var sprite_pointer = 0x8000 + 0x10 * val;

                            for (int line = 0; line < 8; line++) {
                                for (int col = 0; col < 8; col++) {
                                    int LOWER = (cpu.memory.read_byte(sprite_pointer + line * 2) & 255) & (1 << col);
                                    int HIGHER = (cpu.memory.read_byte(sprite_pointer + line * 2 + 1) & 255) & (1 << col);
                                    int COLOR = ((HIGHER << 1) | (LOWER)) >> col;
                                    int color;
                                    if (COLOR == 0) {
                                        color = Color.WHITE.getRGB();
                                    } else if (COLOR == 1) {
                                        color = Color.LIGHT_GRAY.getRGB();
                                    } else if (COLOR == 2) {
                                        color = Color.DARK_GRAY.getRGB();
                                    } else {
                                        color = Color.BLACK.getRGB();
                                    }
//                                    int color = switch (COLOR) {
//                                        case 0 -> Color.WHITE.getRGB();
//                                        case 1 -> Color.BLACK.getRGB();
//                                        case 2 -> Color.GREEN.getRGB();
//                                        case 3 -> Color.RED.getRGB();
//                                        default -> Color.BLUE.getRGB();
//                                    };
                                    putPixel(x, y, line, col, color);
                                }
                            }
                        }
                    }
                }
            }
        }

        for (int i = 0xFE00; i < 0xFE9F; i += 4) {
            char y = (char) (cpu.memory.read_byte(i) & 255);
            char x = (char) (cpu.memory.read_byte(i + 1) & 255);
            char val = (char) (cpu.memory.read_byte(i + 2) & 255);
            char attributes = (char) (cpu.memory.read_byte(i + 3) & 255);
            for (int spriteY = 0; spriteY < 8; spriteY++) {
                cpu.memory.write(0xFF44, (byte) (y * 8 + spriteY));
                for (int spriteX = 0; spriteX < 8; spriteX++) {
                    var sprite_pointer = 0x8000 + 0x10 * val;

                    for (int line = 0; line < 8; line++) {
                        for (int col = 0; col < 8; col++) {
                            int LOWER = (cpu.memory.read_byte(sprite_pointer + line * 2) & 255) & (1 << col);
                            int HIGHER = (cpu.memory.read_byte(sprite_pointer + line * 2 + 1) & 255) & (1 << col);
                            int COLOR = ((HIGHER << 1) | (LOWER)) >> col;
                            int color;
                            if (COLOR == 0) {
                                color = Color.WHITE.getRGB();
                            } else if (COLOR == 1) {
                                color = Color.LIGHT_GRAY.getRGB();
                            } else if (COLOR == 2) {
                                color = Color.DARK_GRAY.getRGB();
                            } else {
                                color = Color.BLACK.getRGB();
                            }
                            putPixel(x, y, line, col, color);
                        }
                    }
                }
            }
        }

        ByteBuffer pixels = screen_buffer.put(screen).flip();
//        GL11.glPixelZoom(1, 1);
        GL11.glDrawPixels(256, 256, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, pixels);

        glfwSwapBuffers(window); // swap the color buffers
    }
}