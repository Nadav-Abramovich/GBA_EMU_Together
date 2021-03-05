package com.company;

import Gameboy.Gameboy;

public class Main {

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        Gameboy gameboy = new Gameboy();
        while (true) {
            gameboy.tick();
        }
    }
}
