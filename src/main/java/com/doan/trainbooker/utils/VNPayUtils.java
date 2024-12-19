package com.doan.trainbooker.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class VNPayUtils {
	public static String hashAllFields(Map<String, String> fields, String hashSecret) {
		StringBuilder data = new StringBuilder();
		Set<String> sortedKeys = fields.keySet().stream().sorted().collect(Collectors.toSet());
		for (String key : sortedKeys) {
			String value = fields.get(key);
			if (value != null && value.length() > 0) {
				data.append(key).append('=').append(value).append('&');
			}
		}
		data.append("vnp_HashSecret=").append(hashSecret);

		return sha256(data.toString());
	}

	private static String sha256(String input) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
			StringBuilder hexString = new StringBuilder();
			for (byte b : hash) {
				String hex = Integer.toHexString(0xff & b);
				if (hex.length() == 1)
					hexString.append('0');
				hexString.append(hex);
			}
			return hexString.toString();
		} catch (Exception e) {
			throw new RuntimeException("Error hashing input", e);
		}
	}
}
