package com.hotrook.kmin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class KMin {

	public static int kMin(int k, List<Integer> M, Function<Integer, Double> hash) {
		List<Double> stats = new ArrayList<>(Collections.nCopies(k, 1.0));

		for (Integer i : M) {
			double h = hash.apply(i);

			if (h < stats.get(k - 1) && !stats.contains(h)) {
				stats.set(k - 1, h);
				sort(stats, k - 1);
			}
		}

		if (stats.get(k - 1) == 1.0) {
			return (int) stats.stream().filter(s -> s != 1.0).count();
		} else {
			return (int) ((k - 1) / stats.get(k - 1));
		}
	}


	public static void sort(List<Double> stats, int k) {
		int it = k;

		while (it > 0 && stats.get(it) < stats.get(it - 1)) {
			Collections.swap(stats, it, it - 1);
			it--;
		}
	}
}
