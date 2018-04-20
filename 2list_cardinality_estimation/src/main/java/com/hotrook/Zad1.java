package com.hotrook;

import javafx.util.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import static com.hotrook.hashing.kMinHashing.gfh;
import static com.hotrook.kmin.KMin.kMin;
import static com.hotrook.utils.Utils.*;


public class Zad1 {

	private static int k = 20;
	private static int minSize = 1;
	private static int maxSize = 100;


	public static void main(String[] args) throws IOException {

		experiment();

	}

	public static void experiment() throws IOException {

		StringBuilder result = new StringBuilder();

		List<Pair<Integer, Double>> withoutReps = new ArrayList();
		List<Pair<Integer, Double>> withReps = new ArrayList();

		for (int size = minSize; size <= maxSize; ++size) {

			List<Integer> set = new ArrayList(Collections.nCopies(size, 0));

			generateSetWithoutRepetitions(set, size);
			makeExpWithotReps(withoutReps, size, set);
			addRepetitionsToSet(set, size);
			makeExpWitReps(withReps, size, set);
		}

		makeStringFromResult(result, withoutReps, withReps);
		saveToFile(result, "hhh.txt");
	}

	private static void makeStringFromResult(
			StringBuilder result,
			List<Pair<Integer, Double>> withoutReps,
			List<Pair<Integer, Double>> withReps) {
		IntStream.range(0, withoutReps.size()).forEach(x -> {
			result.append(withoutReps.get(x).getKey())
					.append(" ")
					.append(withoutReps.get(x).getValue())
					.append(" ")
					.append(withReps.get(x).getValue())
					.append("\n");
		});
	}

	private static void makeExpWitReps(List<Pair<Integer, Double>> withReps, int size, List<Integer> set) {
		int sizeWithReps = (int) set.stream().distinct().count();
		int estimatedSize = kMin(k, set, x -> gfh(x));
		System.out.println(estimatedSize);
		withReps.add(new Pair(size, (double) estimatedSize / (double) sizeWithReps));
	}

	private static void makeExpWithotReps(List<Pair<Integer, Double>> withoutReps, int size, List<Integer> set) {
		int estimatedSize = kMin(k, set, x -> gfh(x));
		System.out.println(estimatedSize);
		withoutReps.add(new Pair(size, (double) estimatedSize / (double) size));
	}


}



