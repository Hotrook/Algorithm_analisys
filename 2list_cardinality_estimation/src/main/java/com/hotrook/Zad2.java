package com.hotrook;

import com.google.common.hash.Hashing;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.hotrook.hashing.kMinHashing.*;
import static com.hotrook.kmin.KMin.kMin;
import static com.hotrook.utils.Utils.generateSetWithoutRepetitions;
import static com.hotrook.utils.Utils.saveToFile;

public class Zad2 {

	private static final List<Function<Integer, Double>> hashFunctions = Arrays.asList(
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
	private static int k = 400;

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
				int estimatedSize = kMin(k, set, hashFunctions.get(i));
				results.set(i, results.get(i) + estimatedSize);
			}
		}

		makeStringFromResults(result, results);
		saveToFile(result, "test.txt");
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


	private static void statisticalTest() {
		int probes = 100000;
		Random generator = new Random();

		List<String> hashes = new ArrayList();
		List<Integer> bitsCounter = new ArrayList(Collections.nCopies(32, 0));

		generateHashes(generator, hashes, probes);

		IntStream.range(0, 32).forEach(i -> {
			bitsCounter.set(i, (int) hashes.stream().filter(y -> y.charAt(i) == '1').count());
			System.out.printf("%d: %d\n", i + 1, bitsCounter.get(i));
		});
	}

	private static void generateHashes(Random generator, List<String> hashes, int probes) {
		for (int i = 0; i < probes; ++i) {
			int k = generator.nextInt();
			int result = Hashing.adler32().hashInt(k).asInt();
			String allBits = StringUtils.leftPad(Integer.toBinaryString(result), 32, '0');
			hashes.add(allBits);
		}
	}

	private static void experimentWithBits() throws IOException {
		int probes = 100;
		int size = 10000;

		StringBuilder result = new StringBuilder();
		List<Integer> set = new ArrayList(Collections.nCopies(size, 0));
		List<Integer> results = new ArrayList(Collections.nCopies(32, 0));

		for (int i = 0; i < probes; ++i) {
			generateSetWithoutRepetitions(set, size);
			for (int bits = 1; bits <= 32; ++bits) {
				int finalBits = bits;
				int estimatedSize = kMin(k, set, x -> gfh(x, finalBits));

				results.set(bits - 1, results.get(bits - 1) + estimatedSize);
			}
		}

		results = results.stream().map(x -> x / probes).collect(Collectors.toList());
		makeStringFromBitsResult(result, results);
		saveToFile(result, "bitsExperiment.txt");
	}

	private static void makeStringFromBitsResult(StringBuilder result, List<Integer> results) {
		List<Integer> finalResults = results;
		IntStream.range(0, results.size())
				.forEach(i -> result
						.append(i).append(" ")
						.append(finalResults.get(i))
						.append("\n"));
	}
}
