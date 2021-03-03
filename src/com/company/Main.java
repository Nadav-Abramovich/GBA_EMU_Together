package com.company;

import Gameboy.Gameboy;

public class Main {

    public static void main(String[] args) {
        // write your code here
        Gameboy gameboy = new Gameboy();
        while (true) {
            gameboy.tick();
        }
    }
}
