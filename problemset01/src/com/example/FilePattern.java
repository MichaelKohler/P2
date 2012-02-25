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
 * @author You!
 *
 */
public class FilePattern implements FileFilter {

	public FilePattern(String string) {
		// TODO Auto-generated constructor stub
	}

	public boolean accept(File pathname) {
		// TODO Auto-generated method stub
		throw new Error();
	}
    
}
