package com.hotrook;

import com.hotrook.hashing.hllHashing;
import com.hotrook.hashing.kMinHashing;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.hotrook.hyperloglog.HyperLogLog.hyperLogLog;
import static com.hotrook.kmin.KMin.kMin;
import static com.hotrook.utils.Utils.generateSetWithoutRepetitions;
import static com.hotrook.utils.Utils.saveToFile;

public class zad6d {

	private static int minSize = 1;
	private static int maxSize = 1000;
	private static int probes = 10;


	public static void main(String[] args) throws IOException {
		experiment();
	}

	private static void experiment() throws IOException {

		StringBuilder result = new StringBuilder();

		for (int size = minSize; size <= maxSize; ++size) {
			List<Integer> set = new ArrayList(Collections.nCopies(size, 0));
			System.out.println(size);

			for (int t = 1; t <= probes; ++t) {
				generateSetWithoutRepetitions(set, size);

				int estimatedSize = kMin(5, set, x -> kMinHashing.gfh(x, 32));
				double kminRatio = (double) estimatedSize / (double) size;

				estimatedSize = hyperLogLog(set, 5, x -> hllHashing.gfh(x));
				double hyperRatio = (double) estimatedSize / (double) size;

				result.append(size).append(" ")
						.append(kminRatio).append(" ")
						.append(hyperRatio).append("\n");
			}

		}

		saveToFile(result, "zad6d.csv");
	}
}
