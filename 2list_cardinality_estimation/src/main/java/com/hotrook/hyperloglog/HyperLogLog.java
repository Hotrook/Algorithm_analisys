package com.hotrook.hyperloglog;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import static java.lang.Integer.max;
import static java.lang.Math.log;
import static java.lang.Math.pow;

public class HyperLogLog {

	private static double[] alpha = {
			0.673102,
			0.697123,
			0.709208,
			0.715271,
			0.718308
	};

	public static int hyperLogLog(List<Integer> set, int b, Function<Integer, Integer> hash) {
		int m = 1 << b;
		List<Integer> M = new ArrayList(Collections.nCopies(m, 0));

		for (int v : set) {
			add(b, hash, M, v);
		}

		double z = 1.0 / M.stream().mapToDouble(x -> x >= 0 ? (1.0 / (double) ((long) 1 << x)) : 0).sum();
		int e = (int) (m * m * getAlpha(b) * z);
		int ec = correct(e, m, M);

		return ec;
	}

	private static int correct(int e, int m, List<Integer> m1) {
		if (e <= (2.5 * m)) {
			int v = (int) m1.stream().filter(x -> x == 0).count();
			if (v != 0) {
				return (int) (m * log((double) m / (double) v));
			}
		} else if (e > (1.0 / 30.0) * ((long) 1 << 32)) {
			double result = (pow(-2, 32) * log(1.0 - (double) e / (double) ((long) 1 << 32)));
			return (int) result;
		}
		return e;
	}

	private static double getAlpha(int b) {
		return alpha[b - 4];
	}

	private static void add(int b, Function<Integer, Integer> hash, List<Integer> m, int v) {
		int x = hash.apply(v);
		int index = getIndex(x, b);
		int firstOne = findFirstOne(x, b);
		m.set(index, max(m.get(index), firstOne));
	}

	private static int findFirstOne(int x, int b) {
		String bits = Integer.toBinaryString(x);
		String binary = StringUtils.leftPad(bits, 32, '0');
		int it = b;
		while (it < binary.length() && binary.charAt(it) != '1') {
			it++;
		}
		return it - b + 1;
	}

	private static int getIndex(int x, int b) {
		int add = 1;
		for (int i = 1; i < b; ++i) {
			add = add | (1 << i);
		}
		return add & (x >> (32 - b));
	}


}
