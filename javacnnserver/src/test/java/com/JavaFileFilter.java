package com;

import java.io.File;
import java.io.FileFilter;
/**
 * For filter the java files only 
 * @author shiju.john
 *
 */
public class JavaFileFilter implements FileFilter {

	@Override
	public boolean accept(File pathname) {
		if(pathname.isDirectory()){
			return true;
		}
		return pathname.getName().contains(".java");		
	}

}
