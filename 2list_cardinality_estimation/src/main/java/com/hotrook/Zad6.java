package com.hotrook;

import com.hotrook.hyperloglog.HyperLogLog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.hotrook.hashing.hllHashing.gfh;
import static com.hotrook.utils.Utils.*;

public class Zad6 {

	private static int n = 1000;
	private static int b = 5;

	public static void main(String[] args) throws IOException {

		experiment();

	}

	private static void experiment() throws IOException {
		StringBuilder toFile = new StringBuilder();

		for (int size = 1; size <= n; ++size) {

			List<Integer> set = new ArrayList(Collections.nCopies(size, 0));

			for (int p = 0; p < 10; ++p) {
				set = new ArrayList(Collections.nCopies(size, 0));

				generateSetWithoutRepetitions(set, size);
				int estimatedSize = HyperLogLog.hyperLogLog(set, b, x -> gfh(x));
				double withoutRepsRatio = (double) estimatedSize / (double) size;

				addRepetitionsToSet(set, size);
				estimatedSize = HyperLogLog.hyperLogLog(set, b, x -> gfh(x));
				double withRepsRatio = (double) estimatedSize / (double) size;

				System.out.printf("%d %.3f %.3f\n", size, withoutRepsRatio, withRepsRatio);
				toFile.append(size)
						.append(" ")
						.append(withoutRepsRatio)
						.append(" ")
						.append(withRepsRatio)
						.append("\n");
			}

		}
		saveToFile(toFile, "zad6a.csv");
	}
}
