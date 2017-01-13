package com.flytxt.imageprocessor.image;

import com.flytxt.imageprocessor.config.ImageConfiguration;
import com.flytxt.imageprocessor.processor.ImageProcessor;
/**
 * 
 * @author shiju.john
 *
 */
public class SplitterFactory {
	
	
	/**
	 * 
	 * @param imageLabel
	 * @param imageProcessor
	 * @return
	 */
	public static DataSplitter<inputDataSplit,ImageConfiguration> getDefaultImageSpliter(String imageLabel,ImageProcessor imageProcessor){		
		return new DefaultImageSplitter(imageLabel,imageProcessor);		
	}
	
	
}
