package com.hotrook.kmin;

import com.google.common.hash.Hashing;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
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

	public static void generateSetWithoutRepetitions(List<Integer> set, int size) {
		Random generator = new Random();

		for (int i = 0; i < size; ++i) {
			set.set(i, generator.nextInt());
		}
	}

	public static void addRepetitionsToSet(List<Integer> set, int size) {
		for (int i = 0; i < size; ++i) {
			set.add(set.get(i));
		}
	}

	public static double md5(int k) {
		long bits = Hashing.md5().hashLong(k).asLong();

		double result = convertToDouble(bits, 50);
		return result;
	}

	public static double gfh(int k) {
		long bits = Hashing.goodFastHash(64).hashLong(k).asLong();

		double result = convertToDouble(bits, 50);
		return result;
	}

	public static double gfh(int k, int bitSize) {
		long bits = Hashing.goodFastHash(bitSize).hashLong(k).asInt();

		double result = convertToDouble(bits, bitSize);
		return result;
	}

	public static double murmur3_32(int k) {
		long bits = Hashing.murmur3_32().hashLong(k).asInt();

		double result = convertToDouble(bits, 32);
		return result;
	}

	public static double murmur3_128(int k) {
		long bits = Hashing.murmur3_128().hashLong(k).asLong();

		double result = convertToDouble(bits, 50);
		return result;
	}

	public static double sha1(int k) {
		long bits = Hashing.sha1().hashLong(k).asLong();

		double result = convertToDouble(bits, 50);
		return result;
	}

	public static double sha256(int k) {
		long bits = Hashing.sha256().hashLong(k).asLong();

		double result = convertToDouble(bits, 50);
		return result;
	}

	public static double sha512(int k) {
		long bits = Hashing.sha512().hashLong(k).asLong();

		double result = convertToDouble(bits, 50);
		return result;
	}

	public static double adler32(int k) {
		long bits = Hashing.adler32().hashLong(k).asInt();

		double result = convertToDouble(bits, 32);
		return result;
	}

	public static double crc32(int k) {
		long bits = Hashing.crc32().hashLong(k).asInt();

		double result = convertToDouble(bits, 32);
		return result;
	}

	public static double sipHash24(int k) {
		long bits = Hashing.sipHash24().hashLong(k).asLong();

		double result = convertToDouble(bits, 50);
		return result;
	}


	public static double convertToDouble(long longBits, int bitsSize) {
		String bits = Long.toBinaryString(longBits);
		String allBits = StringUtils.leftPad(bits, 64, '0');
		double two = 0.5;
		double result = 0.0;

		for (int i = 64 - bitsSize; i < 64; ++i) {
			if (allBits.charAt(i) == '1') {
				result += two;
			}
			two /= 2;
		}

		if (result == 0.0) {
			result = 1.0;
		}

		return result;
	}

}
