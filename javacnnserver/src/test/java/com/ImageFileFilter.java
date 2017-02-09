package com;

import java.io.File;
import java.io.FileFilter;
/**
 * 
 * @author shiju.john
 *
 */
public class ImageFileFilter implements FileFilter {

	@Override
	public boolean accept(File pathname) {		
		return pathname.getName().contains(".png")||pathname.getName().contains(".jpg")||
				pathname.getName().contains(".jpeg");
		
	}
	

}
