package org.springframework.samples.petclinic.system;

public class StringUtil {

	public static boolean isNull(String str) {
		return ((str == null) || (str.trim().length() == 0));
	}

	public static String isNull(String str, String defaultString) {
		return isNull(str) ? defaultString : "";
	}

}
