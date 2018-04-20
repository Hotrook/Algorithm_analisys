package com.hotrook;

import com.hotrook.hyperloglog.HyperLogLog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.hotrook.hashing.hllHashing.gfh;
import static com.hotrook.utils.Utils.generateSetWithoutRepetitions;
import static com.hotrook.utils.Utils.saveToFile;

public class Zad6b {

	private static int n = 1000;

	public static void main(String[] args) throws IOException {

		experiment();

	}

	private static void experiment() throws IOException {
		StringBuilder toFile = new StringBuilder();

		for (int size = 1; size <= n; ++size) {
			System.out.println(size);
			List<Integer> set = new ArrayList(Collections.nCopies(size, 0));
			generateSetWithoutRepetitions(set, size);

			toFile.append(size);
			for (int b = 4; b <= 7; ++b) {
				int estimatedSize = HyperLogLog.hyperLogLog(set, b, x -> gfh(x));
				double withoutRepsRatio = (double) estimatedSize / (double) size;

				toFile.append(" ").append(estimatedSize);
			}
			toFile.append("\n");
		}
		saveToFile(toFile, "zad6b.csv");
	}
}
