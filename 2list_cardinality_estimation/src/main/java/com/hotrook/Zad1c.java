package com.hotrook;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.hotrook.hashing.kMinHashing.gfh;
import static com.hotrook.kmin.KMin.kMin;
import static com.hotrook.utils.Utils.generateSetWithoutRepetitions;
import static com.hotrook.utils.Utils.saveToFile;
import static java.lang.Math.abs;

public class Zad1c {

	private static int probes = 100;
	private static int size = 10000;
	private static int minK = 1;
	private static int maxK = 500;

	public static void main(String[] args) throws IOException {

		experiment();

	}

	private static void experiment() throws IOException {
		int counter = 0;
		StringBuilder result = new StringBuilder();
		List<Integer> set = new ArrayList(Collections.nCopies(size, 0));

		for (int k = minK; k <= maxK; ++k) {
			counter = 0;

			for (int i = 1; i <= probes; ++i) {
				generateSetWithoutRepetitions(set, size);
				int estimatedSize = kMin(k, set, x -> gfh(x));
				if (precisionIsGoodEnough(estimatedSize, size, 0.1)) {
					counter++;
				}
			}

			System.out.printf("%d %d\n", k, counter);
			result.append(k).append(" ")
					.append(counter)
					.append("\n");
		}

		saveToFile(result, "test.txt");
	}

	private static boolean precisionIsGoodEnough(int estimatedSize, int size, double maxError) {
		double ratio = (double) estimatedSize / (double) size;
		double error = abs(1.0 - ratio);

		if (error < maxError) {
			return true;
		}

		return false;
	}

	public static class zad6b {

	}
}
