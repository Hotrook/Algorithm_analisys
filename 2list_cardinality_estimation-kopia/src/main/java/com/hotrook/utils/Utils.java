package com.hotrook.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Utils {
    public static void saveToFile(StringBuilder result, String filename) throws IOException {
        BufferedWriter bufWriter = new BufferedWriter(new FileWriter(filename));
        bufWriter.write(String.valueOf(result));
        bufWriter.close();
    }
}
