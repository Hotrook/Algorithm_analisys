package com.hotrook.hashing;

public class hllHashing {

	public static int md5(int k) {
		int bits = com.google.common.hash.Hashing.md5().hashLong(k).asInt();

		return bits;
	}

	public static int gfh(int k) {
		int bits = com.google.common.hash.Hashing.goodFastHash(64).hashLong(k).asInt();

		return bits;
	}

	public static int gfh(int k, int bitSize) {
		int bits = com.google.common.hash.Hashing.goodFastHash(bitSize).hashLong(k).asInt();

		return bits;
	}

	public static int murmur3_32(int k) {
		int bits = com.google.common.hash.Hashing.murmur3_32().hashLong(k).asInt();

		return bits;
	}

	public static int murmur3_128(int k) {
		int bits = com.google.common.hash.Hashing.murmur3_128().hashLong(k).asInt();

		return bits;
	}

	public static int sha1(int k) {
		int bits = com.google.common.hash.Hashing.sha1().hashLong(k).asInt();

		return bits;
	}

	public static int sha256(int k) {
		int bits = com.google.common.hash.Hashing.sha256().hashLong(k).asInt();

		return bits;
	}

	public static int sha512(int k) {
		int bits = com.google.common.hash.Hashing.sha512().hashLong(k).asInt();

		return bits;
	}

	public static int adler32(int k) {
		int bits = com.google.common.hash.Hashing.adler32().hashLong(k).asInt();

		return bits;
	}

	public static int crc32(int k) {
		int bits = com.google.common.hash.Hashing.crc32().hashLong(k).asInt();

		return bits;
	}

	public static int sipHash24(int k) {
		int bits = com.google.common.hash.Hashing.sipHash24().hashLong(k).asInt();

		return bits;
	}
}
