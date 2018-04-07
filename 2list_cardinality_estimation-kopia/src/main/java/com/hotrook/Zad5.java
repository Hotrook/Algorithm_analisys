package com.hotrook;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.hotrook.kmin.KMin.*;
import static com.hotrook.utils.Utils.saveToFile;
import static java.lang.Math.abs;

public class Zad5 {

	private static int probes = 50;
	private static int k = 500;
	private static int minSize = 1;
	private static int maxSize = 10000;

	public static void main(String[] args) throws IOException {
		exp();
	}

	public static void exp() throws IOException {
		StringBuilder result = new StringBuilder();
		List<Double> results = new ArrayList();

		for (int size = minSize; size <= maxSize; ++size) {
			List<Integer> set = new ArrayList(Collections.nCopies(size, 0));

			for (int i = 0; i < probes; ++i) {
				generateSetWithoutRepetitions(set, size);
				int estimatedSize = kMin(k, set, x -> gfh(x, 32));
				double ratio = (double) estimatedSize / (double) size;
				results.add(ratio);
				result.append(size).append(" ").append(estimatedSize).append("\n");
			}
		}

		saveToFile(result, "visualize5.txt");
		calculateDeltas(results);
	}

	private static void calculateDeltas(List<Double> results) {
		System.out.print("Delta for 0.95: ");
		System.out.println(findDelta(0.5, 1.0, 0.95, results));
		System.out.print("Delta for 0.99: ");
		System.out.println(findDelta(0.5, 1.0, 0.99, results));
		System.out.print("Delta for 0.995: ");
		System.out.println(findDelta(0.5, 1.0, 0.995, results));
	}

	private static double findDelta(double start, double stop, double expectedCoverage, List<Double> stream) {
		double middle = (start + stop) / 2.0;
		double coverage = estimateDeltaCoverage(middle, stream);

		if (abs(expectedCoverage - coverage) < 0.00001) {
			return middle;
		}
		if (expectedCoverage > coverage) {
			return findDelta(middle, stop, expectedCoverage, stream);
		}
		return findDelta(start, middle, expectedCoverage, stream);
	}

	private static double estimateDeltaCoverage(double middle, List<Double> list) {
		return (double) list.stream().filter(x -> abs(1.0 - x) < middle).count() / (double) list.size();
	}

}