package com.hotrook.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Random;

public class Utils {

	public static void saveToFile(StringBuilder result, String filename) throws IOException {
		BufferedWriter bufWriter = new BufferedWriter(new FileWriter(filename));
		bufWriter.write(String.valueOf(result));
		bufWriter.close();
	}

	public static void generateSetWithoutRepetitions(List<Integer> set, int size) {
		Random generator = new Random();

		for (int i = 0; i < size; ++i) {
			set.set(i, generator.nextInt());
		}
	}

	public static void addRepetitionsToSet(List<Integer> set, int size) {
		for (int i = 0; i < size / 2; ++i) {
			set.add(set.get(i));
		}
	}
}
