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
    public static long opengl_window;
    static Byte[][][] under_background = new Byte[144][160][4];
    static Byte[][][] over_background = new Byte[144][160][4];
    static Byte[][][] window = new Byte[144][160][4];
    static Byte[][][] window_low = new Byte[144][160][4];
    static Byte[][][] background = new Byte[256][256][4];
    static Byte[][][] background_low = new Byte[256][256][4];
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
        opengl_window = glfwCreateWindow(160, 144, "GameBoy!", NULL, NULL);
        if (opengl_window == NULL)
            throw new RuntimeException("Failed to create the GLFW window");

        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(opengl_window, (window, key, scancode, action, mods) -> {
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
                glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
        });

        // Get the thread stack and push a new frame
        try (MemoryStack stack = stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(opengl_window, pWidth, pHeight);

            // Get the resolution of the primary monitor
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            // Center the window
            assert vidmode != null;
            glfwSetWindowPos(
                    opengl_window,
                    (vidmode.width() - pWidth.get(0)) / 2 - 85,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        } // the stack frame is popped automatically

        // Make the OpenGL context current
        glfwMakeContextCurrent(opengl_window);
        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(opengl_window);

        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();
        glDisable(GL_BLEND);
        // Set the clear color
        glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        glfwSetKeyCallback(opengl_window, keyboard);
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
        if (CPU.memory.read_byte(0xFF44) == CPU.memory.read_byte(0xFF45)) {
            CPU.memory.write(0xFF0F, (byte) (CPU.memory.read_byte(0xFF0F) | 0x2));
        }
    }

    public static void loop() {
        glfwMakeContextCurrent(opengl_window);

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

    private static void putPixelBackground(int x, int y, int line, int col, int color) {
        if (color == Color.TRANSLUCENT) {
            return;
        }

        int real_x = x * 8 + (7 - col);
        // The y axis is from bottom and upwards so (0,0) is bottom-left....
        int real_y = 255 - ((y * 8 + line));
        if (real_y >= 0 && real_x >= 0 && real_x < 256 && real_y < 256) {
            background[real_y][real_x][0] = (byte) ((color >> 16) & 255);
            background[real_y][real_x][1] = (byte) ((color >> 8) & 255);
            background[real_y][real_x][2] = (byte) (color & 255);
            background[real_y][real_x][3] = (byte) ((color >> 24) & 255);
        } else {
//            System.out.printf("NOT DRAWING: 0x%s, 0x%s\r\n", Integer.toHexString(real_x).toUpperCase(), Integer.toHexString(real_y).toUpperCase());
        }
    }

    private static void putPixelWindow(int x, int y, int line, int col, int color) {
        if (color == Color.TRANSLUCENT) {
            return;
        }

        int real_x = x * 8 + (7 - col);
        // The y axis is from bottom and upwards so (0,0) is bottom-left....
        int real_y = 143 - ((y * 8 + line));
        if (real_y >= 0 && real_x >= 0 && real_x < 160 && real_y < 144) {
            window[real_y][real_x][0] = (byte) ((color >> 16) & 255);
            window[real_y][real_x][1] = (byte) ((color >> 8) & 255);
            window[real_y][real_x][2] = (byte) (color & 255);
            window[real_y][real_x][3] = (byte) ((color >> 24) & 255);
        } else {
//            System.out.printf("NOT DRAWING: 0x%s, 0x%s\r\n", Integer.toHexString(real_x).toUpperCase(), Integer.toHexString(real_y).toUpperCase());
        }
    }

    private static void putPixelObjects(int x, int y, int line, int col, int color, boolean is_over_background) {
        if (color == Color.TRANSLUCENT) {
            return;
        }

        int real_x = x + (7 - col);

        int real_y = 143 - (y + line);
        if (real_y >= 0 && real_x >= 0 && real_x < 160 && real_y < 144) {
            if (is_over_background) {
                over_background[real_y][real_x][0] = (byte) ((color >> 16) & 255);
                over_background[real_y][real_x][1] = (byte) ((color >> 8) & 255);
                over_background[real_y][real_x][2] = (byte) (color & 255);
                over_background[real_y][real_x][3] = (byte) ((color >> 24) & 255);
            } else {
                under_background[real_y][real_x][0] = (byte) ((color >> 16) & 255);
                under_background[real_y][real_x][1] = (byte) ((color >> 8) & 255);
                under_background[real_y][real_x][2] = (byte) (color & 255);
                under_background[real_y][real_x][3] = (byte) ((color >> 24) & 255);
            }
        }
    }

    private static void draw_tile(int sprite_pointer, int x, int y, boolean putpixel_by_tile,
                                  boolean is_background, boolean flip_x, boolean flip_y, boolean over_background, boolean palette_bit) {
        for (int line = 0; line < 8; line++) {
            for (int col = 0; col < 8; col++) {
                int LOWER = (CPU.memory.read_byte(sprite_pointer + line * 2) & 255) & (1 << col);
                int HIGHER = (CPU.memory.read_byte(sprite_pointer + line * 2 + 1) & 255) & (1 << col);
                int COLOR = 0;
                if(LOWER != 0) {
                    COLOR |= 1;
                }
                if(HIGHER !=0) {
                    COLOR |= 2;
                }
                int[] colors;
                byte palette;
                if (putpixel_by_tile) {
                    colors = new int[]{
                            Color.WHITE.getRGB(),
                            Color.LIGHT_GRAY.getRGB(),
                            Color.DARK_GRAY.getRGB(),
                            Color.BLACK.getRGB(),
                    };

                    palette = CPU.memory.read_byte(0xFF47);
                    // Object
                } else {
                    colors = new int[]{
                            Color.WHITE.getRGB(),
                            Color.LIGHT_GRAY.getRGB(),
                            Color.DARK_GRAY.getRGB(),
                            Color.BLACK.getRGB(),
                    };
                    if(palette_bit) {
                        palette = CPU.memory.read_byte(0xFF49);
                    } else {
                        palette = CPU.memory.read_byte(0xFF48);
                    }
                }
                int color_index = palette;
                char mask = (char)(2 * COLOR);
                color_index >>= mask;
                color_index &= 3;
                int color = colors[color_index];
                if(mask == 0 && !putpixel_by_tile && !is_background) {
                    color = Color.TRANSLUCENT;
                }

                short window_y = (short) (CPU.memory.read_byte(0xFF4A) & 0xFF);
                short window_x = (short) ((CPU.memory.read_byte(0xFF4B) & 0xFF) - 7);

                // Background
                if (is_background) {
                    putPixelBackground(x, y, line, col, color);
                } else {
                    // Window
                    if (putpixel_by_tile) {
                        if (window_y <= 143 && window_x >= -7 && window_x <= 166) {
                            putPixelWindow(x, y, line + window_y, col + window_x, color);
                        }
                        // Object
                    } else {
                        if (flip_y && flip_x) {
                            putPixelObjects(x, y, 8 - line, 8 - col, color, over_background);
                        } else if (flip_x) {
                            putPixelObjects(x, y, line, 8 - col, color, over_background);
                        } else if (flip_y) {
                            putPixelObjects(x, y, 16 - line, col, color, over_background);
                        } else {
                            putPixelObjects(x, y, line, col, color, over_background);
                        }
                    }
                }
            }
        }
    }

    private static void draw_background() {
        for (int y = 0; y < 32; y++) {
            for (int x = 0; x < 32; x++) {
                int base_address = (CPU.memory.read_byte(0xFF40) & (1 << 3)) != 0 ? 0x9C00 : 0x9800;
                int val = CPU.memory.read_byte(base_address + y * 32 + x);
                int sprite_base_address = (CPU.memory.read_byte(0xFF40) & (1 << 4)) != 0 ? 0x8000 : 0x9000;
                int sprite_pointer;
                if (sprite_base_address == 0x8000) {
                    sprite_pointer = sprite_base_address + 0x10 * (val & 255);
                } else {
                    sprite_pointer = sprite_base_address + 0x10 * val;
                }
                draw_tile(sprite_pointer, x, y, true, true, false, false, false, false);
            }
        }
    }

    private static void draw_window() {
        for (int y = 0; y < 32; y++) {
            for (int x = 0; x < 32; x++) {
                int base_address = (CPU.memory.read_byte(0xFF40) & (1 << 6)) != 0 ? 0x9C00 : 0x9800;
                int sprite_base_address = (CPU.memory.read_byte(0xFF40) & (1 << 4)) != 0 ? 0x8000 : 0x9000;
                byte val = CPU.memory.read_byte(base_address + y * 32 + x);
                int sprite_pointer;
                if (sprite_base_address == 0x8000) {
                    sprite_pointer = sprite_base_address + 0x10 * (val & 255);
                } else {
                    sprite_pointer = sprite_base_address + 0x10 * val;
                }
                draw_tile(sprite_pointer, x, y, true, false, false, false, true, false);
            }
        }
    }

    private static void draw_objects(boolean background_window_over_object) {
        for (char i = 0xFE00; i <= 0xFE9F; i += 4) {
            int y = ((CPU.memory.read_byte(i) & 255) - 16);
            int x = (char) ((CPU.memory.read_byte(i + 1) & 255) - 8);
            char tile_index = (char) (CPU.memory.read_byte(i + 2) & 255);
            char attributes = (char) (CPU.memory.read_byte(i + 3) & 255);
            boolean flip_x = false, flip_y = false;
            boolean over_background = (attributes & 128) == 0;
            if ((over_background && !background_window_over_object) || (!over_background && background_window_over_object)) {
                if ((attributes & 64) != 0) {
                    flip_y = true;
                }
                if ((attributes & 32) != 0) {
                    flip_x = true;
                }
                char sprite_pointer = (char) (0x8000 + 0x10 * tile_index);
                boolean palette_number_bit = (attributes & (1<<4)) != 0;
                draw_tile(sprite_pointer, x, y, false, false, flip_x, flip_y, over_background, palette_number_bit);
            }
        }
    }

    private static void draw_screen() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

        under_background = new Byte[144][160][4];
        for (int y = 0; y < 144; y++) {
            for (int x = 0; x < 160; x++) {
                under_background[y][x][0] = (byte) ((Color.WHITE.getRGB() >> 16) & 255);
                under_background[y][x][1] = (byte) ((Color.WHITE.getRGB() >> 8) & 255);
                under_background[y][x][2] = (byte) (Color.WHITE.getRGB() & 255);
                under_background[y][x][3] = (byte) ((Color.WHITE.getRGB() >> 24) & 255);

            }
        }

        background = new Byte[256][256][4];
        for (int x = 0; x < 256; x++) {
            for (int y = 0; y < 256; y++) {
                background[y][x][0] = null;
                background[y][x][1] = null;
                background[y][x][2] = null;
                background[y][x][3] = null;
            }
        }

        window = new Byte[144][160][4];
        for (int y = 0; y < 144; y++) {
            for (int x = 0; x < 160; x++) {
                window[y][x][0] = null;
                window[y][x][1] = null;
                window[y][x][2] = null;
                window[y][x][3] = null;
            }
        }

        over_background = new Byte[144][160][4];
        for (int y = 0; y < 144; y++) {
            for (int x = 0; x < 160; x++) {
                over_background[y][x][0] = null;
                over_background[y][x][1] = null;
                over_background[y][x][2] = null;
                over_background[y][x][3] = null;
            }
        }
        screen_buffer = ByteBuffer.allocateDirect(over_background.length);

        if ((CPU.memory.read_byte(0xFF40) & (1 << 1)) != 0) {
            draw_objects(true);
        }
        if ((CPU.memory.read_byte(0xFF40) & 1) != 0) {
            // Background
            draw_background();

            // Window
            if ((CPU.memory.read_byte(0xFF40) & (1 << 5)) != 0) {
                draw_window();
            }
        }
        if ((CPU.memory.read_byte(0xFF40) & (1 << 1)) != 0) {
            draw_objects(false);
        }

        int scX = (CPU.memory.read_byte(0xFF43)) & 0xFF;
        int scY = (CPU.memory.read_byte(0xFF42)) & 0xFF;
        byte[] temp = new byte[144 * 160 * 4];


        for (int y = 0; y < 144; y++) {
            for (int x = 0; x < 160; x++) {
                for (int i = 0; i < 4; i++) {
                    if (under_background[143 - y][x][0] != null) {
                        temp[((143 - y) * 160 + x) * 4 + i] = under_background[143 - y][x][i];
                    }
                }
            }
        }

        for (int y = 0; y < 144; y++) {
            for (int x = 0; x < 160; x++) {
                for (int i = 0; i < 4; i++) {
                    int screenY = (y + scY);
                    screenY %= 256;
                    int screenX = x + scX;
                    screenX %= 256;
                    if (background[255 - screenY][screenX][0] != null) {
                        temp[((143 - y) * 160 + x) * 4 + i] = background[255 - screenY][screenX][i];
                    }
                }
            }
        }

        for (int y = 0; y < 144; y++) {
            for (int x = 0; x < 160; x++) {
                for (int i = 0; i < 4; i++) {
                    if (window[143 - y][x][0] != null){
                        temp[((143 - y) * 160 + x) * 4 + i] = window[143 - y][x][i];
                    }
                }
            }
        }

        for (int y = 0; y < 144; y++) {
            for (int x = 0; x < 160; x++) {
                for (int i = 0; i < 4; i++) {
                    if (over_background[143 - y][x][0] != null) {
                        temp[((143 - y) * 160 + x) * 4 + i] = over_background[143 - y][x][i];
                    }
                }
            }
        }

        ByteBuffer screen_buffer = ByteBuffer.allocateDirect(temp.length);
        pixels = screen_buffer.put(temp).flip();

//        GL11.glPixelZoom(1, 1);
        glWindowPos2i(0, 0);
        GL11.glDrawPixels(160, 144, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, pixels);

        glfwSwapBuffers(opengl_window); // swap the color buffers
    }
}