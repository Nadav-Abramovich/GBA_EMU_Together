package com.company;

import Gameboy.Gameboy;

import java.lang.reflect.InvocationTargetException;

public class Main {

    public static void main(String[] args) throws IllegalAccessException,
                                                  NoSuchMethodException,
                                                  InvocationTargetException,
                                                  InstantiationException {
        Gameboy gameboy = new Gameboy();
        while (true) {
            gameboy.tick();
        }
    }
}
