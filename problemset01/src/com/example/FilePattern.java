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
 * @author Michael Kohler 11-108-289
 * @author Lukas Diener 11-123-213
 *
 */
public class FilePattern implements FileFilter {
	char[] patternChars;
	String pattern;
	public FilePattern(String string) {
		this.pattern = string;
		this.patternChars = string.toCharArray();
	}

	public boolean accept(File pathname) {
		String fileName = pathname.getName();
		char[] fileNameChars = fileName.toCharArray();
		
		if(pattern.indexOf("*") == -1){
			/* Match question marks first. They are easier, because the length has
			 * to be the same for both strings. The chars must either match or
			 * the pattern char has to be a question mark.
			 */
			if(fileNameChars.length != patternChars.length){
				return false;
			}
			for(int i=0; i<patternChars.length; i++){
				char pat = patternChars[i];
				char name = fileNameChars[i];
				if(pat != name && pat != '?'){
					return false;
				}
			}
		}else{
			/* Now match asterisks. Split the pattern on the * sign into parts
			 * (an additional empty array item has to be added when * is the last
			 * character), match them one by one and use only the substring left behind
			 * for teh next match.
			 * The last part must match the rest of the string exactly if it's not an
			 * asterisk.
			 */
			int counter = -1;
			String[] parts = pattern.split("\\*");
			if(pattern.indexOf("*")==pattern.length()-1){
				parts = addToArray(parts, "");
			}

			for(String part : parts){
				counter++;
				if(part.equals("")){}
				else{
					int index = fileName.indexOf(part);
					if(index == -1)
		            {
		                return false;
		            }
					
					if(counter == 0 && fileName.indexOf(part)!= 0){
						return false;
					}
					
					fileName = fileName.substring(index + part.length());

					if(counter == parts.length-1 && !fileName.equals("")){
						return false;
					}
				}
				
			}
			return true;
			
		}
		return true;
		//throw new Error();
	}
	
	public String[] addToArray(String[] array, String item){
		String[] temp = new String[array.length+1];
		System.arraycopy(array,0,temp,0,array.length);
		temp[array.length] = item;
		return temp;
	}
}
