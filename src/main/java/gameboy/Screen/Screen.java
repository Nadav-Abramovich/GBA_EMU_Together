package gameboy.Screen;

import gameboy.CPU;
import gameboy.Keys;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryStack;

import java.awt.*;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL14.glWindowPos2i;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Screen {
    // The window handle
    public static long window;
    static byte[][][] screen = new byte[256][256][4];
    static ByteBuffer screen_buffer = ByteBuffer.allocateDirect(256 * 256 * 4);
    static ByteBuffer pixels = ByteBuffer.allocateDirect(256 * 256 * 4);

    public static void init() {
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
        System.out.println("CREATING");
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
            assert vidmode != null;
            glfwSetWindowPos(
                    window,
                    (vidmode.width() - pWidth.get(0)) / 2 - 85,
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
        glfwSetKeyCallback(window, keyboard);
    }

    private static final GLFWKeyCallback keyboard = new GLFWKeyCallback() {
        @Override
        public void invoke(long window, int key, int scancode, int action, int mods) {
            //;  $80 - Start   $8 - Down
            //;  $40 - Select  $4 - Up
            //;  $20 - B       $2 - Left
            //;  $10 - A       $1 - Right
            if (action == GLFW_PRESS) {
                if (key == GLFW_KEY_ENTER) {
                    CPU.memory.keys_pressed |= Keys.START.value;
                    CPU.memory.write(0xFF0F, (byte) (CPU.memory.read_byte(0xFF0F) | 16));
                } else if (key == GLFW_KEY_BACKSPACE) {
                    CPU.memory.keys_pressed |= Keys.SELECT.value;
                    CPU.memory.write(0xFF0F, (byte) (CPU.memory.read_byte(0xFF0F) | 16));
                } else if (key == GLFW_KEY_A) {
                    CPU.memory.keys_pressed |= Keys.B.value;
                    CPU.memory.write(0xFF0F, (byte) (CPU.memory.read_byte(0xFF0F) | 16));
                } else if (key == GLFW_KEY_S) {
                    CPU.memory.keys_pressed |= Keys.A.value;
                    CPU.memory.write(0xFF0F, (byte) (CPU.memory.read_byte(0xFF0F) | 16));
                } else if (key == GLFW_KEY_DOWN) {
                    CPU.memory.keys_pressed |= Keys.DOWN.value;
                    CPU.memory.write(0xFF0F, (byte) (CPU.memory.read_byte(0xFF0F) | 16));
                } else if (key == GLFW_KEY_UP) {
                    CPU.memory.keys_pressed |= Keys.UP.value;
                    CPU.memory.write(0xFF0F, (byte) (CPU.memory.read_byte(0xFF0F) | 16));
                } else if (key == GLFW_KEY_LEFT) {
                    CPU.memory.keys_pressed |= Keys.LEFT.value;
                    CPU.memory.write(0xFF0F, (byte) (CPU.memory.read_byte(0xFF0F) | 16));
                } else if (key == GLFW_KEY_RIGHT) {
                    CPU.memory.keys_pressed |= Keys.RIGHT.value;
                    CPU.memory.write(0xFF0F, (byte) (CPU.memory.read_byte(0xFF0F) | 16));
                }
            } else if (action == GLFW_RELEASE) {
                CPU.memory.keys_pressed = (byte) 0x0;
                CPU.memory.write(0xFF0F, (byte) (CPU.memory.read_byte(0xFF0F) | 16));
            }
        }
    };

    public static char get_ly() {
        return (char) (CPU.memory.read_byte(0xFF44) & 255);
    }

    private static void xor_ly() {
        CPU.memory.write(0xFF44, (byte) 0);
    }

    private static void inc_ly() {
        CPU.memory.write(0xFF44, (byte) ((CPU.memory.read_byte(0xFF44) & 255) + 1));
    }

    public static void loop() {
        glfwMakeContextCurrent(window);

        if (((CPU.memory.read_byte(0xFF40) >> 7) & 1) == 1) {
            if (get_ly() == 144) {
                draw_screen();
            }
        }
        inc_ly();
        if (get_ly() == 154) {
            xor_ly();
        }
        // Poll for window events. The key callback above will only be
        // invoked during this call.
        glfwPollEvents();

    }

    private static void putPixel(int x, int y, int line, int col, int color) {
        int real_x = x * 8 + (8 - col);
        // The y axis is from bottom and upwards so (0,0) is bottom-left....
        int real_y = 255 - ((byte)(y * 8 + line))&255;
        if (real_y > 0 && real_x > 0 && real_x < 256 && real_y < 256) {
            screen[real_y][real_x][0] = (byte) ((color >> 16) & 255);
            screen[real_y][real_x][1] = (byte) ((color >> 8) & 255);
            screen[real_y][real_x][2] = (byte) (color & 255);
            screen[real_y][real_x][3] = (byte) ((color >> 24) & 255);
        } else {
//            System.out.printf("NOT DRAWING: 0x%s, 0x%s\r\n", Integer.toHexString(real_x).toUpperCase(), Integer.toHexString(real_y).toUpperCase());
        }
    }

    private static void putPixel2(int x, int y, int line, int col, int color) {
        int real_x = x + (8 - col);

        int real_y = 255 - (y + line);
        if (real_y >= 0 && real_x >= 0 && real_x < 256 && real_y < 256) {
            screen[real_y][real_x][0] = (byte) ((color >> 16) & 255);
            screen[real_y][real_x][1] = (byte) ((color >> 8) & 255);
            screen[real_y][real_x][2] = (byte) (color & 255);
            if(color != Color.WHITE.getRGB()) {
                screen[real_y][real_x][3] = (byte) ((color >> 24) & 255);
            } else {
                screen[real_y][real_x][3] = (byte) 0;
            }
        }
    }

    private static void draw_tile(int sprite_pointer, int x, int y, boolean putpixel_by_tile,
                                  boolean should_add_vertical_horizontal_y, boolean flip_x, boolean flip_y) {
        for (int line = 0; line < 8; line++) {
            for (int col = 0; col < 8; col++) {
                int LOWER = (CPU.memory.read_byte(sprite_pointer + line * 2) & 255) & (1 << col);
                int HIGHER = (CPU.memory.read_byte(sprite_pointer + line * 2 + 1) & 255) & (1 << col);
                int COLOR = ((HIGHER << 1) | (LOWER)) >> col;
                int[] colors;
                char value;
                if(putpixel_by_tile) {
                    colors = new int[]{
                            Color.WHITE.getRGB(),
                            Color.LIGHT_GRAY.getRGB(),
                            Color.DARK_GRAY.getRGB(),
                            Color.BLACK.getRGB(),
                    };

                    value = (char) (CPU.memory.read_byte(0xFF47) & 255);
                } else {
                    colors = new int[]{
                            Color.WHITE.getRGB(),
                            Color.LIGHT_GRAY.getRGB(),
                            Color.DARK_GRAY.getRGB(),
                            Color.BLACK.getRGB(),
                    };

                    value = (char) (CPU.memory.read_byte(0xFF48) & 255);
                }
                int color = (value >> (2 * (COLOR)))&3;
                color = colors[color];

                char vertical_y = (char) (CPU.memory.read_byte(0xFF42) & 255);
                char horizontal_x = (char) (CPU.memory.read_byte(0xFF43) & 255);
                if(should_add_vertical_horizontal_y) {
                    putPixel(x, y, line - vertical_y, col - horizontal_x, color);
                } else {
                    if(putpixel_by_tile) {
                        putPixel(x, y, line, col, color);
                    } else {
                        if(flip_y && flip_x) {
                            putPixel2(x, y, 8-line, 8-col, color);
                        } else if (flip_x) {
                            putPixel2(x, y, line, 8-col, color);
                        } else if(flip_y) {
                            putPixel2(x, y, 16-line, col, color);
                        } else {
                            putPixel2(x, y, line, col, color);
                        }
                    }
                }
            }
        }
    }

    private static void draw_background() {
        for (int y = 0; y < 32; y++) {
            for (int x = 0; x < 32; x++) {
                int base_address = (CPU.memory.read_byte(0xFF40)&(1<<3)) != 0?0x9C00:0x9800;
                int val = CPU.memory.read_byte(base_address + y * 32 + x) & 255;
                if (val != 0) {
                    int sprite_base_address = (CPU.memory.read_byte(0xFF40)&(1<<4)) != 0?0x8000:0x8800;
                    int sprite_pointer = sprite_base_address + 0x10 * val;
                    draw_tile(sprite_pointer, x, y, true, true, false, false);
                }
            }
        }
    }

    private static void draw_window() {
        for (int y = 0; y < 32; y++) {
            for (int x = 0; x < 32; x++) {
                int base_address = (CPU.memory.read_byte(0xFF40)&(1<<6)) != 0?0x9C00:0x9800;
                byte val = CPU.memory.read_byte(base_address + y * 32 + x);
                if (val != 0) {
                    int sprite_base_address = (CPU.memory.read_byte(0xFF40)&(1<<4)) != 0?0x8000:0x8800;
                    var sprite_pointer = sprite_base_address + 0x10 * val;
                    draw_tile(sprite_pointer, x, y, true, false, false, false);
                }
            }
        }
    }

    private static void draw_objects() {
        for (char i = 0xFE00; i <= 0xFE9F; i += 4) {
            int y = ((CPU.memory.read_byte(i) & 255) - 16);
            int x = (char) ((CPU.memory.read_byte(i + 1) & 255) - 8);
            char tile_index = (char) (CPU.memory.read_byte(i + 2) & 255);
            if(tile_index == 7 * 16 + 8) {
                System.out.println("AAA");
            }
            char attributes = (char) (CPU.memory.read_byte(i + 3) & 255);
            boolean flip_x = false, flip_y = false;
            if((attributes & 64) != 0) {
                flip_y = true;
            }
            if((attributes & 32) != 0) {
                flip_x = true;
            }
            char sprite_pointer = (char) (0x8000 + 0x10 * tile_index);
            draw_tile(sprite_pointer, x, y, false, false, flip_x, flip_y);
        }
    }


    private static void draw_screen() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

        screen = new byte[256][256][4];
        for (int x = 0; x < 256; x++) {
            for (int y = 0; y < 256; y++) {
                screen[x][y][0] = (byte) ((Color.WHITE.getRGB() >> 16) & 255);
                screen[x][y][1] = (byte) ((Color.WHITE.getRGB() >> 8) & 255);
                screen[x][y][2] = (byte) (Color.WHITE.getRGB() & 255);
                screen[x][y][3] = (byte) ((Color.WHITE.getRGB() >> 24) & 255);
            }
        }
        screen_buffer = ByteBuffer.allocateDirect(screen.length);

        // Background
        draw_background();
        //

        // Window
        if((CPU.memory.read_byte(0xFF40) & (1<<5)) != 0) {
            draw_window();
        }
        if((CPU.memory.read_byte(0xFF40) & (1<<1)) != 0) {
            draw_objects();
        }

        byte[] temp = new byte[256 * 256 * 4];
        for (int y = 0; y < 255; y++) {
            for (int x = 0; x < 255; x++) {
                System.arraycopy(screen[y][x], 0, temp, (y * 256 + x) * 4, 4);
            }

            ByteBuffer screen_buffer = ByteBuffer.allocateDirect(temp.length);

            pixels = screen_buffer.put(temp).flip();
        }
//        GL11.glPixelZoom(1, 1);
        glWindowPos2i(0, 144 - 256);
        GL11.glDrawPixels(256, 256, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, pixels);

        glfwSwapBuffers(window); // swap the color buffers
    }
}