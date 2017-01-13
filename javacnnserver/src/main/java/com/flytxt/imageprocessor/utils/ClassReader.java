package com.flytxt.imageprocessor.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
/**
 * 
 * @author shiju.john
 *
 */
public class ClassReader {
	
	private static final String IMAGE_FOLDER_NAME = "image";
	
	
	/**
	 * 
	 * @param filePath
	 * @throws IOException
	 */
	public void read(String filePath) throws IOException{
		FileToImageConverter converter = new FileToImageConverter();
		Path path = Paths.get(filePath);
		if(path.toFile().isDirectory()){
			File inputDirectory = path.toFile(); 
			String imagePath = inputDirectory.getParentFile().getAbsolutePath() + File.separator + IMAGE_FOLDER_NAME ;
			File [] inpuSubDirectories = inputDirectory.listFiles();
			for(File subDirectory : inpuSubDirectories){
				File []inputClassFiles = subDirectory.listFiles();
				for(File file : inputClassFiles){
					String newImagePath = imagePath + File.separator+file.getParentFile().getName();
					if(!hasImageExist(file,newImagePath)){						
						converter.convert(file, newImagePath);
					}
				}				
			}
		}		
	}
	/**
	 * Check the class image was already created or not 
	 * @param inputClassFile
	 * @param parentPath
	 * @return
	 */
	private boolean hasImageExist(File inputClassFile,String parentPath) {
		String fileName =  parentPath+File.separator+FileToImageConverter.getName(inputClassFile.getName())+".jpg";
		File file = Paths.get(fileName).toFile();
		return file.exists();
	}
	
	

}
