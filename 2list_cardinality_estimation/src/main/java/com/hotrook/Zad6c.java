package com.hotrook;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import static com.hotrook.hashing.hllHashing.*;
import static com.hotrook.hyperloglog.HyperLogLog.hyperLogLog;
import static com.hotrook.utils.Utils.generateSetWithoutRepetitions;
import static com.hotrook.utils.Utils.saveToFile;

public class Zad6c {

	private static final List<Function<Integer, Integer>> hashFunctions = Arrays.asList(
			x -> md5(x),
			x -> gfh(x),
			x -> murmur3_32(x),
			x -> murmur3_128(x),
			x -> sha1(x),
			x -> sha256(x),
			x -> sha512(x),
			x -> adler32(x),
			x -> crc32(x),
			x -> sipHash24(x));

	private static final List<String> hashNames = Arrays.asList(
			"md5",
			"gfh",
			"murmur3_32",
			"murmur3_128",
			"sha1",
			"sha256",
			"sha512",
			"adler32",
			"crc32",
			"sipHash24");

	private static int probes = 1000;
	private static int size = 2000;

	public static void main(String[] args) throws IOException {
		experiment();
	}

	private static void experiment() throws IOException {

		StringBuilder result = new StringBuilder();
		List<Integer> set = new ArrayList(Collections.nCopies(size, 0));
		List<Integer> results = new ArrayList(Collections.nCopies(hashFunctions.size(), 0));

		for (int t = 1; t <= probes; ++t) {
			generateSetWithoutRepetitions(set, size);
			for (int i = 0; i < hashFunctions.size(); ++i) {
				int estimatedSize = hyperLogLog(set, 5, hashFunctions.get(i));
				results.set(i, results.get(i) + estimatedSize);
			}
		}

		makeStringFromResults(result, results);
		saveToFile(result, "zad6c.csv");
	}

	private static void makeStringFromResults(StringBuilder result, List<Integer> results) {
		for (int i = 0; i < hashFunctions.size(); ++i) {
			System.out.printf("%s: %d\n", hashNames.get(i), results.get(i) / probes);
			result.append(hashNames.get(i))
					.append(": ")
					.append(results.get(i) / probes)
					.append("\n");
		}
	}
}
