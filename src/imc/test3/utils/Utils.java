package imc.test3.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

//@formatter:off
/**
 * Helper class which contains many MW/Tools related to: - files handling -
 * string parsing - input stream validation - Map sorting
 *
 * @author Vincent Pingard
 * @version 1.0
 * @since 01-01-2017
 */
// @formatter:on
public class Utils {

	static public List<String> getFileContent(Object o, String fileName, String pack) {
		if (pack == null) // default
			pack = "/imc/test3/resources/";

		List<String> ret = new ArrayList<>();
		try {
			InputStream in = o.getClass().getResourceAsStream(pack + fileName);
			Reader fr = new InputStreamReader(in, "utf-8");
			BufferedReader br = new BufferedReader(fr);
			String line;
			while ((line = br.readLine()) != null) {
				ret.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return ret;
	}

	static public String concatenate(List<String> strs) {
		StringBuilder ret = new StringBuilder();
		for (String str : strs) {
			ret.append(str + "");
		}
		return ret.toString();
	}

	static public int getIntFromUser(String phrase, int min, int max, Scanner reader) {
		int choice = -1;
		if (min > max)
			return choice;
		while (choice < min || choice > max) {
			System.out.println(phrase);
			String choiceStr = reader.next();
			try {
				choice = Integer.parseInt(choiceStr);
			} catch (NumberFormatException e) {
			}
		}
		return choice;
	}

	public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
		return map.entrySet().stream().sorted(Map.Entry.comparingByValue(Collections.reverseOrder()))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
	}

	static public String getCurrentPackage(Object obj) {
		String fullName = obj.getClass().getCanonicalName();
		int lastDot = fullName.lastIndexOf(".");
		if (lastDot == -1)
			return "";
		return "src/" + fullName.substring(0, lastDot).replaceAll("\\.", "/");
	}
}
