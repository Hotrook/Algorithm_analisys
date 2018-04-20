package com.hotrook.hashing;

import org.apache.commons.lang3.StringUtils;

public class kMinHashing {

	public static double md5(int k) {
		long bits = com.google.common.hash.Hashing.md5().hashLong(k).asLong();

		double result = convertToDouble(bits, 50);
		return result;
	}

	public static double gfh(int k) {
		long bits = com.google.common.hash.Hashing.goodFastHash(64).hashLong(k).asLong();

		double result = convertToDouble(bits, 50);
		return result;
	}

	public static double gfh(int k, int bitSize) {
		long bits = com.google.common.hash.Hashing.goodFastHash(bitSize).hashLong(k).asInt();

		double result = convertToDouble(bits, bitSize);
		return result;
	}

	public static double murmur3_32(int k) {
		long bits = com.google.common.hash.Hashing.murmur3_32().hashLong(k).asInt();

		double result = convertToDouble(bits, 32);
		return result;
	}

	public static double murmur3_128(int k) {
		long bits = com.google.common.hash.Hashing.murmur3_128().hashLong(k).asLong();

		double result = convertToDouble(bits, 50);
		return result;
	}

	public static double sha1(int k) {
		long bits = com.google.common.hash.Hashing.sha1().hashLong(k).asLong();

		double result = convertToDouble(bits, 50);
		return result;
	}

	public static double sha256(int k) {
		long bits = com.google.common.hash.Hashing.sha256().hashLong(k).asLong();

		double result = convertToDouble(bits, 50);
		return result;
	}

	public static double sha512(int k) {
		long bits = com.google.common.hash.Hashing.sha512().hashLong(k).asLong();

		double result = convertToDouble(bits, 50);
		return result;
	}

	public static double adler32(int k) {
		long bits = com.google.common.hash.Hashing.adler32().hashLong(k).asInt();

		double result = convertToDouble(bits, 32);
		return result;
	}

	public static double crc32(int k) {
		long bits = com.google.common.hash.Hashing.crc32().hashLong(k).asInt();

		double result = convertToDouble(bits, 32);
		return result;
	}

	public static double sipHash24(int k) {
		long bits = com.google.common.hash.Hashing.sipHash24().hashLong(k).asLong();

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
