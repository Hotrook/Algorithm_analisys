package com.hotrook;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.hotrook.kmin.KMin.*;
import static com.hotrook.utils.Utils.saveToFile;

public class Zad1b {

	private static int minSize = 1;
	private static int maxSize = 10000;

	public static void main(String[] args) throws IOException {

		exp1b();

	}

	private static void exp1b() throws IOException {
		StringBuilder result = new StringBuilder();
		List<Integer> possibleK = Arrays.asList(2, 3, 10, 100, 400);

		for (int size = minSize; size <= maxSize; ++size) {
			List<Integer> set = new ArrayList<>(Collections.nCopies(size, 0));

			generateSetWithoutRepetitions(set, size);

			result.append(size).append(" ");

			int finalSize = size;
			possibleK.forEach(k -> {
				int estimatedSize = kMin(k, set, x -> gfh(x));
				result.append((double) estimatedSize / (double) finalSize).append(" ");
			});

			result.append("\n");
		}

		saveToFile(result, "zad1bRatios.txt");
	}

}
