package com.flytxt.imageprocessor.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
/**
 * 
 * @author shiju.john
 *
 */
public class FileToImageConverter {
	
	
	/**
	 * 
	 * @param file
	 * @param imageWritePath
	 * @throws IOException
	 */
	public void convert(File file,String imageOutPath) throws IOException{
		String classContent = getFileAsString(file);
		if(null!=classContent){
			int rgbArray[] = new int[classContent.length()];
			int index =0;
			String [] spliiedFiles= classContent.split("[\\s]+");
			for(String words: spliiedFiles){
				String [] splittedWords= words.split("[;]+");
				if(splittedWords.length>1){
					for(String value: splittedWords){
						index = processString(value,';',rgbArray,index);
					}
				}else{
						index = processString(words,' ',rgbArray,index);
				}
			}
			
			/*char [] fileChars =  classContent.toCharArray();
			
			int index =0;
			for(char charVAlue:fileChars ){
				rgbArray[index++]=ImageUtils.getInstance().getRGBValueOf(charVAlue);				
			}*/
			int imageSize = getImageSize(classContent.length());
			BufferedImage bufferedImage = getBufferedImage(imageSize,imageSize,rgbArray);
			createOutFolder(imageOutPath);
			File outFile = new File(imageOutPath+File.separator+getName(file.getName())+".jpg");
			ImageIO.write(bufferedImage, "jpg", outFile);
			outFile.createNewFile();
		}
	}
	
	/**
	 * 
	 * @param value
	 * @param separater
	 * @param rgbArray
	 * @param index
	 * @return
	 */
	public int processString(String value,char separater,int []rgbArray,int index ){
		int rgbValue = ImageUtils.getInstance().getRGBValueOf(value);
		if(-1!=rgbValue){
			rgbArray[index++] = rgbValue;
		}else{
			char [] fileChars =  value.toCharArray();		
			for(char charVAlue:fileChars ){
				rgbArray[index++] = ImageUtils.getInstance().getRGBValueOf(charVAlue);				
			}
		}
		rgbArray[index++] = ImageUtils.getInstance().getRGBValueOf(separater);
		return index;
	}
	
	/**
	 * 
	 * @param imageOutPath
	 */
	private void createOutFolder(String imageOutPath) {
		File file = new File(imageOutPath);
		if(!file.exists()){
			file.mkdirs();
		}		
	}


	/**
	 * 
	 * @param name
	 * @return
	 */
	public static String getName(String name) {		
		int index = name.indexOf(".");
		return index!=-1?name.substring(0,index):name;
		
	}

	/**
	 * 
	 * @param length
	 * @return
	 */
	private int getImageSize(int length) {
		Double value = Math.sqrt(length);
		return new Double(Math.ceil(value)).intValue();		
	}

	
	/**
	 * 
	 * @param height
	 * @param width
	 * @param rgb
	 * @return
	 */
	public BufferedImage getBufferedImage(int height,int width,int rgb[]){		
		BufferedImage bufferedImage = new BufferedImage(height, width, BufferedImage.TYPE_INT_RGB);	
		int index=0;
		for(int x=0;x<height;x++){
			for(int y=0;y<width;y++){
				if(rgb.length>index)
				bufferedImage.setRGB(x, y, rgb[index++]);
			}
		}
		return bufferedImage;
	}
	
	/**
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public String getFileAsString(File file) throws IOException{
		return FileUtils.readFileToString(file);
	}

}
