package com.example;

import java.io.File;
import java.io.FileFilter;

/** Filters file names using command-line wildcards.
 * <P>
 * <tt>*</tt> matches any number of characters.
 * <tt>?</tt> matches exactly one characters.
 * <P>
 * <b>Example:</b> 
 * <tt>*.txt</tt> matches all text files;  
 * <tt>stoya??.jpg</tt> matches all images that start
 * with <i>stoya</i> and got two more characters. 
 * 
 * @author omn
 *
 */
public class FilePattern implements FileFilter {

	private String pattern;

	public FilePattern(String string) {
		pattern = string;
	}

	public boolean accept(File file) {
		return match(pattern, file.getPath());
	}

	boolean match(String glob, String fileName) {
		if (glob.isEmpty()) {
			return fileName.isEmpty();
		}
		if (glob.startsWith("*")) {
			return matchStar(glob.substring(1), fileName);
		}
		if (fileName.isEmpty()) {
			return false;
		}
		if ((!glob.startsWith("?")) && (glob.charAt(0) != fileName.charAt(0))) {
			return false;
		}
		return match(glob.substring(1), fileName.substring(1));
	}

	boolean matchStar(String rest, String fileName) {
		for (int i = fileName.length(); i >= 0; i--) {
			if (match(rest, fileName.substring(i))) {
				return true;
			}
		}
		return false;
	}
}
